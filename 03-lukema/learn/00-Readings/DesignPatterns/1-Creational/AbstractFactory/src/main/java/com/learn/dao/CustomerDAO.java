package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class CustomerDAO
   extends DAOImpl
   implements DAO
{
   public CustomerDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }

   public String getType()
   {
      return "Customer";
   }
}
