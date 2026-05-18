package com.learn.service;


public interface StoredProcedureService
{
   /**
    * Throws RuntimeException to cause automatic roll back (default).
    */
   public void updatePersonRollback(float weight);

   /**
    * Throws Exception will NOT cause automatic roll back. 
    */
   public void updatePersonCommit(float weight)
      throws Exception;
}
