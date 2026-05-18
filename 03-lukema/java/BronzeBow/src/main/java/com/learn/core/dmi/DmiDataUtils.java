package com.learn.core.dmi;


import java.util.Vector;

import com.learn.common.util.ChartConstants;
import com.learn.core.raw.RawData;


public class DmiDataUtils
{
   private static void digestYesterdayData(DmiData dmiData, RawData todayData, DmiData yesterdayDMIData, RawData yesterdayData, Vector<DmiData> data)
   {
      // tr1
      float fTR1 = Math.abs(todayData.getHigh() - todayData.getLow());
      float fTR2 = Math.abs(todayData.getHigh() - yesterdayData.getClose());
      float fTR3 = Math.abs(todayData.getLow() - yesterdayData.getClose());
      float tmp = Math.max(fTR1, fTR2);
      dmiData.setTr1(Math.max(tmp, fTR3));

      // plusDM1 minusDM1
      float fPlusDM1 = Math.max(0, todayData.getHigh() - yesterdayData.getHigh());
      float fMinusDM1 = Math.max(0, yesterdayData.getLow() - todayData.getLow());

      if (fPlusDM1 > fMinusDM1)
      {
         dmiData.setPlusDM1(fPlusDM1);
         dmiData.setMinusDM1(0);
      }
      else if (fPlusDM1 == fMinusDM1)
      {
         dmiData.setPlusDM1(0);
         dmiData.setMinusDM1(0);
      }
      else
      {
         dmiData.setPlusDM1(0);
         dmiData.setMinusDM1(fMinusDM1);
      }

      // 15th day
      float f13_14 = (float) (ChartConstants.HALF_CYCLE - 1) / ChartConstants.HALF_CYCLE;

      if (data.size() == ChartConstants.HALF_CYCLE + 1)
      {
         float sumTR1 = 0;
         float sumPlusDM1 = 0;
         float sumMinusDM1 = 0;

         for (int i = 1; i < ChartConstants.HALF_CYCLE; i++)
         {
            DmiData tmpDMIData = data.get(i);

            sumTR1 += tmpDMIData.getTr1();
            sumPlusDM1 += tmpDMIData.getPlusDM1();
            sumMinusDM1 += tmpDMIData.getMinusDM1();
         }

         dmiData.setTr14(f13_14 * sumTR1 + dmiData.getTr1());
         dmiData.setPlusDM14(f13_14 * sumPlusDM1 + dmiData.getPlusDM1());
         dmiData.setMinusDM14(f13_14 * sumMinusDM1 + dmiData.getMinusDM1());
      }
      else if (data.size() > ChartConstants.HALF_CYCLE + 1)
      {
         dmiData.setTr14(f13_14 * yesterdayDMIData.getTr14() + dmiData.getTr1());
         dmiData.setPlusDM14(f13_14 * yesterdayDMIData.getPlusDM14() + dmiData.getPlusDM1());
         dmiData.setMinusDM14(f13_14 * yesterdayDMIData.getMinusDM14() + dmiData.getMinusDM1());
      }

      if (data.size() > ChartConstants.HALF_CYCLE)
      {
         dmiData.setPlusDI14((float) 100 * dmiData.getPlusDM14() / dmiData.getTr14());
         dmiData.setMinusDI14((float) 100 * dmiData.getMinusDM14() / dmiData.getTr14());
         dmiData.setDiDiff(Math.abs(dmiData.getPlusDI14() - dmiData.getMinusDI14()));
         dmiData.setDiSum(dmiData.getPlusDI14() + dmiData.getMinusDI14());
         dmiData.setDx(100 * dmiData.getDiDiff() / dmiData.getDiSum());
      }

      if (data.size() == 2 * ChartConstants.HALF_CYCLE)
      {
         float sumDX = 0;
         for (int i = ChartConstants.HALF_CYCLE; i < 2 * ChartConstants.HALF_CYCLE; i++)
         {
            sumDX += data.get(i).getDx();
         }
         dmiData.setAdx(sumDX / ChartConstants.HALF_CYCLE);
      }
      else if (data.size() > 2 * ChartConstants.HALF_CYCLE)
      {
         dmiData.setAdx((yesterdayDMIData.getAdx() * (ChartConstants.HALF_CYCLE - 1) + dmiData.getDx()) / ChartConstants.HALF_CYCLE);
      }

      if (data.size() == 3 * ChartConstants.HALF_CYCLE - 1)
      {
         float sumADX = 0;
         for (int i = 2 * ChartConstants.HALF_CYCLE - 1; i < data.size(); i++)
         {
            sumADX += data.get(i).getAdx();
         }
         dmiData.setAdxr(sumADX / ChartConstants.HALF_CYCLE);
      }
      else if (data.size() > 3 * ChartConstants.HALF_CYCLE - 1)
      {
         DmiData dmiData1 = data.get(data.size() - ChartConstants.HALF_CYCLE - 1);
         DmiData dmiData2 = data.get(data.size() - 1);

         dmiData.setAdxr((dmiData1.getAdx() + dmiData2.getAdx()) / 2);
      }

      if (data.size() >= 3 * ChartConstants.HALF_CYCLE - 1)
      {
         dmiData.setAtr14(dmiData.getTr14() / ChartConstants.HALF_CYCLE);
      }
   }

   public static Vector<DmiData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<DmiData> data = new Vector<DmiData>();

      DmiData yesterdayDMIData = null;
      RawData yesterdayData = null;

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RawData todayData = rawDataVector.get(i);

         DmiData dmiData = new DmiData();
         data.add(dmiData);

         if (yesterdayDMIData != null)
         {
            digestYesterdayData(dmiData, todayData, yesterdayDMIData, yesterdayData, data);
         }

         yesterdayDMIData = dmiData;
         yesterdayData = todayData;
      }

      return data;
   }
}
