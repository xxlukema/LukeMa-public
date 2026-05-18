package com.learn.core.chaikin;


public class ChaikinOscillatorData
{
   private float ema5;

   private float ema10;

   private float ema5_ema10;

   private float dimensionless;

   public float getEma5()
   {
      return ema5;
   }

   public void setEma5(float ema5)
   {
      this.ema5 = ema5;
   }

   public float getEma10()
   {
      return ema10;
   }

   public void setEma10(float ema10)
   {
      this.ema10 = ema10;
   }

   public float getEma5_ema10()
   {
      return ema5_ema10;
   }

   public void setEma5_ema10(float ema5Ema10)
   {
      ema5_ema10 = ema5Ema10;
   }

   public float getDimensionless()
   {
      return dimensionless;
   }

   public void setDimensionless(float dimensionless)
   {
      this.dimensionless = dimensionless;
   }

}
