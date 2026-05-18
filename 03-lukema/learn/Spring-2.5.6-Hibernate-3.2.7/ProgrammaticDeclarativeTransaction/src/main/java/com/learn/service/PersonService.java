package com.learn.service;


public interface PersonService
   extends CommonService
{
   /**
    * Throws RuntimeException to cause automatic roll back (default).
    */
   public void updatePersonRollback(String newName);

   /**
    * Throws Exception will NOT cause automatic roll back. 
    */
   public void updatePersonCommit(String newName)
      throws Exception;
}
