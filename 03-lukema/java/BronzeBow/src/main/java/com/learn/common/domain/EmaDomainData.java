package com.learn.common.domain;


public class EmaDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           ema_5_10Raw;

   private float[]           incRaw;

   private int[]             ema_5_10;

   private int[]             inc;

   public float[] getEma_5_10Raw()
   {
      return ema_5_10Raw;
   }

   public void setEma_5_10Raw(float[] ema_5_10Raw)
   {
      this.ema_5_10Raw = ema_5_10Raw;
   }

   public float[] getIncRaw()
   {
      return incRaw;
   }

   public void setIncRaw(float[] incRaw)
   {
      this.incRaw = incRaw;
   }

   public int[] getEma_5_10()
   {
      return ema_5_10;
   }

   public void setEma_5_10(int[] ema_5_10)
   {
      this.ema_5_10 = ema_5_10;
   }

   public int[] getInc()
   {
      return inc;
   }

   public void setInc(int[] inc)
   {
      this.inc = inc;
   }

}
