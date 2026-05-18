package com.learn.core.cmo;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class CmoDataUtils
{
   private static Vector<CmoData> getNewDataVector(Vector<RawData> rawDataVector, int days)
   {
      Vector<CmoData> data = new Vector<CmoData>();

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         CmoData cmoData = new CmoData();
         data.add(cmoData);

         if (i < days)
         {
            continue;
         }

         float su = 0;
         float sd = 0;
         float tmp = 0;
         RawData newClose = rawDataVector.get(i);
         RawData oldClose = null;

         for (int k = 1; k <= days; k++)
         {
            oldClose = rawDataVector.get(i - k);
            tmp = newClose.getClose() - oldClose.getClose();
            newClose = oldClose;

            if (tmp > 0)
            {
               su += tmp;
            }
            else
            {
               sd -= tmp;
            }
         }

         if (su == 0 && sd == 0)
         {
            cmoData.setCmo(0.0f);
         }
         else
         {
            cmoData.setCmo((float) (100.0f) * ((su - sd) / (su + sd)));
         }
      }

      return data;
   }

   public static Vector<CmoData> getNewDataVector05(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 5);
   }

   public static Vector<CmoData> getNewDataVector10(Vector<RawData> rawDataVector)
   {
      return getNewDataVector(rawDataVector, 10);
   }

}
