package com.learn.core.std;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class StandardDeviationDataUtils
{
   private static final int DAYS = 5;

   private static void expend10DayEMAofSTD(Vector<StandardDeviationData> data)
   {
      int days = DAYS * 2;

      float sm = (float) 2.0 / (days + 1);

      float lastEMA = 0;
      float todayEMA = 0;

      for (int i = 1; i < data.size(); i++)
      {
         StandardDeviationData stdData = data.get(i);

         todayEMA = (float) (sm * stdData.getStd() + (1.0 - sm) * lastEMA);

         stdData.setStdEMA(todayEMA);
         lastEMA = todayEMA;

         float vi = stdData.getStd() / stdData.getEMAofSTD();

         int timePeriod = (int) (14 / vi);
         stdData.setTimePeriod(timePeriod);

      }
   }

   public static Vector<StandardDeviationData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<StandardDeviationData> data = new Vector<StandardDeviationData>();

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         StandardDeviationData stdData = new StandardDeviationData();
         data.add(stdData);

         if (i < DAYS)
         {
            continue;
         }

         float sumClose = 0;
         for (int k = i - DAYS + 1; k <= i; k++)
         {
            RawData oldRawData = rawDataVector.get(k);
            sumClose += oldRawData.getClose();
         }

         stdData.setCloseSMA(sumClose / DAYS);

         double sumSquare = 0;
         for (int k = i - DAYS + 1; k <= i; k++)
         {
            RawData oldRawData = rawDataVector.get(k);
            sumSquare += Math.pow((oldRawData.getClose() - stdData.getCloseSMA()), 2);
         }

         float deviation = (float) Math.pow((sumSquare / DAYS), 0.5);

         stdData.setStd(deviation);
      }

      expend10DayEMAofSTD(data);

      return data;
   }
}
