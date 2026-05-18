package com.learn.core.collection;


import java.util.Vector;

import com.learn.common.domain.VolumeDomainData;
import com.learn.common.util.MbaUtils;
import com.learn.core.raw.RawData;
import com.learn.core.volume.VolumeData;
import com.learn.core.volume.VolumeDataUtils;


public class VolumeDataCollection
   extends DataCollectionBase
{
   private VolumeDomainData volumeDomainData;

   protected void setDomainData()
   {
      volumeDomainData = new VolumeDomainData();
      setDomainData(volumeDomainData);
   }

   protected void fillAdjustedY()
   {
      volumeDomainData.setVolume(getAdjustedY().get(0));
   }

   public VolumeDataCollection(Vector<RawData> rawDataVector, int y0Position)
   {
      super(rawDataVector, y0Position);
   }

   public boolean isKUint()
   {
      return true;
   }

   public void loadData()
   {
      int len = MbaUtils.MaxDays;

      float[] rawVolume = new float[len];
      volumeDomainData.setVolumeRaw(rawVolume);

      Vector<VolumeData> data = VolumeDataUtils.getNewDataVector(getRawDataVector());

      for (int i = 0; i < len; i++)
      {
         int k = getRawDataVector().size() - len + i;
         if (k < 0)
         {
            continue;
         }

         rawVolume[i] = data.get(k).getVolume();
      }

      addRawY(rawVolume);
   }

}
