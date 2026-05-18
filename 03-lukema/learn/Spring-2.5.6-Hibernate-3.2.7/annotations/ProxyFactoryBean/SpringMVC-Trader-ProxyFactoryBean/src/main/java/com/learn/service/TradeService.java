package com.learn.service;


import java.util.List;

import com.learn.bean.Customer;
import com.learn.bean.PortfolioItem;
import com.learn.command.Trade;


public interface TradeService
{
   public boolean ownStock(Customer customer, String symbol);

   public int getNumberOfShares(Customer customer, String symbol);

   public void buyStock(Customer customer, String symbol, int sharesBought, float purchasePrice)
      throws AppException;

   public void sellStock(Customer customer, String symbol, int sharesSold, float sellPrice)
      throws AppException;

   public boolean canBuy(Customer customer, int shares, float purchasePrice);

   public List<PortfolioItem> getInitializedPortfolioItems(Customer customer);

   public boolean notEnoughShares(Customer customer, Trade trade);

   public boolean insufficientFunds(Customer customer, Trade trade);

   public boolean tradeIsBuy(Trade trade);
}
