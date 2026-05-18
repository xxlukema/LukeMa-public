package com.learn.dao;


import com.learn.daoFactory.DAOFactory;


public class OracleCustomerDAO
   extends CustomerDAO
{
   public OracleCustomerDAO(DAOFactory createdBy)
   {
      super(createdBy);
   }
}
