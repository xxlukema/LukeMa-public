package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");
      
      String value = System.getProperty("key");
      
      if(value == null)
      {
         System.setProperty("key", "new value.");
         value = System.getProperty("key");
      }
      
      LOG.info("key = " + value);
   }
}
