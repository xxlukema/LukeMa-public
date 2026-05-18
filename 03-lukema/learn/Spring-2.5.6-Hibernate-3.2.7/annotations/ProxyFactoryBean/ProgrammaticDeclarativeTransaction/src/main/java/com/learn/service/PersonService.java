package com.learn.service;


import com.learn.dao.PersonDAO;


public interface PersonService
{
   /**
    * Setter is invoked in SpringBeanConfig.xml
    */
   public void setPersonDAO(PersonDAO personDAO);

   /**
    * Throws AppException to cause automatic roll back. See interceptor config.
    */
   public void updatePersonRollback()
      throws Exception;

   /**
    * Throws Exception will NOT cause automatic roll back. See interceptor config.
    */
   public void updatePersonCommit()
      throws Exception;
}
