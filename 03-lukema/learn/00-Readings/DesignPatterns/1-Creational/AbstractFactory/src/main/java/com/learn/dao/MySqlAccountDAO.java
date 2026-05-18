package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class MySqlAccountDAO
   extends AccountDAO
{
   public MySqlAccountDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }
}
