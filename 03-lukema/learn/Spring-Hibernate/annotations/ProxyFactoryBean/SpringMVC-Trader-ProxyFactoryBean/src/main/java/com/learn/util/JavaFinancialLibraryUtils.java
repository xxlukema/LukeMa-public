package com.learn.util;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;

public class JavaFinancialLibraryUtils
{
   public static Quote getQuote(String symbol)
   {
      try
      {
         QuoteFactory quoteFactory = new QuoteFactory();
         return quoteFactory.getQuote(symbol);
      }
      catch (QuoteException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   public static boolean symbolIsValid(String symbol)
   {
      if (symbol == null)
      {
         return false;
      }

      symbol = symbol.trim();
      
      if (symbol.equals(""))
      {
         return false;
      }
      
      try
      {
         getQuote(symbol);
         return true;
      }
      catch (RuntimeException e)
      {
         return false;
      }
   }
}
