package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class MySqlCustomerDAO
   extends CustomerDAO
{
   public MySqlCustomerDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }
}
