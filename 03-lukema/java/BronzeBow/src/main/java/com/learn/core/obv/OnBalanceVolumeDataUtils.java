package com.learn.core.obv;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class OnBalanceVolumeDataUtils
{
   public static Vector<OnBalanceVolumeData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<OnBalanceVolumeData> data = new Vector<OnBalanceVolumeData>();

      float lastClose = 0;
      float todayClose = 0;
      float lastOBV = 0;
      float todayOBV = 0;

      for (RawData rd : rawDataVector)
      {
         todayClose = rd.getClose();

         OnBalanceVolumeData obvData = new OnBalanceVolumeData();
         data.add(obvData);

         if (data.size() == 1)
         {
            lastClose = todayClose;
            continue;
         }

         if (todayClose > lastClose)
         {
            todayOBV = lastOBV + rd.getVolume() / 1000;
         }
         else if (todayClose < lastClose)
         {
            todayOBV = lastOBV - rd.getVolume() / 1000;
         }
         else
         {
            todayOBV = lastOBV;
         }

         obvData.setObv(todayOBV);
         lastOBV = todayOBV;
         lastClose = todayClose;
      }

      return data;
   }
}
