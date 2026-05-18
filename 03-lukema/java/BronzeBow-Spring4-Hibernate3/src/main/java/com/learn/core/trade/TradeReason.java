package com.learn.core.trade;


public class TradeReason
{
   public static final TradeReason CUT_LOSS     = new TradeReason("CutLoss_###");
   public static final TradeReason REVERSAL     = new TradeReason("Reversal");
   public static final TradeReason CROSS_UP     = new TradeReason("CrossUp");
   public static final TradeReason CROSS_DOWN   = new TradeReason("CrossDown");
   public static final TradeReason LONG_TARGET  = new TradeReason("LongTarget");
   public static final TradeReason SHORT_TARGET = new TradeReason("ShortTarget");
   public static final TradeReason LONG_STOP    = new TradeReason("LongStop");
   public static final TradeReason SHORT_STOP   = new TradeReason("ShortStop");
   public static final TradeReason LIQUIDATE    = new TradeReason("Liquidate");


   private String reason = null;

   private TradeReason(String reason)
   {
      this.reason = reason;
   }

   public String toString()
   {
      return reason;
   }
}
