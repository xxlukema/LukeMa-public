package com.learn.persistence.service;


import com.learn.persistence.bean.User;


public interface UserService
{
   public User getUserByUsername(String username)
      throws AppException;

   public User getUserByEmail(String email)
      throws AppException;
}
