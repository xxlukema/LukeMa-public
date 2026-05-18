package com.learn.common.domain;


public class RSquaredDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           slopeRaw;

   private float[]           rsquaredRaw;

   private int[]             slope;

   private int[]             rsquared;

   public float[] getSlopeRaw()
   {
      return slopeRaw;
   }

   public void setSlopeRaw(float[] slopeRaw)
   {
      this.slopeRaw = slopeRaw;
   }

   public float[] getRsquaredRaw()
   {
      return rsquaredRaw;
   }

   public void setRsquaredRaw(float[] rsquaredRaw)
   {
      this.rsquaredRaw = rsquaredRaw;
   }

   public int[] getSlope()
   {
      return slope;
   }

   public void setSlope(int[] slope)
   {
      this.slope = slope;
   }

   public int[] getRsquared()
   {
      return rsquared;
   }

   public void setRsquared(int[] rsquared)
   {
      this.rsquared = rsquared;
   }

}
