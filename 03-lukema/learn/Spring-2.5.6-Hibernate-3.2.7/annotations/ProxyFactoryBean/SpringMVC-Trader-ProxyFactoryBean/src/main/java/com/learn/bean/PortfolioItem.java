package com.learn.bean;


import net.neurotech.quotes.Quote;


public class PortfolioItem
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private Portfolio         portfolio;

   private String            symbol;

   private Integer           shares;

   private Quote             quote;

   private double            currentValue;

   private double            gainLoss;

   public void setPortfolio(Portfolio portfolio)
   {
      this.portfolio = portfolio;
   }

   public Portfolio getPortfolio()
   {
      return portfolio;
   }

   public Integer getShares()
   {
      return shares;
   }

   public void setShares(Integer value)
   {
      this.shares = value;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String value)
   {
      this.symbol = value;
   }

   public double getCurrentValue()
   {
      return currentValue;
   }

   public void setCurrentValue(double value)
   {
      this.currentValue = value;
   }

   public Quote getQuote()
   {
      return quote;
   }

   public void setQuote(Quote value)
   {
      this.quote = value;
   }

   public double getGainLoss()
   {
      return gainLoss;
   }

   public void setGainLoss(double value)
   {
      this.gainLoss = value;
   }
}
