package com.learn.dao;


import com.learn.bean.Customer;


public interface CustomerDAO
   extends CommonDAO
{
   public Customer getCustomerByUsername(String username);
}
