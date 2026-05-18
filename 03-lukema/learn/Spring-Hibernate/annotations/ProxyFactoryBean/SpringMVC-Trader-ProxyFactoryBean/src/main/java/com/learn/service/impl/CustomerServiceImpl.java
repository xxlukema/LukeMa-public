package com.learn.service.impl;


import org.apache.log4j.Logger;

import com.learn.bean.Customer;
import com.learn.bean.Portfolio;
import com.learn.bean.PortfolioItem;
import com.learn.dao.CustomerDAO;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.util.SpringBeanFactory;
import com.learn.util.StringConstants;


public class CustomerServiceImpl
   implements CustomerService
{
   private static final Logger LOG = Logger.getLogger(CustomerServiceImpl.class);

   public Customer getCustomer()
      throws AppException
   {
      Customer customer = getCustomerDAO().getCustomerByUsername(StringConstants.GUEST_USERNAME);

      if (customer == null)
      {
         initCustomer();
         customer = getCustomerDAO().getCustomerByUsername(StringConstants.GUEST_USERNAME);
         if (customer == null)
         {
            throw new RuntimeException("Unable to find customer.");
         }
      }

      return customer;
   }

   private void initCustomer()
   {
      Customer newCcustomer = new Customer();

      newCcustomer.setUsername(StringConstants.GUEST_USERNAME);
      newCcustomer.setPassword(StringConstants.GUEST_PASSWORD);

      Portfolio portfolio = new Portfolio();
      newCcustomer.setPortfolio(portfolio);

      PortfolioItem ibm = new PortfolioItem();
      ibm.setSymbol("IBM");
      ibm.setShares(50);

      PortfolioItem sun = new PortfolioItem();
      sun.setSymbol("CAL");
      sun.setShares(300);

      PortfolioItem dell = new PortfolioItem();
      dell.setSymbol("DELL");
      dell.setShares(200);

      portfolio.setCash(1000.0F);

      ibm.setPortfolio(portfolio);
      sun.setPortfolio(portfolio);
      dell.setPortfolio(portfolio);

      portfolio.getPortfolioItems().add(ibm);
      portfolio.getPortfolioItems().add(sun);
      portfolio.getPortfolioItems().add(dell);

      getCustomerDAO().saveOrUpdate(ibm);
      getCustomerDAO().saveOrUpdate(sun);
      getCustomerDAO().saveOrUpdate(dell);
      getCustomerDAO().saveOrUpdate(portfolio);
      getCustomerDAO().saveOrUpdate(newCcustomer);

      LOG.info("initCustomer() completed.");
   }

   private CustomerDAO getCustomerDAO()
   {
      LOG.info("SpringBeanFactory.getBean(beanName)...");
      CustomerDAO customerDAO = SpringBeanFactory.getBean("customerDAO");
      LOG.info("Got the customerDAO bean.");

      return customerDAO;
   }

}
