package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class SqlServerCustomerDAO
   extends CustomerDAO
{
   public SqlServerCustomerDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }
}
