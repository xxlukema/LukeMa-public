package com.learn.persistence.service.impl;


import org.apache.log4j.Logger;

import com.learn.persistence.bean.User;
import com.learn.persistence.dao.UserDAO;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


public class UserServiceImpl
   implements UserService
{
   protected static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

   private UserDAO               userDAO;

   public void setUserDAO(UserDAO userDAO)
   {
      this.userDAO = userDAO;
   }

   public UserDAO getUserDAO()
   {
      return userDAO;
   }

   public User getUserByEmail(String email)
      throws AppException
   {
      try
      {
         return userDAO.getUserByEmail(email);
      }
      catch (Exception e)
      {
         throw new AppException(e);
      }
   }

   public User getUserByUsername(String username)
      throws AppException
   {
      try
      {
         return userDAO.getUserByUsername(username);
      }
      catch (Exception e)
      {
         throw new AppException(e);
      }
   }

}
