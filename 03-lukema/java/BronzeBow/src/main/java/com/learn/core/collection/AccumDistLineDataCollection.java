package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.AccumDistLineDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.accumDist.AccumDistLineData;
import com.learn.core.accumDist.AccumDistLineDataUtils;
import com.learn.core.raw.RawData;


public class AccumDistLineDataCollection
   extends DataCollectionBase
{
   private AccumDistLineDomainData accumDistLineDomainData;

   public AccumDistLineDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      accumDistLineDomainData.setAccumDistLine(getAdjustedY().get(0));
   }

   protected void setDomainData()
   {
      accumDistLineDomainData = new AccumDistLineDomainData();
      setDomainData(accumDistLineDomainData);
   }

   public boolean isKUint()
   {
      return true;
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] accumDistLineRaw = new float[len];
      accumDistLineDomainData.setAccumDistLineRaw(accumDistLineRaw);

      Vector<AccumDistLineData> data = AccumDistLineDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         accumDistLineRaw[i] = data.get(k).getAccumDistLine() / 1000;
      }

      addRawY(accumDistLineRaw);
   }

}
