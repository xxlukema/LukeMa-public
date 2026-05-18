package com.learn.service;


import com.learn.bean.Customer;


public interface CustomerService
{
   public Customer getCustomer()
      throws AppException;
}
