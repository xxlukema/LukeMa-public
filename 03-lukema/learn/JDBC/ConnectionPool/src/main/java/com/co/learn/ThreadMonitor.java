package com.co.learn;


import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;


public class ThreadMonitor
implements Runnable
{
   private static final int checkMarkedTimeCycle = 200; 

   private static Logger logger = Logger.getLogger("ThreadMonitor");

   private static int nbrBlockedThreads = 0;
   private boolean isBlocked = false;

   private boolean finish = false;

   private InsertUpdateDeleteTester monitored = null;

   public ThreadMonitor(InsertUpdateDeleteTester obj)
   {
      monitored = obj;
   }

   public void markFinish()
   {
      finish = true;
   }

   public static int getBlockedCounter()
   {
      return nbrBlockedThreads;
   }

   private void decBlockedThreadsCounter()
   {
      if (isBlocked)
      {
         isBlocked = false;
         nbrBlockedThreads--;
      }
   }

   private void incBlockedThreadsCounter()
   {
      if (!isBlocked)
      {
         isBlocked = true;
         nbrBlockedThreads++;
      }
   }

   public void run()
   {
      long blockedTimeMiliSec = 0;

      while (!finish)
      {
         try
         {
            incBlockedThreadsCounter();

            Thread.sleep(checkMarkedTimeCycle);
            blockedTimeMiliSec += checkMarkedTimeCycle;
            //System.out.print("*");

            if (blockedTimeMiliSec > 300000) // 5 minutes
            {
               logger.info(monitored.getId()+" has been blocked for 5 minutes. Time out. Exit.");
               System.exit(1);
            }
         }
         catch (Throwable t)
         {
         }
      }

      decBlockedThreadsCounter();
   }

   public void finalize()
   {
      //logger.info("finalized: "+monitored.getId()+" AAAAAAAAAA ThreadMonitor.");
   }
}

