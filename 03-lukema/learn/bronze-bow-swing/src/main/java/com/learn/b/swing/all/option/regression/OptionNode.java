package com.learn.b.swing.all.option.regression;


public class OptionNode
{
   private String expirationDate   = null;
   private float  strike           = -1;
   private String optionSymbol     = null;
   private float  last             = -1;
   private float  change           = -1;
   private float  bid              = -1;
   private float  ask              = -1;
   private int    volume           = -1;
   private int    openInterest     = -1;

   public OptionNode(String expirationDate, String strike, String optionSymbol, String last, String change, String bid, String ask, String volume, String openInterest)
   {
      this.expirationDate = expirationDate;
      this.strike         = parseFloat(strike);
      this.optionSymbol   = optionSymbol;
      this.last           = parseFloat(last);
      this.change         = parseFloat(change);
      this.bid            = parseFloat(bid);
      this.ask            = parseFloat(ask);
      this.volume         = parseInt(volume);
      this.openInterest   = parseInt(openInterest);
   }

   private int parseInt(String strValue)
   {
      int value = -1;

      if(strValue != null)
      {
         try
         {
            value = Integer.parseInt(strValue.trim().replaceAll("[,]", ""));
         }
         catch (Throwable t)
         {
         }
      }

      return value;
   }

   private float parseFloat(String strValue)
   {
      float value = -1;

      if(strValue != null)
      {
         try
         {
            value = Float.parseFloat(strValue.trim().replaceAll("[,]", ""));
         }
         catch (Throwable t)
         {
         }
      }

      return value;
   }

   public String getExpirationDate()
   {
      return expirationDate;
   }

   public float getStrike()
   {
      return strike;
   }

   public String getOptionSymbol()
   {
      return optionSymbol;
   }

   public float getLast()
   {
      return last;
   }

   public float getChange()
   {
      return change;
   }

   public float getBid()
   {
      return bid;
   }

   public float getAsk()
   {
      return ask;
   }

   public int getVolume()
   {
      return volume;
   }

   public int getOpenInterest()
   {
      return openInterest;
   }

   public String toString()
   {
      String ret = 
          "expirationDate = "+expirationDate+"\n"
         +"strike         = "+strike+"\n"
         +"optionSymbol   = "+optionSymbol+"\n"
         +"last           = "+last+"\n"
         +"change         = "+change+"\n"
         +"bid            = "+bid+"\n"
         +"ask            = "+ask+"\n"
         +"volume         = "+volume+"\n"
         +"openInterest   = "+openInterest+"\n";

      return ret;
   }
}

