package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");

      LOG.info("Current thread: " + Thread.currentThread().getName());
      LOG.info("Current thread group: " + Thread.currentThread().getThreadGroup().getName());

      Thread.currentThread().setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

      callMe();
      callMe();

      LOG.info("HelloWorld finished run.");
   }

   public static void callMe()
   {
      throw new RuntimeException("I am RuntimeException.");
   }


}
