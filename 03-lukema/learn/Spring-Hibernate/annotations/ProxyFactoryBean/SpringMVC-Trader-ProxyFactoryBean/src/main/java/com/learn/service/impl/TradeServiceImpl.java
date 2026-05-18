package com.learn.service.impl;


import java.util.List;

import net.neurotech.quotes.Quote;

import com.learn.bean.Customer;
import com.learn.bean.Portfolio;
import com.learn.bean.PortfolioItem;
import com.learn.command.Trade;
import com.learn.dao.CustomerDAO;
import com.learn.service.AppException;
import com.learn.service.TradeService;
import com.learn.util.JavaFinancialLibraryUtils;
import com.learn.util.SpringBeanFactory;


public class TradeServiceImpl
   implements TradeService
{
   public boolean ownStock(Customer customer, String symbol)
   {
      Portfolio portfolio = customer.getPortfolio();
      if (portfolio != null)
      {
         for (PortfolioItem portfolioItem : portfolio.getPortfolioItems())
         {
            if (portfolioItem.getSymbol().equalsIgnoreCase(symbol))
            {
               return true;
            }
         }
      }

      return false;
   }

   public int getNumberOfShares(Customer customer, String symbol)
   {
      PortfolioItem portfolioItem = getPortfolioItem(customer, symbol);
      if (portfolioItem != null)
      {
         return portfolioItem.getShares();
      }

      return 0;
   }

   private PortfolioItem getPortfolioItem(Customer customer, String symbol)
   {
      Portfolio portfolio = customer.getPortfolio();
      if (portfolio != null)
      {
         for (PortfolioItem portfolioItem : portfolio.getPortfolioItems())
         {
            if (portfolioItem.getSymbol().equalsIgnoreCase(symbol))
            {
               return portfolioItem;
            }
         }
      }

      return null;
   }

   public void buyStock(Customer customer, String symbol, int sharesBought, float purchasePrice)
      throws AppException
   {
      Portfolio portfolio = customer.getPortfolio();
      if (portfolio != null)
      {
         Float cash = portfolio.getCash();
         cash -= sharesBought * purchasePrice;
         portfolio.setCash(cash);

         PortfolioItem portfolioItem = getPortfolioItem(customer, symbol);

         if (portfolioItem != null)
         {
            portfolioItem.setShares(portfolioItem.getShares() + sharesBought);
         }
         else
         {
            portfolioItem = new PortfolioItem();
            portfolioItem.setSymbol(symbol);
            portfolioItem.setShares(sharesBought);
            portfolio.getPortfolioItems().add(portfolioItem);
         }

         saveOrUpdate(customer);
      }
      
      throw new AppException("You are not allowed to buy stocks. This will test rollback your transaction.");
   }

   private void saveOrUpdate(Customer customer)
   {
      CustomerDAO customerDAO = SpringBeanFactory.getBean("customerDAO");

      customerDAO.saveOrUpdate(customer);
   }

   public void sellStock(Customer customer, String symbol, int sharesSold, float sellPrice)
      throws AppException
   {
      Portfolio portfolio = customer.getPortfolio();
      if (portfolio != null)
      {
         Float cash = portfolio.getCash();
         cash += sharesSold * sellPrice;
         portfolio.setCash(cash);

         PortfolioItem portfolioItem = getPortfolioItem(customer, symbol);

         int currentShares = portfolioItem.getShares();
         int sharesLeft = currentShares - sharesSold;
         if (sharesLeft == 0)
         {
            portfolio.getPortfolioItems().remove(portfolioItem);
         }
         else
         {
            portfolioItem.setShares(sharesLeft);
         }

         saveOrUpdate(customer);
      }
   }

   public boolean canBuy(Customer customer, int shares, float purchasePrice)
   {
      Portfolio portfolio = customer.getPortfolio();
      if (portfolio != null)
      {
         if ((shares * purchasePrice) <= portfolio.getCash())
         {
            return true;
         }
      }

      return false;
   }

   public List<PortfolioItem> getInitializedPortfolioItems(Customer customer)
   {
      Portfolio portfolio = customer.getPortfolio();

      List<PortfolioItem> list = portfolio.getPortfolioItems();

      for (PortfolioItem portfolioItem : list)
      {
         String symbol = portfolioItem.getSymbol();

         int shares = getNumberOfShares(customer, symbol);
         Quote quote = JavaFinancialLibraryUtils.getQuote(symbol);

         portfolioItem.setSymbol(symbol);
         portfolioItem.setShares(shares);
         portfolioItem.setQuote(quote);
         portfolioItem.setCurrentValue(shares * quote.getValue());
         portfolioItem.setGainLoss(shares * quote.getPctChange());
      }

      return list;
   }

   public boolean notEnoughShares(Customer customer, Trade trade)
   {
      return getNumberOfShares(customer, trade.getSymbol()) < trade.getShares();
   }

   public boolean insufficientFunds(Customer customer, Trade trade)
   {
      return !canBuy(customer, trade.getShares(), trade.getPrice());
   }

   public boolean tradeIsBuy(Trade trade)
   {
      return trade.isBuySell() == Trade.BUY;
   }

}
