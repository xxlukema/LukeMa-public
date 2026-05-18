package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.ChaikinOscillatorDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.chaikin.ChaikinOscillatorData;
import com.learn.core.chaikin.ChaikinOscillatorDataUtils;
import com.learn.core.raw.RawData;


public class ChaikinOscillatorDataCollection
   extends DataCollectionBase
{
   private ChaikinOscillatorDomainData chaikinOscillatorDomainData;

   public ChaikinOscillatorDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      chaikinOscillatorDomainData.setDimensionless(getAdjustedY().get(0));
   }

   protected void setDomainData()
   {
      chaikinOscillatorDomainData = new ChaikinOscillatorDomainData();
      setDomainData(chaikinOscillatorDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] dimensionlessRaw = new float[len];
      chaikinOscillatorDomainData.setDimensionlessRaw(dimensionlessRaw);

      Vector<ChaikinOscillatorData> data = ChaikinOscillatorDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         dimensionlessRaw[i] = data.get(k).getDimensionless();
      }

      addRawY(dimensionlessRaw);
   }

}
