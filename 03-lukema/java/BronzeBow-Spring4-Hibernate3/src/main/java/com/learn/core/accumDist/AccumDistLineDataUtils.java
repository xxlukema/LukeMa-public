package com.learn.core.accumDist;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class AccumDistLineDataUtils
{
   public static Vector<AccumDistLineData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<AccumDistLineData> data = new Vector<AccumDistLineData>();

      float high = 0;
      float low = 0;
      float close = 0;
      float volume = 0;
      int todayAccumDistLine = 0;
      double todayAccumDistLineIncrease = 0;

      for (RawData todayData : rawDataVector)
      {
         high = todayData.getHigh();
         low = todayData.getLow();
         close = todayData.getClose();
         volume = todayData.getVolume();
         todayAccumDistLineIncrease = ((2.0 * close - high - low) / (high - low)) * volume;
         todayAccumDistLine += (int) todayAccumDistLineIncrease;

         AccumDistLineData accumDistLineData = new AccumDistLineData();
         accumDistLineData.setAccumDistLine(todayAccumDistLine);

         data.add(accumDistLineData);
      }

      return data;
   }

}
