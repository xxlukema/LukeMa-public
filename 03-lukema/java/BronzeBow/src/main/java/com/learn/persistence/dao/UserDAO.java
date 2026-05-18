package com.learn.persistence.dao;


import com.learn.persistence.bean.User;


public interface UserDAO
   extends CommonDAO
{
   public User getUserByUsername(String username)
      throws Exception;

   public User getUserByEmail(String email)
      throws Exception;
}
