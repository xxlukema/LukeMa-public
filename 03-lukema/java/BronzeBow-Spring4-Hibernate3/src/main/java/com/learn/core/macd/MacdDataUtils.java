package com.learn.core.macd;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class MacdDataUtils
{
   /*
   private static final float EMA12_PCT      = (float)0.15;    // 2 / (# periods + 1)
   private static final float EMA26_PCT      = (float)0.075;    // 2 / (26 + 1)
   private static final float EMA9MACD_PCT   = (float)0.20;    // 2 / (# periods + 1)
   */

   // Make it 5X10 days.
   private static final float EMA12_PCT    = (float) 0.3333; // 2 / (# periods + 1)

   private static final float EMA26_PCT    = (float) 0.1818; // 2 / (26 + 1)

   private static final float EMA9MACD_PCT = (float) 0.4210; // 2 / (# periods + 1)

   public static Vector<MacdData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<MacdData> data = new Vector<MacdData>();

      RawData seedRawData = rawDataVector.get(0);
      MacdData seedMACDData = new MacdData();
      data.add(seedMACDData);
      seedMACDData.setEma12(seedRawData.getClose());
      seedMACDData.setEma26(seedRawData.getClose());
      seedMACDData.setMacd_12_26(seedMACDData.getEma12() - seedMACDData.getEma26());
      seedMACDData.setEma9MACD(seedMACDData.getMacd_12_26());

      MacdData yesterdayMACDData = seedMACDData;

      for (int i = 1; i < rawDataVector.size(); i++)
      {
         RawData todayData = rawDataVector.get(i);

         MacdData macdData = new MacdData();
         data.add(macdData);

         macdData.setEma12(EMA12_PCT * todayData.getClose() + (1 - EMA12_PCT) * yesterdayMACDData.getEma12());
         macdData.setEma26(EMA26_PCT * todayData.getClose() + (1 - EMA26_PCT) * yesterdayMACDData.getEma26());
         macdData.setMacd_12_26(macdData.getEma12() - macdData.getEma26());
         macdData.setEma9MACD(EMA9MACD_PCT * macdData.getMacd_12_26() + (1 - EMA9MACD_PCT) * yesterdayMACDData.getMacd_12_26());
         macdData.setDivergence(macdData.getMacd_12_26() - macdData.getEma9MACD());

         yesterdayMACDData = macdData;
      }

      return data;
   }
}
