package com.learn.wait;


import org.apache.log4j.Logger;

import com.learn.LockedObject;
import com.learn.sleeper.Sleeper;


public class MyNotifyAllThread
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(MyNotifyAllThread.class);

   public void run()
   {
      LOG.info("Sleep for 2 sec...");
      
      Sleeper.sleepMiliSec(getName(), 2000);

      synchronized(LockedObject.getInstance())
      {
         LockedObject.getInstance().notifyAll();
      }
      
      LOG.info("Notified the target. Waiting thread should proceed.");
   }
}
