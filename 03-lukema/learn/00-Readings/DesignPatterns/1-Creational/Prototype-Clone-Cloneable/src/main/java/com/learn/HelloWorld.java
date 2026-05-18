package com.learn;


import junit.framework.Assert;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      ExpensiveToCreateNewObject prototype = ExpensiveToCreateNewObject.getPTOTOTYPE();
      ExpensiveToCreateNewObject clone = prototype.clone();

      LOG.info("prototype name: " + prototype.getClass().getSimpleName());
      LOG.info("prototype expensive resource: " + prototype.getExpensiveResourceShallowCopy());
      LOG.info("prototype cheap resource: " + prototype.getCheapResourceDeepCopy());

      LOG.info("clone name: " + clone.getClass().getSimpleName());
      LOG.info("clone expensive resource: " + clone.getExpensiveResourceShallowCopy());
      LOG.info("clone cheap resource: " + clone.getCheapResourceDeepCopy());

      Assert.assertTrue("x.clone() != x: ", prototype != clone);
      Assert.assertTrue("x.clone().getClass() == x.getClass(): ", prototype.getClass() == clone.getClass());

      /**
       * This might or might not be true:
       */
      // Assert.assertTrue("x.clone().equals(x): ", prototype.equals(clone));
   }
}
