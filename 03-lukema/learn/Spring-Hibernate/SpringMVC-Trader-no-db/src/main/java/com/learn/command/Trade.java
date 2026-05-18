package com.learn.command;

public class Trade
{
   public static final boolean BUY  = true;

   public static final boolean SELL = false;

   private boolean             buySell;

   private String              symbol;

   private int                 shares;

   private float               price;

   public boolean isBuySell()
   {
      return buySell;
   }

   public void setBuySell(boolean buySell)
   {
      this.buySell = buySell;
   }

   public float getPrice()
   {
      return price;
   }

   public void setPrice(float price)
   {
      this.price = price;
   }

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
}
