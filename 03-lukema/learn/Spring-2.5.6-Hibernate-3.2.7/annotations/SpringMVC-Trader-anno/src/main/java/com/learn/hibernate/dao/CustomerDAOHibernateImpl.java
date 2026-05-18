package com.learn.hibernate.dao;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.apache.log4j.Logger;

import com.learn.hibernate.orm.Customer;



/**
 * Priorities: 
 * The annotation closest to the method has the highest priority.
 * Class level annotation has less priority than the method level annotaion.
 * Descriptives in the advisor has the least priority.
 *
 * Defaults:
 * The default propagartion is PROPAGATION_REQUIRED.
 *
 */
// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Transactional(readOnly = false)
public class CustomerDAOHibernateImpl 
extends HibernateDaoSupport 
implements CustomerDAO
{
   private static final Logger LOG = Logger.getLogger(CustomerDAOHibernateImpl.class);

   @Transactional(readOnly = true)
   public void displayCustomers()
   {
      List<Customer> c = getCustomers();

      LOG.debug("Number of records in customer table: " + c.size());

      for (Customer w : c)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }

   @Transactional(readOnly = false, rollbackFor = MySQLException.class)
   public Customer getCustomer()
   throws Exception
   {
      List<Customer> list = getCustomers();
      if (list.size() == 0)
      {
         Customer c = new Customer();
         c.setUsr("guest");
         c.setPasswd("guest");

         return saveCustomer(c);
      }
      else
      {
         return list.get(0);
      }
   }

   public List<Customer> getCustomers()
   {
      return getHibernateTemplate().loadAll(Customer.class);
   }

   public Customer getCustomerById(Long id)
   {
      return(Customer) getHibernateTemplate().get(Customer.class, id); 
   }

   // The annotation closest to the method has the highest priority.
   // @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
   @Transactional(readOnly = false, rollbackFor = MySQLException.class)
   public Customer saveCustomer(Customer customer)
   throws Exception
   {
      getHibernateTemplate().save(customer);

      // Throw RuntimeException will cause rollback as defined in advisor.
      // throw new RuntimeException("Test Rollback.");

      // Throw MySQLException will cause rollback as defined in method annotation.
      // throw new MySQLException("Test Rollback.");

      // Throw Exception will NOT cause rollback as defined in advisor.
      // throw new Exception("Test Rollback.");

      return customer;
   }

   public Customer saveOrUpdateCustomer(Customer customer)
   {
      getHibernateTemplate().saveOrUpdate(customer);
      return customer;
   }

   public Customer updateCustomer(Customer customer)
   {
      getHibernateTemplate().update(customer);
      return customer;
   }

   public void deleteCustomer(Long id)
   {
      Customer customer = getCustomerById(id);
      deleteCustomer(customer);
   }

   public void deleteCustomer(Customer customer)
   {
      getHibernateTemplate().delete(customer);
   }

   public void testHQL()
   {
   }
}



