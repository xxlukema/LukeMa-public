package com.learn;


import org.apache.log4j.Logger;


public class MyUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler
{
   private static final Logger LOG = Logger.getLogger(MyUncaughtExceptionHandler.class);

   public void uncaughtException(Thread t, Throwable e)
   {
      LOG.error("################# Thread name: " + t.getName());

      LOG.error("################# Throwable: " + e);
   }


}
