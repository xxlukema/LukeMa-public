package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.CmoDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.cmo.CmoData;
import com.learn.core.cmo.CmoDataUtils;
import com.learn.core.raw.RawData;


public class CmoDataCollection
   extends DataCollectionBase
{
   private CmoDomainData cmoDomainData;

   public CmoDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      cmoDomainData.setCmo05(getAdjustedY().get(0));
      cmoDomainData.setCmo10(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      cmoDomainData = new CmoDomainData();
      setDomainData(cmoDomainData);
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] cmo05Raw = new float[len];
      float[] cmo10Raw = new float[len];

      cmoDomainData.setCmo05Raw(cmo05Raw);
      cmoDomainData.setCmo10Raw(cmo10Raw);

      Vector<CmoData> data05 = CmoDataUtils.getNewDataVector05(getRawDataVector());
      Vector<CmoData> data10 = CmoDataUtils.getNewDataVector10(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         cmo05Raw[i] = data05.get(k).getCmo();
         cmo10Raw[i] = data10.get(k).getCmo();
      }

      addRawY(cmo05Raw);
      addRawY(cmo10Raw);
   }

}
