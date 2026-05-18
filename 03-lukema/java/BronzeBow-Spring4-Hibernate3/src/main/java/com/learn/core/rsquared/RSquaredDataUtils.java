package com.learn.core.rsquared;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class RSquaredDataUtils
{
   private static final int DAYS = 5;

   public static Vector<RSquaredData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<RSquaredData> data = new Vector<RSquaredData>();

      int sumX = 0;
      int sumX2 = 0;

      for (int x = 0; x < DAYS; x++)
      {
         sumX += x;
         sumX2 += x * x;
      }

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RSquaredData rsquaredData = new RSquaredData();
         data.add(rsquaredData);

         if (i < DAYS)
         {
            continue;
         }

         float sumY = 0;
         float sumXY = 0;
         double sumY2 = 0;

         for (int x = 0; x < DAYS; x++)
         {
            float y = rawDataVector.get(i - DAYS + x + 1).getClose();

            sumY += y;
            sumXY += x * y;
            sumY2 += y * y;
         }

         rsquaredData.setSlope((float) ((DAYS * sumXY - sumX * sumY) / (DAYS * sumX2 - sumX * sumX)));
         float fenZi = DAYS * sumXY - sumX * sumY;
         rsquaredData.setRsquared((float) (fenZi * fenZi / ((DAYS * sumX2 - sumX * sumX) * (DAYS * sumY2 - sumY * sumY))));
      }

      return data;
   }
}
