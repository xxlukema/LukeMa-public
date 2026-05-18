package com.learn.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.learn.bean.Customer;
import com.learn.bean.PortfolioItem;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.service.TradeService;
import com.learn.util.SpringApplicationContext;


public class PortfolioController
   implements Controller
{
   protected static final Logger LOG = Logger.getLogger(LogonFormController.class);

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
   {
      LOG.info("Entering function.");

      CustomerService customerService = SpringApplicationContext.getBean("customerService");
      TradeService tradeService = SpringApplicationContext.getBean("tradeService");

      Customer customer = null;

      try
      {
         LOG.info("Call getCustomer.");
         customer = customerService.getCustomer();
         LOG.info("Success with call getCustomer.");
      }
      catch (AppException ae)
      {
         LOG.info("Exception with call getCustomer.");

         return new ModelAndView("Error500");
      }

      LOG.info("Calling customer.getPortfolio().getCash()...");
      Float cash = customer.getPortfolio().getCash();
      LOG.info("Success with customer.getPortfolio().getCash(): " + cash);

      List<PortfolioItem> portfolioItems = tradeService.getInitializedPortfolioItems(customer);

      LOG.info("portfolioItems.size(): " + portfolioItems.size());

      Map<String, Object> model = new HashMap<String, Object>();

      model.put("cash", cash);
      model.put("portfolioItems", portfolioItems);

      return new ModelAndView("Portfolio", "model", model);
   }

}
