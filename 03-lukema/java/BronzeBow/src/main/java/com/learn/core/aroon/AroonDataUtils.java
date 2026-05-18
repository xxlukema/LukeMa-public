package com.learn.core.aroon;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class AroonDataUtils
{
   private final static int DAYS = 5;

   public static Vector<AroonData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<AroonData> data = new Vector<AroonData>();

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         AroonData aroonData = new AroonData();
         data.add(aroonData);

         if (i < DAYS)
         {
            continue;
         }

         float highestHigh = Integer.MIN_VALUE;
         float lowestLow = Integer.MAX_VALUE;

         int daysSinceHighestHigh = 0;
         int daysSinceLowestLow = 0;

         for (int k = 0; k <= DAYS; k++)
         {
            RawData rd = rawDataVector.get(i - k);
            highestHigh = Math.max(highestHigh, rd.getHigh());
            lowestLow = Math.min(lowestLow, rd.getLow());

            if (highestHigh == rd.getHigh())
            {
               daysSinceHighestHigh = k;
            }

            if (lowestLow == rd.getLow())
            {
               daysSinceLowestLow = k;
            }
         }

         // aroonUpData.aroonUp = (float) ((highestHigh-todayData.getClose())* (-100.0) / (highestHigh - lowestLow));

         // 100 + aroonUp' %R.
         float aroonUp = (float) ((100.0) * ((float) DAYS - (float) daysSinceHighestHigh) / (float) DAYS);
         float aroonDown = (float) ((100.0) * ((float) DAYS - (float) daysSinceLowestLow) / (float) DAYS);

         aroonData.setAroonDown(aroonDown);
         aroonData.setAroonUp(aroonUp);
      }

      return data;
   }

}
