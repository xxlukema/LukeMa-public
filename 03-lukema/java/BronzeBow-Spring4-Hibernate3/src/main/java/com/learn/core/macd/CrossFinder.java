package com.learn.core.macd;
/*package macd;

import java.io.*;
import java.util.*;
import common.*;


public class CrossFinder extends TraderBase
{
   private boolean isOriginalVersion = true;


   public static Vector getDataVector()
   {
      return MACDData.getInstance().getDataVector();
   }

   public void doTrades()
   {
      int size = getDataVector().size();

      if(size > 2)
      {
         MACDData yesterdayData = (MACDData) getDataVector().get(size-2);
         MACDData todayData     = (MACDData) getDataVector().get(size-1);

         String symbol = getSymbol();
         if(symbol.indexOf("data") == 0)
         {
            symbol = symbol.substring(5);
         }

         symbol = symbol.toUpperCase();

         String output = "                "+Utility.rpadSpace(symbol, 10);
  
         if(isCrossingUp(yesterdayData, todayData))
         {
            
            System.out.println("-------------yesterday: ");        
            System.out.println(yesterdayData.toString());
            System.out.println("-------------today: ");        
            System.out.println(todayData.toString());
            
            System.out.println(output+"^^^   CROSSING UP     ^^^ !!!!!!!");
         }
         else if(isCrossingDown(yesterdayData, todayData))
         {
            
            System.out.println("-------------yesterday: ");        
            System.out.println(yesterdayData.toString());
            System.out.println("-------------today: ");        
            System.out.println(todayData.toString());
            
            System.out.println(output+"vvv   CROSSING DOWN   vvv !!!!!!!");
         }
         
         else
         {
            if(todayData.getMACD_12_26() > 0)
            {
               System.out.println(output+"^^^   Trending up     ^^^");
            }
            else
            {
               System.out.println(output+"vvv   Trending down   vvv");
            }
         }
         
      }
      
      else
      {
         System.out.println("### Not enough data ###");
      }
      
   }

   private boolean isCrossingUp(MACDData yesterdayData, MACDData todayData)
   {
      return (yesterdayData.getMACD_12_26() <= 0 && todayData.getMACD_12_26() > 0);
   }

   private boolean isCrossingDown(MACDData yesterdayData, MACDData todayData)
   {
      return (yesterdayData.getMACD_12_26() >= 0 && todayData.getMACD_12_26() < 0);
   }



   public static void main(String [] args)
   {
      processCommandlimeArguments(args);

      RawDataOld.readDataFromFile(getFileName());

      CrossFinder macdTrader = new CrossFinder();
      macdTrader.doTrades();
   }
}

*/