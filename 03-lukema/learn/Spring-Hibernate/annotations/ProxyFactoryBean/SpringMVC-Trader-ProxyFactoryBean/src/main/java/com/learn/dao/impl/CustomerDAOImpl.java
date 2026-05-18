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
      Customer customer = new Customer();
      customer.setUsername(username);

      @SuppressWarnings("unchecked")
      List<Customer> list = getHibernateTemplate().findByExample(customer);

      if (list.size() == 1)
      {
         customer = list.get(0);

         return customer;
      }

      if (list.size() > 1)
      {
         LOG.error("More than one Customer for name = " + username);
      }

      return null;
   }
}
