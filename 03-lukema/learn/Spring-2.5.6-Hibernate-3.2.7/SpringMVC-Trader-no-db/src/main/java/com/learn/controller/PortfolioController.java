package com.learn.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.learn.model.Portfolio;
import com.learn.model.PortfolioItemBean;


public class PortfolioController implements Controller
{
   private Portfolio portfolio = null;

   public PortfolioController(Portfolio portfolio)
   {
      this.portfolio = portfolio;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
   {
      Map model = new HashMap();

      List portfolioItems = getPortfolioItems();

      model.put("cash", portfolio.getCash() + "");
      model.put("portfolioItems", portfolioItems);

      return new ModelAndView("Portfolio", "model", model);
   }

   private List getPortfolioItems()
   {
      List portfolioItems = new ArrayList();

      Iterator symbolIter = portfolio.getSymbolIterator();

      while (symbolIter.hasNext())
      {
         String symbol = (String) symbolIter.next();

         int shares = portfolio.getNumberOfShares(symbol);
         QuoteFactory quoteFactory = new QuoteFactory();

         Quote quote = null;

         try
         {
            quote = quoteFactory.getQuote(symbol);
         }
         catch (QuoteException e)
         {
            quote = new Quote(this.getClass().getName())
            {
            };
         }

         PortfolioItemBean portfolioItem = new PortfolioItemBean();
         portfolioItem.setSymbol(symbol);
         portfolioItem.setShares(shares);
         portfolioItem.setQuote(quote);
         portfolioItem.setCurrentValue(shares * quote.getValue());
         portfolioItem.setGainLoss(shares * quote.getPctChange());
         portfolioItems.add(portfolioItem);
      }

      return portfolioItems;
   }
}

