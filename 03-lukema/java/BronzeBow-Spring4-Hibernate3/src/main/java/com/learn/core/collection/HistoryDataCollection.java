package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.HistoryDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.bollinger.BollingerData;
import com.learn.core.bollinger.BollingerDataUtils;
import com.learn.core.ema.EmaData;
import com.learn.core.ema.EmaDataUtils;
import com.learn.core.momentum.MomentumData;
import com.learn.core.momentum.MomentumDataUtils;
import com.learn.core.raw.RawData;


public class HistoryDataCollection
   extends DataCollectionBase
{
   private HistoryDomainData historyDomainData;

   public HistoryDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position, true);
   }

   protected void setDomainData()
   {
      historyDomainData = new HistoryDomainData();
      setDomainData(historyDomainData);
   }

   protected void fillAdjustedY()
   {
      historyDomainData.setOpen(getAdjustedY().get(0));
      historyDomainData.setHigh(getAdjustedY().get(1));
      historyDomainData.setLow(getAdjustedY().get(2));
      historyDomainData.setClose(getAdjustedY().get(3));

      historyDomainData.setBollingerUpper(getAdjustedY().get(4));
      historyDomainData.setBollingerLower(getAdjustedY().get(5));

      historyDomainData.setHighTBP(getAdjustedY().get(6));
      historyDomainData.setLowTBP(getAdjustedY().get(7));
      historyDomainData.setHighTarget(getAdjustedY().get(8));
      historyDomainData.setLowTarget(getAdjustedY().get(9));
      historyDomainData.setHighStop(getAdjustedY().get(10));
      historyDomainData.setLowStop(getAdjustedY().get(11));
      historyDomainData.setTomorrowHighTarget(getAdjustedY().get(12));
      historyDomainData.setTomorrowLowTarget(getAdjustedY().get(13));
      historyDomainData.setTomorrowHighStop(getAdjustedY().get(14));
      historyDomainData.setTomorrowLowStop(getAdjustedY().get(15));

      historyDomainData.setEma05(getAdjustedY().get(16));
      historyDomainData.setEma10(getAdjustedY().get(17));
      historyDomainData.setEma15(getAdjustedY().get(18));
      historyDomainData.setEma25(getAdjustedY().get(19));
      historyDomainData.setEma50(getAdjustedY().get(20));
   }

   public void loadData()
   {
      addRawData();
      addBollingerData();
      addMomentumData();
      addEMAData();
   }

   private void addRawData()
   {
      int len = MbaUtils.MaxDays;

      String[] date = new String[len];
      float[] openRaw = new float[len];
      float[] highRaw = new float[len];
      float[] lowRaw = new float[len];
      float[] closeRaw = new float[len];

      historyDomainData.setDate(date);
      historyDomainData.setOpenRaw(openRaw);
      historyDomainData.setHighRaw(highRaw);
      historyDomainData.setLowRaw(lowRaw);
      historyDomainData.setCloseRaw(closeRaw);

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         date[i] = getRawDataVector().get(k).getDate();
         openRaw[i] = getRawDataVector().get(k).getOpen();
         highRaw[i] = getRawDataVector().get(k).getHigh();
         lowRaw[i] = getRawDataVector().get(k).getLow();
         closeRaw[i] = getRawDataVector().get(k).getClose();
      }

      addRawY(openRaw);
      addRawY(highRaw);
      addRawY(lowRaw);
      addRawY(closeRaw);
   }

   private void addBollingerData()
   {
      int len = MbaUtils.MaxDays;
      float[] bollingerUpper = new float[len];
      float[] bollingerLower = new float[len];

      Vector<BollingerData> data = BollingerDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         bollingerUpper[i] = data.get(k).getUpperBand();
         bollingerLower[i] = data.get(k).getLowerBand();
      }

      addRawY(bollingerUpper);
      addRawY(bollingerLower);
   }

   private void addEMAData()
   {
      Vector<EmaData> ema05V = EmaDataUtils.getNewDataVector05(getRawDataVector());
      Vector<EmaData> ema10V = EmaDataUtils.getNewDataVector10(getRawDataVector());

      Vector<EmaData> ema15V = null;
      Vector<EmaData> ema25V = null;
      Vector<EmaData> ema50V = null;

      ema15V = EmaDataUtils.getNewDataVector15(getRawDataVector());

      ema25V = EmaDataUtils.getNewDataVector25(getRawDataVector());
      ema50V = EmaDataUtils.getNewDataVector50(getRawDataVector());

      int len = MbaUtils.MaxDays;
      float[] ema05 = new float[len];
      float[] ema10 = new float[len];

      float[] ema15 = null;
      float[] ema25 = null;
      float[] ema50 = null;

      ema15 = new float[len];

      ema25 = new float[len];
      ema50 = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         ema05[i] = ema05V.get(k).getEma();
         ema10[i] = ema10V.get(k).getEma();

         ema15[i] = ema15V.get(k).getEma();

         ema25[i] = ema25V.get(k).getEma();
         ema50[i] = ema50V.get(k).getEma();
      }

      addRawY(ema05);
      addRawY(ema10);

      addRawY(ema15);

      addRawY(ema25);
      addRawY(ema50);
   }

   private void addMomentumData()
   {
      int len = MbaUtils.MaxDays;

      Vector<MomentumData> data = MomentumDataUtils.getNewDataVector(getRawDataVector());

      int yesterdayIndex = Math.max(0, data.size() - len - 1);
      MomentumData yesterdayMD = data.get(yesterdayIndex);

      MomentumData md = null;

      float[] highTBP = new float[len];
      float[] lowTBP = new float[len];
      float[] highTarget = new float[len];
      float[] lowTarget = new float[len];
      float[] highStop = new float[len];
      float[] lowStop = new float[len];
      float[] tomorrowHighTarget = new float[len];
      float[] tomorrowLowTarget = new float[len];
      float[] tomorrowHighStop = new float[len];
      float[] tomorrowLowStop = new float[len];

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         md = data.get(k);
         highTBP[i] = md.getHighTBP();
         lowTBP[i] = md.getLowTBP();
         highTarget[i] = yesterdayMD.getHighTarget();
         lowTarget[i] = yesterdayMD.getLowTarget();
         highStop[i] = yesterdayMD.getHighStop();
         lowStop[i] = yesterdayMD.getLowStop();
         tomorrowHighTarget[i] = md.getHighTarget();
         tomorrowLowTarget[i] = md.getLowTarget();
         tomorrowHighStop[i] = md.getHighStop();
         tomorrowLowStop[i] = md.getLowStop();

         yesterdayMD = md;
      }

      String tomorrowRangeString = "   (" + MbaUtils.DecimalFormat.format(tomorrowLowStop[len - 1]) + " | "
            + MbaUtils.DecimalFormat.format(tomorrowLowTarget[len - 1]) + ",  " + MbaUtils.DecimalFormat.format(tomorrowHighTarget[len - 1]) + " | "
            + MbaUtils.DecimalFormat.format(tomorrowHighStop[len - 1]) + ")";

      historyDomainData.setTomorrowRangeString(tomorrowRangeString);

      addRawY(highTBP);
      addRawY(lowTBP);
      addRawY(highTarget);
      addRawY(lowTarget);
      addRawY(highStop);
      addRawY(lowStop);
      addRawY(tomorrowHighTarget);
      addRawY(tomorrowLowTarget);
      addRawY(tomorrowHighStop);
      addRawY(tomorrowLowStop);
   }
}
