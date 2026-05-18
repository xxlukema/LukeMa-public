package com.learn.core.bollinger;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class BollingerDataUtils
{
   public static final int halfCycle = 20;

   public static final int D         = 2;

   public static Vector<BollingerData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<BollingerData> data = new Vector<BollingerData>();

      // Middle band
      for (int i = 0; i < rawDataVector.size(); i++)
      {
         BollingerData bollingerData = new BollingerData();
         data.add(bollingerData);

         if (i < halfCycle)
         {
            continue;
         }

         float sumClose = 0;
         for (int k = i - halfCycle; k < i; k++)
         {
            RawData oldRawData = rawDataVector.get(k);
            sumClose += oldRawData.getClose();
         }

         bollingerData.setMiddleBand(sumClose / halfCycle);

         double sumSquare = 0;
         for (int k = i - halfCycle; k < i; k++)
         {
            RawData oldRawData = rawDataVector.get(k);
            sumSquare += Math.pow((oldRawData.getClose() - bollingerData.getMiddleBand()), 2);
         }

         float deviation = (float) Math.pow((sumSquare / halfCycle), 0.5);

         bollingerData.setUpperBand(bollingerData.getMiddleBand() + D * deviation);
         bollingerData.setLowerBand(bollingerData.getMiddleBand() - D * deviation);
      }

      return data;
   }
}
