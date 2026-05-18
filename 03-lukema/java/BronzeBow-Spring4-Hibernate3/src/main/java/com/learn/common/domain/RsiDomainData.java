package com.learn.common.domain;


public class RsiDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           rsiRaw;

   private float[]           williamsRaw;

   private int[]             rsi;

   private int[]             williams;

   public float[] getRsiRaw()
   {
      return rsiRaw;
   }

   public void setRsiRaw(float[] rsiRaw)
   {
      this.rsiRaw = rsiRaw;
   }

   public float[] getWilliamsRaw()
   {
      return williamsRaw;
   }

   public void setWilliamsRaw(float[] williamsRaw)
   {
      this.williamsRaw = williamsRaw;
   }

   public int[] getRsi()
   {
      return rsi;
   }

   public void setRsi(int[] rsi)
   {
      this.rsi = rsi;
   }

   public int[] getWilliams()
   {
      return williams;
   }

   public void setWilliams(int[] williams)
   {
      this.williams = williams;
   }
}
