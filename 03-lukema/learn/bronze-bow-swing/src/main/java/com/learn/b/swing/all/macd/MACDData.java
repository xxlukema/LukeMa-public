package com.learn.b.swing.all.macd;


public class MACDData
{

   private float ema12;

   private float ema26;

   private float macd_12_26;

   private float ema9MACD;

   private float divergence;

   public float getEma12()
   {
      return ema12;
   }

   public void setEma12(float ema12)
   {
      this.ema12 = ema12;
   }

   public float getEma26()
   {
      return ema26;
   }

   public void setEma26(float ema26)
   {
      this.ema26 = ema26;
   }

   public float getMacd_12_26()
   {
      return macd_12_26;
   }

   public void setMacd_12_26(float macd_12_26)
   {
      this.macd_12_26 = macd_12_26;
   }

   public float getEma9MACD()
   {
      return ema9MACD;
   }

   public void setEma9MACD(float ema9macd)
   {
      ema9MACD = ema9macd;
   }

   public float getDivergence()
   {
      return divergence;
   }

   public void setDivergence(float divergence)
   {
      this.divergence = divergence;
   }

}
