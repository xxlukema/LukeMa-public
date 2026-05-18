package com.learn.daoFactory;


import com.learn.dao.AccountDAO;
import com.learn.dao.CustomerDAO;
import com.learn.dao.SqlServerAccountDAO;
import com.learn.dao.SqlServerCustomerDAO;


public class SqlServerDAOFactory
   extends DAOFactory
{

   public CustomerDAO getCustomerDAO()
   {
      return new SqlServerCustomerDAO(this);
   }

   public AccountDAO getAccountDAO()
   {
      return new SqlServerAccountDAO(this);
   }
}
