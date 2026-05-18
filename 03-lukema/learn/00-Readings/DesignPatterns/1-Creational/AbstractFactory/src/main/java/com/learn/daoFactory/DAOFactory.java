package com.learn.daoFactory;


import com.learn.dao.AccountDAO;
import com.learn.dao.CustomerDAO;


public abstract class DAOFactory
{
   public abstract CustomerDAO getCustomerDAO();

   public abstract AccountDAO getAccountDAO();

   public static DAOFactory getDAOFactory(DAOFactoryType whichFactory)
   {
      switch (whichFactory)
      {
         case MySqlDAOFactory:
            return new MySqlDAOFactory();
         case OracleDAOFactory:
            return new OracleDAOFactory();
         case SqlServerDAOFactory:
            return new SqlServerDAOFactory();
         default:
            return null;
      }
   }
}
