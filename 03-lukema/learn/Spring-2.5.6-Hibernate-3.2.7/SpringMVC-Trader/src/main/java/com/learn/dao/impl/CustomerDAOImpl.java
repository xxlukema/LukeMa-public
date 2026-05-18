package com.learn.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;

import com.learn.bean.Customer;
import com.learn.dao.CustomerDAO;


public class CustomerDAOImpl
   extends CommonDAOImpl
   implements CustomerDAO
{
   private static final Logger LOG = Logger.getLogger(CustomerDAOImpl.class);

   public Customer getCustomerByUsername(String username)
   {
      LOG.info("Entering function.");

      Customer customer = new Customer();
      customer.setUsername(username);

      LOG.info("Do getHibernateTemplate().findByExample(customer).");

      @SuppressWarnings("unchecked")
      List<Customer> list = getHibernateTemplate().findByExample(customer);

      LOG.info("list.size() = " + list.size());

      if (list.size() == 1)
      {
         customer = list.get(0);

         // Touch the lazy initializations.
         /**
          * TODO 
          * 
          * Make it work for OpenSessionInViewFilter
          * Now: Use "Touch the lazy initializations" as an alternative for OpenSessionInViewFilter.
          */
         customer.getPortfolio().getPortfolioItems().size();

         return customer;
      }

      if (list.size() > 1)
      {
         LOG.error("More than one Customer for name = " + username);
      }

      LOG.info("No customer found. Leaving function.");

      return null;
   }
}
