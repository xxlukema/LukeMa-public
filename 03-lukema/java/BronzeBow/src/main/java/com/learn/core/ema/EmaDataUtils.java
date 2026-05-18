package com.learn.core.ema;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class EmaDataUtils
{
   private static Vector<EmaData> getNewDataVector(Vector<RawData> rawDataVector, int days)
   {
      Vector<EmaData> data = new Vector<EmaData>();

      float factor = (float) 2.0 / (days + 1);

      float lastEMA = 0;
      float todayEMA = 0;

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RawData rd = rawDataVector.get(i);

         EmaData emaData = new EmaData();
         data.add(emaData);

         if (i < 1)
         {
            emaData.setEma(rd.getClose());
            lastEMA = emaData.getEma();

            continue;
         }

         todayEMA = (float) (factor * rd.getClose() + (1.0 - factor) * lastEMA);

         emaData.setEma(todayEMA);
         lastEMA = todayEMA;
      }

      return data;
   }

   public static Vector<EmaData> getNewDataVector05(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 5);
   }

   public static Vector<EmaData> getNewDataVector10(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 10);
   }

   public static Vector<EmaData> getNewDataVector15(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 15);
   }

   public static Vector<EmaData> getNewDataVector25(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 25);
   }

   public static Vector<EmaData> getNewDataVector50(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 50);
   }

}
