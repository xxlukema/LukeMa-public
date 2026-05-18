package com.learn.b.swing.all.common;


public class Reason
{
   public static final Reason CUT_LOSS     = new Reason("CutLoss_###");
   public static final Reason REVERSAL     = new Reason("Reversal");
   public static final Reason CROSS_UP     = new Reason("CrossUp");
   public static final Reason CROSS_DOWN   = new Reason("CrossDown");
   public static final Reason LONG_TARGET  = new Reason("LongTarget");
   public static final Reason SHORT_TARGET = new Reason("ShortTarget");
   public static final Reason LONG_STOP    = new Reason("LongStop");
   public static final Reason SHORT_STOP   = new Reason("ShortStop");
   public static final Reason LIQUIDATE    = new Reason("Liquidate");


   private String reason = null;

   private Reason(String reason)
   {
      this.reason = reason;
   }

   public String toString()
   {
      return reason;
   }
}
