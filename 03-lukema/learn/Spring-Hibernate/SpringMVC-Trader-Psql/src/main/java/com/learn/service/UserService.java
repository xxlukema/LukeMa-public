package com.learn.service;


import com.learn.bean.Customer;


public interface UserService
{
   public Customer getCustomer(String username)
      throws AppException;
}
