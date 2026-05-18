package com.learn.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.learn.bean.Customer;
import com.learn.bean.PortfolioItem;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.service.TradeService;
import com.learn.util.SpringBeanFactory;


public class PortfolioController
   implements Controller
{
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
   {
      CustomerService customerService = SpringBeanFactory.getBean("customerService");
      TradeService tradeService = SpringBeanFactory.getBean("tradeService");

      Customer customer = null;

      try
      {
         customer = customerService.getCustomer();
      }
      catch (AppException ae)
      {
         return new ModelAndView("Error500");
      }

      Float cash = customer.getPortfolio().getCash();
      List<PortfolioItem> portfolioItems = tradeService.getInitializedPortfolioItems(customer);

      Map<String, Object> model = new HashMap<String, Object>();

      model.put("cash", cash);
      model.put("portfolioItems", portfolioItems);

      return new ModelAndView("Portfolio", "model", model);
   }

}
