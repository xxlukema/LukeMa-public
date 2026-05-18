package com.learn;


import org.apache.log4j.Logger;


public class PersonDriver
   extends PersonDriverBase
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();

      queryRecord();

      LOG.debug("Test complete.");
   }

}
