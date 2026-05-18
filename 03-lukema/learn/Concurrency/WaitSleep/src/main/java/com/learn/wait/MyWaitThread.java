package com.learn.wait;


import org.apache.log4j.Logger;

import com.learn.LockedObject;
import com.learn.sleeper.Sleeper;


public class MyWaitThread
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(MyWaitThread.class);

   public void run()
   {
      try
      {
          synchronized(LockedObject.getInstance())
         {
            LOG.info("Start waiting for 100 sec and release the lock on the object...");

            LockedObject.getInstance().wait(100 * 1000);
         }

         LOG.info("Finished waiting for 100 sec or received notify signal.");
      }
      catch (InterruptedException ie)
      {
         LOG.error("Intterruption detected", ie);
      }
   }
}
