package com.learn.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neurotech.quotes.Quote;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.learn.bean.Customer;
import com.learn.command.Trade;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.service.TradeService;
import com.learn.util.JavaFinancialLibraryUtils;
import com.learn.util.SpringApplicationContext;


public class TradeFormController
   extends AbstractWizardFormController
{
   private static final Logger LOG = Logger.getLogger(TradeFormController.class);

   public TradeFormController()
   {
      setPages(new String[] { "Trade", "TradeConfirm" });
      setCommandName("trade");
   }

   @Override
   protected Object formBackingObject(HttpServletRequest request)
   {
      LOG.info("Entering function.");

      Trade trade = new Trade();
      trade.setBuySell(Trade.SELL);
      return trade;
   }

   @Override
   protected void onBind(HttpServletRequest request, Object command, BindException errors)
   {
      LOG.info("Entering function.");

      Trade trade = (Trade) command;

      if (JavaFinancialLibraryUtils.symbolIsValid(trade.getSymbol()))
      {
         Quote quote = JavaFinancialLibraryUtils.getQuote(trade.getSymbol());
         trade.setPrice(quote.getValue());
         trade.setSymbol(trade.getSymbol().toUpperCase());
      }
      else
      {
         errors.rejectValue("symbol", "error.trade.invalid-symbol", new Object[] { trade.getSymbol() }, "Invalid ticker symbol.");
      }
   }

   @Override
   protected void validatePage(Object command, Errors errors, int page)
   {
      LOG.info("Entering function.");

      LOG.info("Page number: " + page);

      Trade trade = (Trade) command;

      CustomerService customerService = SpringApplicationContext.getBean("customerService");
      TradeService tradeService = SpringApplicationContext.getBean("tradeService");

      Customer customer = null;

      try
      {
         customer = customerService.getCustomer();
      }
      catch (AppException ae)
      {
         errors.reject("error.trade.exception", ae.getMessage());
      }

      if (tradeService.tradeIsBuy(trade))
      {
         if (tradeService.insufficientFunds(customer, trade))
         {
            errors.reject("error.trade.insufficient-funds", "Insufficient funds.");
         }
      }
      else
      {
         if (!tradeService.ownStock(customer, trade.getSymbol()))
         {
            errors.rejectValue("symbol", "error.trade.dont-own", "You don't own this stock.");
         }
         else if (tradeService.notEnoughShares(customer, trade))
         {
            errors.rejectValue("quantity", "error.trade.not-enough-shares", "Not enough shares.");
         }
      }
   }

   @Override
   protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
   {
      LOG.info("Entering function.");

      Trade trade = (Trade) command;

      CustomerService customerService = SpringApplicationContext.getBean("customerService");
      TradeService tradeService = SpringApplicationContext.getBean("tradeService");

      Customer customer = null;

      try
      {
         customer = customerService.getCustomer();
         if (tradeService.tradeIsBuy(trade))
         {
            tradeService.buyStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
         }
         else
         {
            tradeService.sellStock(customer, trade.getSymbol(), trade.getShares(), trade.getPrice());
         }

         return new ModelAndView("TradeAcknowledge", "trade", trade);
      }
      catch (AppException ae)
      {
         LOG.error("Exception with data transaction: " + ae.getMessage());

         trade.setException(ae.getMessage());
         return new ModelAndView("TradeException", "trade", trade);
      }
   }

   @Override
   protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
   {
      LOG.info("Entering function.");

      return new ModelAndView(new RedirectView("portfolio.go"));
   }

}
