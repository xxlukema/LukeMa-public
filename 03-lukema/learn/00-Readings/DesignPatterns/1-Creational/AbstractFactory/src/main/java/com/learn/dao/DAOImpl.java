package com.learn.dao;


import org.apache.log4j.Logger;

import com.learn.daoFactory.DAOFactory;


public abstract class DAOImpl
   implements DAO
{
   private static final Logger LOG  = Logger.getLogger(DAOImpl.class);

   public abstract String getType();

   private DAOFactory createdBy;
   
   public DAOImpl(DAOFactory createdBy)
   {
      setCreatedBy(createdBy);
   }
   
   public DAOFactory getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(DAOFactory createdBy)
   {
      this.createdBy = createdBy;
   }

   public void insert()
   {
      LOG.info(getprefix() + "insert().");
   }

   public void update()
   {
      LOG.info(getprefix() + "update().");
   }

   public void delete()
   {
      LOG.info(getprefix() + "delete().");
   }
   
   private String getprefix()
   {
      return "Type: " + getType() + ". Created by: " + getCreatedBy().getClass().getSimpleName() + ". Method: ";
   }
}
