package com.learn.daoFactory;


import com.learn.dao.AccountDAO;
import com.learn.dao.CustomerDAO;
import com.learn.dao.OracleAccountDAO;
import com.learn.dao.OracleCustomerDAO;


public class OracleDAOFactory
   extends DAOFactory
{

   public CustomerDAO getCustomerDAO()
   {
      return new OracleCustomerDAO(this);
   }

   public AccountDAO getAccountDAO()
   {
      return new OracleAccountDAO(this);
   }
}
