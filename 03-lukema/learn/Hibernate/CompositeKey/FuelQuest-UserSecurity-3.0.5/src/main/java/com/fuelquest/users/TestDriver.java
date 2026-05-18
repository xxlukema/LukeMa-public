package com.fuelquest.users;


import org.apache.log4j.Logger;


public class TestDriver
{
   private static final Logger LOG = Logger.getLogger(TestDriver.class);

   public static void main(String[] args)
   {
      LOG.debug("Test begin.");

      UserQuerier.test();

      LOG.debug("Test complete.");
   }
}
