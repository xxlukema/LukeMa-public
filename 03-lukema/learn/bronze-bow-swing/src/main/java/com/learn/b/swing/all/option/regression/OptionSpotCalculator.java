package com.learn.b.swing.all.option.regression;


public class OptionSpotCalculator
{
   public static void main(String[] args)
   {
      if (args.length < 1)
      {
         System.out.println("\n\tUsage: java OptionDataRetriever symbol [-v]\n");
         System.exit(1);
      }

      String symbol = args[0];
      boolean isVerbose = false;
      boolean calculateOneMonthOnly = false;
      boolean printTraderProfit = false;

      for (int i = 1; i < args.length; i++)
      {
         String arg = args[i].trim().toUpperCase();

         if (arg.startsWith("-V"))
         {
            isVerbose = true;
         }

         if (arg.startsWith("-P"))
         {
            printTraderProfit = true;
         }

         if (arg.equals("1") || arg.equals("0"))
         {
            calculateOneMonthOnly = true;
         }
      }

      OptionDataProcessor odc = new OptionDataProcessor(symbol, isVerbose, calculateOneMonthOnly, printTraderProfit);
      odc.solveForSpotPrint();
   }
}
