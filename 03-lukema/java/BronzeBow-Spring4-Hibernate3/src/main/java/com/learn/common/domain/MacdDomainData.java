package com.learn.common.domain;


public class MacdDomainData
   extends DomainDataBase
{
   private static final long serialVersionUID = 1L;

   private float[]           macd_12_26Raw;

   private float[]           incRaw;

   private int[]             macd_12_26;

   private int[]             inc;

   public float[] getMacd_12_26Raw()
   {
      return macd_12_26Raw;
   }

   public void setMacd_12_26Raw(float[] macd_12_26Raw)
   {
      this.macd_12_26Raw = macd_12_26Raw;
   }

   public float[] getIncRaw()
   {
      return incRaw;
   }

   public void setIncRaw(float[] incRaw)
   {
      this.incRaw = incRaw;
   }

   public int[] getMacd_12_26()
   {
      return macd_12_26;
   }

   public void setMacd_12_26(int[] macd_12_26)
   {
      this.macd_12_26 = macd_12_26;
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
