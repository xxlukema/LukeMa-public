package com.learn.common.domain;


public class ChaikinOscillatorDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           dimensionlessRaw;

   private int[]             dimensionless;

   public float[] getDimensionlessRaw()
   {
      return dimensionlessRaw;
   }

   public void setDimensionlessRaw(float[] dimensionlessRaw)
   {
      this.dimensionlessRaw = dimensionlessRaw;
   }

   public int[] getDimensionless()
   {
      return dimensionless;
   }

   public void setDimensionless(int[] dimensionless)
   {
      this.dimensionless = dimensionless;
   }

}
