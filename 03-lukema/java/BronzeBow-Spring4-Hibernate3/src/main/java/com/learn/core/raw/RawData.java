package com.learn.core.raw;


import java.io.Serializable;


public class RawData
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            date;

   private float             open             = 0;

   private float             high             = 0;

   private float             low              = 0;

   private float             close            = 0;

   private float             volume           = 99;

   private float             adjustedClose    = 0;

   public String getDate()
   {
      return date;
   }

   public void setDate(String date)
   {
      this.date = date;
   }

   public float getOpen()
   {
      return open;
   }

   public void setOpen(float open)
   {
      this.open = open;
   }

   public float getHigh()
   {
      return high;
   }

   public void setHigh(float high)
   {
      this.high = high;
   }

   public float getLow()
   {
      return low;
   }

   public void setLow(float low)
   {
      this.low = low;
   }

   public float getClose()
   {
      return close;
   }

   public void setClose(float close)
   {
      this.close = close;
   }

   public float getVolume()
   {
      return volume;
   }

   public void setVolume(float volume)
   {
      this.volume = volume;
   }

   public float getAdjustedClose()
   {
      return adjustedClose;
   }

   public void setAdjustedClose(float adjustedClose)
   {
      this.adjustedClose = adjustedClose;
   }

}
