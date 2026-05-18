package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class SqlServerAccountDAO
   extends AccountDAO
{
   public SqlServerAccountDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }
}
