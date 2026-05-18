package com.learn.service.impl;


import com.learn.dao.StoredProcedureDAO;
import com.learn.service.StoredProcedureService;


public class StoredProcedureServiceImpl
   implements StoredProcedureService
{
   private StoredProcedureDAO storedProcedureDAO;

   public void setStoredProcedureDAO(StoredProcedureDAO storedProcedureDAO)
   {
      this.storedProcedureDAO = storedProcedureDAO;
   }

   public StoredProcedureDAO getStoredProcedureDAO()
   {
      return storedProcedureDAO;
   }

   public void updatePersonRollback(float weight)
   {
      getStoredProcedureDAO().callStoredProcedure(weight);

      throw new RuntimeException("This will cause Rollback");
   }

   public void updatePersonCommit(float weight)
      throws Exception
   {
      getStoredProcedureDAO().callStoredProcedure(weight);

      throw new Exception("This will not affact transaction.");
   }

}
