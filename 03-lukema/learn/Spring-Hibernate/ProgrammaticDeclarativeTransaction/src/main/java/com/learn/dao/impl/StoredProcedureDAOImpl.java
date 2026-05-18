package com.learn.dao.impl;


import org.apache.log4j.Logger;

import com.learn.dao.MyStoredProcedure;
import com.learn.dao.StoredProcedureDAO;


public class StoredProcedureDAOImpl
   implements StoredProcedureDAO
{
   private static final Logger LOG = Logger.getLogger(StoredProcedureDAOImpl.class);

   private MyStoredProcedure   myStoredProcedure;

   public void setMyStoredProcedure(MyStoredProcedure myStoredProcedure)
   {
      this.myStoredProcedure = myStoredProcedure;
   }

   public MyStoredProcedure getMyStoredProcedure()
   {
      return myStoredProcedure;
   }

   public void callStoredProcedure(float weight)
   {
      LOG.info("Updating weight: " + weight);

      getMyStoredProcedure().execute(weight);

      LOG.info("Weight updated.");
   }

}
