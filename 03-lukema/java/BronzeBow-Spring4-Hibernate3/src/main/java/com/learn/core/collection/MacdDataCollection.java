package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.MacdDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.macd.MacdData;
import com.learn.core.macd.MacdDataUtils;
import com.learn.core.raw.RawData;


public class MacdDataCollection
   extends DataCollectionBase
{
   private MacdDomainData macdDomainData;

   public MacdDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      macdDomainData.setMacd_12_26(getAdjustedY().get(0));
      macdDomainData.setInc(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      macdDomainData = new MacdDomainData();
      setDomainData(macdDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] macd_12_26Raw = new float[len];
      float[] incRaw = new float[len];

      macdDomainData.setMacd_12_26Raw(macd_12_26Raw);
      macdDomainData.setIncRaw(incRaw);

      Vector<MacdData> data = MacdDataUtils.getNewDataVector(getRawDataVector());

      macd_12_26Raw[0] = data.get(0).getDivergence();
      incRaw[0] = 0;
      macd_12_26Raw[1] = data.get(1).getDivergence();
      incRaw[1] = macd_12_26Raw[1] - macd_12_26Raw[0];

      float sign = 0;

      for (int i = 2; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         macd_12_26Raw[i] = data.get(k).getDivergence();

         if (macd_12_26Raw[i] == 0)
         {
            sign = 0;
         }
         else
         {
            sign = (float) (macd_12_26Raw[i] / Math.abs(macd_12_26Raw[i]));
         }

         incRaw[i] = macd_12_26Raw[i] - macd_12_26Raw[i - 2];

         if ((incRaw[i] * sign) > 0)
         {
            incRaw[i] = 0;
         }
      }

      float maxMACD_12_26 = Integer.MIN_VALUE;
      float minMACD_12_26 = Integer.MAX_VALUE;

      for (int i = 0; i < macd_12_26Raw.length; i++)
      {
         maxMACD_12_26 = Math.max(macd_12_26Raw[i], maxMACD_12_26);
         minMACD_12_26 = Math.min(macd_12_26Raw[i], minMACD_12_26);
      }

      addRawY(macd_12_26Raw);
      addRawY(incRaw);
   }

}
