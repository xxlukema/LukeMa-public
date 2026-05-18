package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.DmiDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.dmi.DmiData;
import com.learn.core.dmi.DmiDataUtils;
import com.learn.core.raw.RawData;


public class DmiDataCollection
   extends DataCollectionBase
{
   private DmiDomainData dmiDomainData;

   public DmiDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      dmiDomainData.setDiPlus(getAdjustedY().get(0));
      dmiDomainData.setDiMinus(getAdjustedY().get(1));
      dmiDomainData.setAdx(getAdjustedY().get(2));
   }

   protected void setDomainData()
   {
      dmiDomainData = new DmiDomainData();
      setDomainData(dmiDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] diPlusRaw = new float[len];
      float[] diMinusRaw = new float[len];
      float[] adxRaw = new float[len];

      dmiDomainData.setDiPlusRaw(diPlusRaw);
      dmiDomainData.setDiMinusRaw(diMinusRaw);
      dmiDomainData.setAdxRaw(adxRaw);

      Vector<DmiData> data = DmiDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         diPlusRaw[i] = data.get(k).getPlusDI14();
         diMinusRaw[i] = data.get(k).getMinusDI14();
         adxRaw[i] = data.get(k).getAdx();
      }

      addRawY(diPlusRaw);
      addRawY(diMinusRaw);
      addRawY(adxRaw);
   }

}
