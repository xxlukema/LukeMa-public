package com.learn.core.macd;
/*package macd;

import java.io.*;
import java.util.*;
import common.*;


public class MACDTrader extends TraderBase
{
   private boolean isOriginalVersion = true;


   public static Vector getDataVector()
   {
      return MACDData.getInstance().getDataVector();
   }

   public void doTrades()
   {
      MACDData yesterdayData = null;
      MACDData todayData = null;

      // start trade 30 days after init date to let EMA26 stablize.
      for(int i=30; i<getDataVector().size(); i++)
      {
         todayData = (MACDData) getDataVector().get(i);

         applyCutLossRule(todayData.getRawData());

         if(yesterdayData != null)
         {
            if(isCrossingUp(yesterdayData, todayData))
            {
               System.out.println("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
               System.out.println(yesterdayData.toString());
               System.out.println("   vs ");
               System.out.println(todayData.toString());
               System.out.println("");

               // Cover the short
               if (isShorting())
               {
                  buyToCloseShort(todayData.getRawData(), Reason.CROSS_UP); 
               }

               // Long
               buyToEnterLong(todayData.getRawData()); 
            }
            else if(isCrossingDown(yesterdayData, todayData))
            {
               System.out.println("\n\nvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
               System.out.println(yesterdayData.toString());
               System.out.println("   vs ");
               System.out.println(todayData.toString());
               System.out.println("");

               // Close the long
               if (isLonging())
               {
                  sellToCloseLong(todayData.getRawData(), Reason.CROSS_DOWN); 
               }

               // Short
               sellToEnterShort(todayData.getRawData()); 
            }
            else
            {
               if (!isOriginalVersion)
               {
                  // Close the position due to reversal.
                  if (isReversal(yesterdayData, todayData))
                  {
                     closePosition(todayData.getRawData(), Reason.REVERSAL);
                  }
               }
            }
         }

         yesterdayData = todayData;
      }

      // Print Trading Summary
      printTradingSummary();

      if (isVerbose())
      {
         MACDData md = (MACDData) getDataVector().get(getDataVector().size()-1);
         System.out.println("\n"+md.toString()+"\n");
      }
   }

   public boolean isReversal(MACDData yesterdayData, MACDData todayData)
   {
      if (
              (todayData.getEMA9MACD() > yesterdayData.getEMA9MACD() && todayData.getMACD_12_26() < yesterdayData.getMACD_12_26())
           || (todayData.getEMA9MACD() < yesterdayData.getEMA9MACD() && todayData.getMACD_12_26() > yesterdayData.getMACD_12_26())
         )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   private boolean isCrossingUp(MACDData yesterdayData, MACDData todayData)
   {
      // System.out.println(todayData.toString());

      return (yesterdayData.getMACD_12_26() <= 0 && todayData.getMACD_12_26() > 0);
   }

   private boolean isCrossingDown(MACDData yesterdayData, MACDData todayData)
   {
      return (yesterdayData.getMACD_12_26() >= 0 && todayData.getMACD_12_26() < 0);
   }

   public void printQuote(String action, MACDData macdData)
   {
      if (isVerbose())
      {
         System.out.println(action + ": " + macdData.getRawData().getDate() + "\t close: " + macdData.getRawData().getClose() 
                            // + "\t +DI: " + macdData.getPlusDI14() 
                            // + "\t -DI: " + macdData.getMinusDI14()
                            + "\t Divergence: " + macdData.getDivergence()
                            );
      }
   }


   public static void main(String [] args)
   {
      processCommandlimeArguments(args);

      RawDataOld.readDataFromFile(getFileName());

      // MACDData.listData();

      MACDTrader macdTrader = new MACDTrader();
      macdTrader.doTrades();
   }
}

*/