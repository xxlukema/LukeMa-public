package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class AccountDAO
   extends DAOImpl
   implements DAO
{
   public AccountDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }

   public String getType()
   {
      return "Account";
   }
}
