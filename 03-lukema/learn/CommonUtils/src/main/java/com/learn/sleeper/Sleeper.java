package com.learn.sleeper;


import org.apache.log4j.Logger;


public class Sleeper
{
   private static final Logger LOG = Logger.getLogger(Sleeper.class);

   public static void sleep4OneSec(String threadName)
   {
      sleepMiliSec(threadName, 1000);
   }

   public static void sleepMiliSec(String threadName, long milisec)
   {
      LOG.info(threadName + ": Sleeping for " + milisec + " milisec...");

      try
      {
         Thread.sleep(milisec);
         LOG.info(threadName + ": Done with sleeping.");
      }
      catch (InterruptedException e)
      {
         LOG.error(threadName + " sleep interrupted", e);
         LOG.info(threadName + ": Out of sleeping.");
      }
   }
}
