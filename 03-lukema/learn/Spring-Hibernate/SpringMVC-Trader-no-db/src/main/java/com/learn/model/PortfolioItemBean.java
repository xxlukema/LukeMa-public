package com.learn.model;


import net.neurotech.quotes.Quote;


public class PortfolioItemBean
{
   private String symbol;

   private int    shares;

   private Quote  quote;

   private double currentValue;

   private double gainLoss;

   public int getShares()
   {
      return shares;
   }

   public void setShares(int quantity)
   {
      this.shares = quantity;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }

   public double getCurrentValue()
   {
      return currentValue;
   }

   public void setCurrentValue(double currentValue)
   {
      this.currentValue = currentValue;
   }

   public Quote getQuote()
   {
      return quote;
   }

   public void setQuote(Quote quote)
   {
      this.quote = quote;
   }

   public double getGainLoss()
   {
      return gainLoss;
   }

   public void setGainLoss(double valueChange)
   {
      this.gainLoss = valueChange;
   }
}
