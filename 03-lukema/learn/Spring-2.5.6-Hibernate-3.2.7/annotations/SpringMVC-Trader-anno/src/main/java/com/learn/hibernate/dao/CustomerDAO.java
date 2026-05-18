package com.learn.hibernate.dao;


import java.util.*;

import com.learn.hibernate.orm.Customer;


public interface CustomerDAO
{
   public List<Customer> getCustomers();

   public void displayCustomers();

   public Customer getCustomerById(Long id);

   public Customer getCustomer()
   throws Exception;

   public Customer saveCustomer(Customer customer)
   throws Exception;

   public Customer updateCustomer(Customer customer);

   public Customer saveOrUpdateCustomer(Customer customer);

   public void deleteCustomer(Long id);

   public void deleteCustomer(Customer customer);

   public void testHQL();
}



