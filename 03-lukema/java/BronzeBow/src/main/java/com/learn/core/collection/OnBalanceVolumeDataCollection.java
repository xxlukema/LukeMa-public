package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.OnBalanceVolumeDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.obv.OnBalanceVolumeData;
import com.learn.core.obv.OnBalanceVolumeDataUtils;
import com.learn.core.pvt.PriceVolumeTrendData;
import com.learn.core.pvt.PriceVolumeTrendDataUtils;
import com.learn.core.raw.RawData;


public class OnBalanceVolumeDataCollection
   extends DataCollectionBase
{
   private OnBalanceVolumeDomainData onBalanceVolumeDomainData;

   public OnBalanceVolumeDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   protected void fillAdjustedY()
   {
      onBalanceVolumeDomainData.setObv(getAdjustedY().get(0));
      onBalanceVolumeDomainData.setPvt(getAdjustedY().get(1));
   }

   protected void setDomainData()
   {
      onBalanceVolumeDomainData = new OnBalanceVolumeDomainData();
      setDomainData(onBalanceVolumeDomainData);
   }

   public boolean isKUint()
   {
      return true;
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] obvRaw = new float[len];
      float[] pvtRaw = new float[len];

      onBalanceVolumeDomainData.setObvRaw(obvRaw);
      onBalanceVolumeDomainData.setPvtRaw(pvtRaw);

      float obvMin = Integer.MAX_VALUE;
      float obvMax = Integer.MIN_VALUE;
      float pvtMin = Integer.MAX_VALUE;
      float pvtMax = Integer.MIN_VALUE;

      Vector<OnBalanceVolumeData> vData = OnBalanceVolumeDataUtils.getNewDataVector(getRawDataVector());
      Vector<PriceVolumeTrendData> pData = PriceVolumeTrendDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         obvRaw[i] = vData.get(k).getObv();
         obvMin = Math.min(obvMin, obvRaw[i]);
         obvMax = Math.max(obvMax, obvRaw[i]);

         pvtRaw[i] = pData.get(k).getPvt();
         pvtMin = Math.min(pvtMin, pvtRaw[i]);
         pvtMax = Math.max(pvtMax, pvtRaw[i]);
      }

      addRawY(obvRaw);

      float k = (obvMax - obvMin) / (pvtMax - pvtMin);

      for (int i = 0; i < len; i++)
      {
         pvtRaw[i] = k * (pvtRaw[i] - pvtMin) + obvMin;
      }

      addRawY(pvtRaw);
   }

}
