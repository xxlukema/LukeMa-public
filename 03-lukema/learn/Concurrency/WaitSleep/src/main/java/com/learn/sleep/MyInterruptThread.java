package com.learn.sleep;


import org.apache.log4j.Logger;

import com.learn.sleeper.Sleeper;


public class MyInterruptThread
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(MyInterruptThread.class);

   private Thread              threadToBeInterrupted;

   public MyInterruptThread(Thread threadToBeInterrupted)
   {
      this.threadToBeInterrupted = threadToBeInterrupted;
   }

   public void run()
   {
      LOG.info("Sleep for 2 sec...");

      Sleeper.sleepMiliSec(getName(), 2000);

      threadToBeInterrupted.interrupt();

      LOG.info("Interrupted the target thread.");
   }
}
