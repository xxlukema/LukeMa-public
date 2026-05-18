package com.learn.jsf.util;


import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.learn.common.util.MbaUtils;
import com.learn.persistence.util.SpringServiceFacade;


public class AccessUtils
{
   protected static final Logger LOG                            = Logger.getLogger(SpringServiceFacade.class);

   /**
    * 10 seconds ago.
    */
   private static final long     MilisecBack                    = 10 * 1000;

   /**
    * For last 10 seconds, no more than 200 access allowed from the same IP address.
    */
   private static final long     AlarmThreshold                 = 200;

   private static final long     SleepMilisec                   = 2 * 1000;

   private static final Lock     LOCK                           = new ReentrantLock();

   private static int            databaseTableCleanupCounter    = 0;

   private static int            MaxDatabaseTableCleanupCounter = 5 * 1000;                                   // 100 * 1000;

   public static void checkAccess(String remoteAddress, String symbol)
      throws Exception
   {
      if(remoteAddress == null)
      {
         return;
      }
      
      LOG.info("Entering processRequest()...");

      databaseTableCleanupCounter++;
      if (databaseTableCleanupCounter > MaxDatabaseTableCleanupCounter)
      {
         databaseTableCleanupCounter = 0;

         SpringServiceFacade.doDatabaseHouseKeeping();
      }

      LOG.info(remoteAddress + " requesting for symbol '" + symbol + "'");

      symbol = MbaUtils.formalizeSysmbol(symbol);
      SpringServiceFacade.recordAccess(remoteAddress, symbol);
      LOG.info("Access recorded.");

      if (SpringServiceFacade.isAccessBlocked(remoteAddress))
      {
         LOG.warn("Access is blocked for remoteAddress = " + remoteAddress);

         throw new Exception("Access is blocked.");
      }

      Date from = new Date(System.currentTimeMillis() - MilisecBack);

      if (SpringServiceFacade.getRecentAccessCount(remoteAddress, from) > AlarmThreshold)
      {
         SpringServiceFacade.putToAccessAlarm(remoteAddress, symbol);
         LOG.info("Put to access alarm list.");

         if (LOCK.tryLock())
         {
            try
            {
               Thread.sleep(SleepMilisec);
            }
            finally
            {
               LOCK.unlock();
            }

            LOG.warn("Access is delayed for remoteAddress = " + remoteAddress);
         }
         else
         {
            LOG.warn("Parrall access is skipped for remoteAddress = " + remoteAddress);

            throw new Exception("Server is busy. Please try at a later time.");
         }
      }
   }
}
