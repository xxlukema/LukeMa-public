package com.learn.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.springframework.beans.factory.*;
import org.springframework.beans.factory.xml.*;
import org.springframework.core.io.*;

import org.apache.log4j.Logger;

import com.learn.hibernate.orm.Portfolio;
import com.learn.hibernate.orm.PortfolioItem;
import com.learn.hibernate.orm.Customer;
import com.learn.hibernate.dao.PortfolioDAO;
import com.learn.hibernate.dao.PortfolioItemDAO;
import com.learn.hibernate.dao.CustomerDAO;


public class PortfolioController implements Controller
{
   private static final Logger LOG = Logger.getLogger(PortfolioController.class);

   private Portfolio portfolio = null;

   public PortfolioController(Portfolio portfolio)
   {
      this.portfolio = portfolio;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
   {
      Map model = new HashMap();

      List<PortfolioItem> portfolioItems = getPortfolioItems();

      model.put("cash", portfolio.getCash() + "");
      model.put("portfolioItems", portfolioItems);

      return new ModelAndView("Portfolio", "model", model);
   }

   private List<PortfolioItem> getPortfolioItems()
   {
      try
      {
         LOG.info("Creating ClassPathResource...");

         Resource  res = new ClassPathResource("WidgetDAOClient.xml");

         LOG.info("Creating XmlBeanFactory using the above resource...");
         BeanFactory  factory = new XmlBeanFactory(res);

         LOG.info("Calling factory.getBean...");
         final CustomerDAO customerDAO = (CustomerDAO) (factory.getBean("customerDAO"));
         LOG.info("Got the customerDAO bean.");
         /*
         final PortfolioDAO portfolioDAO = (PortfolioDAO) (factory.getBean("portfolioDAO"));
         LOG.info("Got the portfolioDAO bean.");
         final PortfolioItemDAO portfolioItemDAO = (PortfolioItemDAO) (factory.getBean("portfolioItemDAO"));
         LOG.info("Got the portfolioItemDAO bean.");
         */

         Customer c = customerDAO.getCustomer();
         Portfolio p = c.getPortfolio();

         return p.getPortfolioItems();
      }
      catch (Exception e1)
      {
         LOG.error(e1);
      }

      return new ArrayList<PortfolioItem>();
   }
}

