package com.learn.transaction.declarative;


import org.apache.log4j.Logger;

import com.learn.ClientBase;
import com.learn.service.PersonService;
import com.learn.util.SpringBeanFactory;


public class DeclarativeTransactionClient
   extends ClientBase
{
   private static final Logger LOG = Logger.getLogger(DeclarativeTransactionClient.class);
   
   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      testUpdateTransaction();

      queryRecords();
   }

   public static void testUpdateTransaction()
      throws Exception
   {
      PersonService personService = SpringBeanFactory.getBean("personService");

      try
      {
         personService.updatePersonCommit();
      }
      catch (Exception e)
      {
         LOG.info("Caught exception: " + e.getMessage());
      }

      try
      {
         personService.updatePersonRollback();
      }
      catch (Exception e)
      {
         LOG.info("Caught exception: " + e.getMessage());
      }
   }

}
