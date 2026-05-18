package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.EmaDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.ema.EmaData;
import com.learn.core.ema.EmaDataUtils;
import com.learn.core.raw.RawData;


public class EmaDataCollection
   extends DataCollectionBase
{
   private EmaDomainData emaDomainData;

   public EmaDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      emaDomainData.setEma_5_10(getAdjustedY().get(0));
      emaDomainData.setInc(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      emaDomainData = new EmaDomainData();
      setDomainData(emaDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] ema_5_10Raw = new float[len];
      float[] incRaw = new float[len];

      emaDomainData.setEma_5_10Raw(ema_5_10Raw);
      emaDomainData.setIncRaw(incRaw);

      Vector<EmaData> data05 = EmaDataUtils.getNewDataVector05(getRawDataVector());
      Vector<EmaData> data10 = EmaDataUtils.getNewDataVector10(getRawDataVector());

      ema_5_10Raw[0] = data05.get(0).getEma() - data10.get(0).getEma();
      incRaw[0] = 0;
      ema_5_10Raw[1] = data05.get(1).getEma() - data10.get(1).getEma();
      incRaw[1] = ema_5_10Raw[1] - ema_5_10Raw[0];

      float sign = 0;

      for (int i = 2; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         ema_5_10Raw[i] = data05.get(k).getEma() - data10.get(k).getEma();

         if (ema_5_10Raw[i] == 0)
         {
            sign = 0;
         }
         else
         {
            sign = (float) (ema_5_10Raw[i] / Math.abs(ema_5_10Raw[i]));
         }

         incRaw[i] = ema_5_10Raw[i] - ema_5_10Raw[i - 2];

         if ((incRaw[i] * sign) > 0)
         {
            incRaw[i] = 0;
         }
      }

      float maxEMA_5_10 = Integer.MIN_VALUE;
      float minEMA_5_10 = Integer.MAX_VALUE;

      for (int i = 0; i < ema_5_10Raw.length; i++)
      {
         maxEMA_5_10 = Math.max(ema_5_10Raw[i], maxEMA_5_10);
         minEMA_5_10 = Math.min(ema_5_10Raw[i], minEMA_5_10);
      }

      addRawY(ema_5_10Raw);
      addRawY(incRaw);
   }

}
