package com.learn.jsf.util;


import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteFactory;

import org.apache.log4j.Logger;


public class QuoteRunnable
   implements Runnable
{
   protected static final Logger LOG = Logger.getLogger(QuoteRunnable.class);

   private Quote                 quote;
   private String                symbol;

   public void run()
   {
      try
      {
         QuoteFactory quoteFactory = new QuoteFactory();
         quote = quoteFactory.getQuote(symbol);
      }
      catch (Throwable e)
      {
         LOG.error("Exception with quoteFactory.getQuote: " + symbol + " " + e.getMessage());
         e.printStackTrace();
      }
   }

   public void setQuote(Quote quote)
   {
      this.quote = quote;
   }

   public Quote getQuote()
   {
      return quote;
   }

   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }

   public String getSymbol()
   {
      return symbol;
   }

}
