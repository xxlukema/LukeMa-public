package com.learn.daoFactory;


import com.learn.dao.AccountDAO;
import com.learn.dao.CustomerDAO;
import com.learn.dao.MySqlAccountDAO;
import com.learn.dao.MySqlCustomerDAO;


public class MySqlDAOFactory
   extends DAOFactory
{
   public CustomerDAO getCustomerDAO()
   {
      return new MySqlCustomerDAO(this);
   }

   public AccountDAO getAccountDAO()
   {
      return new MySqlAccountDAO(this);
   }
}
