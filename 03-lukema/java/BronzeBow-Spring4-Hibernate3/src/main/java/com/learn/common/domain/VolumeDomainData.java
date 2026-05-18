package com.learn.common.domain;


public class VolumeDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           volumeRaw;

   private int[]             volume;

   public boolean isVolumeData()
   {
      return true;
   }

   public int[] getVolume()
   {
      return volume;
   }

   public void setVolume(int[] volume)
   {
      this.volume = volume;
   }

   public void setVolumeRaw(float[] volumeRaw)
   {
      this.volumeRaw = volumeRaw;
   }

   public float[] getVolumeRaw()
   {
      return volumeRaw;
   }

}
