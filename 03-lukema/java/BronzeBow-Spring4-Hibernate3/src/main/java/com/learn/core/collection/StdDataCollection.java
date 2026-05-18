package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.StdDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.raw.RawData;
import com.learn.core.std.StandardDeviationData;
import com.learn.core.std.StandardDeviationDataUtils;


public class StdDataCollection
   extends DataCollectionBase
{
   private StdDomainData stdDomainData;

   public StdDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      stdDomainData.setStd(getAdjustedY().get(0));
   }

   protected void setDomainData()
   {
      stdDomainData = new StdDomainData();
      setDomainData(stdDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] stdRaw = new float[len];

      stdDomainData.setStdRaw(stdRaw);

      Vector<StandardDeviationData> data = StandardDeviationDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         stdRaw[i] = data.get(k).getStd();
      }

      addRawY(stdRaw);
   }
}
