package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.RSquaredDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.raw.RawData;
import com.learn.core.rsquared.RSquaredData;
import com.learn.core.rsquared.RSquaredDataUtils;


public class RSquaredDataCollection
   extends DataCollectionBase
{
   private RSquaredDomainData rSquaredDomainData;

   public RSquaredDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      rSquaredDomainData.setSlope(getAdjustedY().get(0));
      rSquaredDomainData.setRsquared(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      rSquaredDomainData = new RSquaredDomainData();
      setDomainData(rSquaredDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] slopeRaw = new float[len];
      float[] rsquaredRaw = new float[len];

      rSquaredDomainData.setSlopeRaw(slopeRaw);
      rSquaredDomainData.setRsquaredRaw(rsquaredRaw);

      Vector<RSquaredData> data = RSquaredDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         slopeRaw[i] = data.get(k).getSlope();
         rsquaredRaw[i] = data.get(k).getRsquared();
      }

      float min = Integer.MAX_VALUE;
      float max = Integer.MIN_VALUE;
      float slopeMin = Integer.MAX_VALUE;
      float slopeMax = Integer.MIN_VALUE;

      // Find zero point
      for (int i = 0; i < slopeRaw.length; i++)
      {
         min = Math.min(min, slopeRaw[i]);
         min = Math.min(min, rsquaredRaw[i]);

         max = Math.max(max, slopeRaw[i]);
         max = Math.max(max, rsquaredRaw[i]);

         slopeMin = Math.min(min, slopeRaw[i]);
         slopeMax = Math.max(max, slopeRaw[i]);
      }

      float slopeFactor = Math.max(Math.abs(slopeMin), Math.abs(slopeMax));

      if (slopeFactor > 1)
      {
         for (int i = 0; i < slopeRaw.length; i++)
         {
            slopeRaw[i] = (float) (slopeRaw[i] / slopeFactor);
         }
      }

      addRawY(slopeRaw);
      addRawY(rsquaredRaw);
   }

}
