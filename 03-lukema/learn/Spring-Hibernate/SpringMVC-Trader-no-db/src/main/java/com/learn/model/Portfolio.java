package com.learn.model;


import java.util.Iterator;
import java.util.Map;


public class Portfolio
{
   private float cash;

   // maps symbol string to shares
   private Map<String, Integer>   sharesPerSymbol;

   public Portfolio(float cash, Map<String, Integer> sharesPerSymbol)
   {
      this.cash = cash;
      this.sharesPerSymbol = sharesPerSymbol;
   }

   public float getCash()
   {
      return cash;
   }

   public boolean contains(String symbol)
   {
      return sharesPerSymbol.containsKey(symbol);
   }

   public int getNumberOfShares(String symbol)
   {
      if (contains(symbol))
      {
         return sharesPerSymbol.get(symbol);
      }

      return 0;
   }

   public Iterator getSymbolIterator()
   {
      return sharesPerSymbol.keySet().iterator();
   }

   public void buyStock(String symbol, int sharesBought, float purchasePrice)
   {
      cash -= sharesBought * purchasePrice;
      if (sharesPerSymbol.containsKey(symbol))
      {
         int currentShares = getNumberOfShares(symbol);
         sharesPerSymbol.put(symbol, new Integer(currentShares + sharesBought));
      }
      else
      {
         sharesPerSymbol.put(symbol, new Integer(sharesBought));
      }
   }

   public void sellStock(String symbol, int sharesSold, float sellPrice)
   {
      cash += sharesSold * sellPrice;
      int currentShares = getNumberOfShares(symbol);
      int sharesLeft = currentShares - sharesSold;
      if (sharesLeft == 0)
      {
         sharesPerSymbol.remove(symbol);
      }
      else
      {
         sharesPerSymbol.put(symbol, new Integer(sharesLeft));
      }

   }

   public boolean canBuy(int shares, float purchasePrice)
   {
      if ((shares * purchasePrice) <= cash)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}
