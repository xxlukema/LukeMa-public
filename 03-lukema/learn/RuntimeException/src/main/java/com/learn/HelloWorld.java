package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Before throw.");
      throwRE();
      LOG.info("After throw.");
   }

   public static void throwRE()
   {
      throw new RuntimeException("This is my RuntimeException.");
   }

}
