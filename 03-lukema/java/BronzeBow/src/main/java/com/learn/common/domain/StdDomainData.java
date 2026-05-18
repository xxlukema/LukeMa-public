package com.learn.common.domain;


public class StdDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           stdRaw;

   private int[]             std;

   public float[] getStdRaw()
   {
      return stdRaw;
   }

   public void setStdRaw(float[] stdRaw)
   {
      this.stdRaw = stdRaw;
   }

   public int[] getStd()
   {
      return std;
   }

   public void setStd(int[] std)
   {
      this.std = std;
   }

}
