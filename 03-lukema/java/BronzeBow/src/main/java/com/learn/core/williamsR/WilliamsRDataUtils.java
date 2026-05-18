package com.learn.core.williamsR;


import java.util.Vector;

import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;
import com.learn.core.raw.RawData;


public class WilliamsRDataUtils
{
   private static final int DAYS = ChartConstants.HALF_CYCLE;

   public static Vector<WilliamsRData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<WilliamsRData> data = new Vector<WilliamsRData>();

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RawData todayData = rawDataVector.get(i);

         WilliamsRData williamsData = new WilliamsRData();
         data.add(williamsData);

         if (i < DAYS - 1)
         {
            continue;
         }

         float highestHigh = Integer.MIN_VALUE;
         float lowestLow = Integer.MAX_VALUE;

         for (int k = 0; k < DAYS; k++)
         {
            RawData rd = rawDataVector.get(i - k);
            highestHigh = Math.max(highestHigh, rd.getHigh());
            lowestLow = Math.min(lowestLow, rd.getLow());
         }

         // williamsData.williams = (float) ((highestHigh-todayData.getClose())* (-100.0) / (highestHigh - lowestLow));

         // 100 + williams' %R.
         float tmpWilliams = (float) ((todayData.getClose() - lowestLow) * (100.0) / (highestHigh - lowestLow));
         williamsData.setWilliams(MbaUtils.limitValueToMinMax(tmpWilliams, 0, 100));
      }

      return data;
   }
}
