package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.RsiDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.raw.RawData;
import com.learn.core.rsi.RsiData;
import com.learn.core.rsi.RsiDataUtils;
import com.learn.core.williamsR.WilliamsRData;
import com.learn.core.williamsR.WilliamsRDataUtils;


public class RsiDataCollection
   extends DataCollectionBase
{
   private RsiDomainData rsiDomainData;

   public RsiDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      rsiDomainData.setRsi(getAdjustedY().get(0));
      rsiDomainData.setWilliams(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      rsiDomainData = new RsiDomainData();
      setDomainData(rsiDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] rsiRaw = new float[len];
      float[] williamsRaw = new float[len];

      rsiDomainData.setRsiRaw(rsiRaw);
      rsiDomainData.setWilliamsRaw(williamsRaw);

      Vector<RsiData> rsiData = RsiDataUtils.getNewDataVector(getRawDataVector());
      Vector<WilliamsRData> williamsRData = WilliamsRDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }
         rsiRaw[i] = rsiData.get(k).getRsi();
         williamsRaw[i] = williamsRData.get(k).getWilliams();
      }

      addRawY(rsiRaw);
      addRawY(williamsRaw);
   }

}
