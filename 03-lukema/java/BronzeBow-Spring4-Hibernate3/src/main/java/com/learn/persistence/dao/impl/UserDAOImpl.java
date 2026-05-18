package com.learn.persistence.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Example;

import com.learn.persistence.bean.User;
import com.learn.persistence.dao.UserDAO;


public class UserDAOImpl
   extends CommonDAOImpl
   implements UserDAO
{
   private static final Logger LOG                = Logger.getLogger(UserDAOImpl.class);

   private static final String FindUserByUsername = "findUserByUsername";

   private static final String FindUserByEmail    = "findUserByEmail";

   public User getUserByUsername(String username)
      throws Exception
   {
      LOG.info("Entering function.");

      User user = new User();
      user.setUsername(username);

      Example example = Example.create(user);
      example.ignoreCase();

      @SuppressWarnings("unchecked")
      List<User> list = (List<User>) getHibernateTemplate().findByNamedQueryAndNamedParam(FindUserByUsername, "username", username);

      LOG.info("list.size() = " + list.size());

      if (list.size() == 1)
      {
         user = list.get(0);
         return user;
      }
      else if (list.size() > 1)
      {
         String errorMessage = "User is not unique for username: " + username;
         LOG.error(errorMessage);
         throw new Exception(errorMessage);
      }

      LOG.info("No user found by username. Leaving function.");

      return null;
   }

   public User getUserByEmail(String email)
      throws Exception
   {
      LOG.info("Entering function.");

      User user = new User();
      user.setEmail(email);

      Example example = Example.create(user);
      example.ignoreCase();

      @SuppressWarnings("unchecked")
      List<User> list = (List<User>) getHibernateTemplate().findByNamedQueryAndNamedParam(FindUserByEmail, "email", email);

      LOG.info("list.size() = " + list.size());

      if (list.size() == 1)
      {
         user = list.get(0);
         return user;
      }
      else if (list.size() > 1)
      {
         String errorMessage = "User is not unique for email: " + email;
         LOG.error(errorMessage);
         throw new Exception(errorMessage);
      }

      LOG.info("No user found by email. Leaving function.");

      return null;
   }
}
