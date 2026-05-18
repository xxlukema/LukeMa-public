package com.learn.core.rsi;


import java.util.Vector;

import com.learn.common.util.ChartConstants;
import com.learn.core.raw.RawData;


public class RsiDataUtils
{
   private static final int DAYS = ChartConstants.HALF_CYCLE;

   public static Vector<RsiData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<RsiData> data = new Vector<RsiData>();

      float up = 0;
      float down = 0;
      float todayRSI = 0;
      float lastClose = rawDataVector.get(0).getClose();

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RsiData rsiData = new RsiData();
         data.add(rsiData);

         if (i < DAYS)
         {
            continue;
         }

         if (i == DAYS)
         {
            for (int m = 1; m < DAYS + 1; m++)
            {
               float todayClose = rawDataVector.get(m).getClose();
               up += Math.max(0, (todayClose - lastClose));
               down += Math.max(0, (lastClose - todayClose));
               lastClose = todayClose;
            }

            up /= DAYS;
            down /= DAYS;
         }
         else
         {
            float todayClose = rawDataVector.get(i).getClose();
            up = (up * (DAYS - 1) + Math.max(0, (todayClose - lastClose))) / DAYS;
            down = (down * (DAYS - 1) + Math.max(0, (lastClose - todayClose))) / DAYS;
            lastClose = todayClose;
         }

         todayRSI = (float) (up * 100 / (up + down));
         rsiData.setRsi(todayRSI);
      }

      return data;
   }

}
