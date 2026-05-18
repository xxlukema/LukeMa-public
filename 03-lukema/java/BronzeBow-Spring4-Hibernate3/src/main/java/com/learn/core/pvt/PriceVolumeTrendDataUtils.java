package com.learn.core.pvt;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class PriceVolumeTrendDataUtils
{
   public static Vector<PriceVolumeTrendData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<PriceVolumeTrendData> data = new Vector<PriceVolumeTrendData>();

      float lastClose = 0;
      float todayClose = 0;
      float lastPVT = 0;
      float todayPVT = 0;

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         RawData rd = rawDataVector.get(i);
         todayClose = rd.getClose();

         PriceVolumeTrendData pvtData = new PriceVolumeTrendData();
         data.add(pvtData);

         if (i == 0)
         {
            lastClose = todayClose;
            continue;
         }

         todayPVT = lastPVT + (todayClose / lastClose - 1) * rd.getVolume() / 1000;

         pvtData.setPvt(todayPVT);
         lastPVT = todayPVT;
         lastClose = todayClose;
      }

      return data;
   }

}
