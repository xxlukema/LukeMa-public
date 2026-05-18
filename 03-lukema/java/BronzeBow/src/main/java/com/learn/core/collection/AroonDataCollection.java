package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.AroonDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.aroon.AroonData;
import com.learn.core.aroon.AroonDataUtils;
import com.learn.core.raw.RawData;


public class AroonDataCollection
   extends DataCollectionBase
{
   AroonDomainData aroonDomainData;

   public AroonDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      aroonDomainData.setAroonUp(getAdjustedY().get(0));
      aroonDomainData.setAroonDown(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      aroonDomainData = new AroonDomainData();
      setDomainData(aroonDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] aroonUpRaw = new float[len];
      float[] aroonDownRaw = new float[len];

      aroonDomainData.setAroonUpRaw(aroonUpRaw);
      aroonDomainData.setAroonDownRaw(aroonDownRaw);

      Vector<AroonData> data = AroonDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         aroonUpRaw[i] = data.get(k).getAroonUp();
         aroonDownRaw[i] = data.get(k).getAroonDown();
      }

      addRawY(aroonUpRaw);
      addRawY(aroonDownRaw);
   }

}
