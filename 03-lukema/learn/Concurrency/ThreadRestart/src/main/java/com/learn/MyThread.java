package com.learn;


import org.apache.log4j.Logger;


public class MyThread
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(MyThread.class);

   public void run()
   {
      LOG.info(getName() + " started.");
   }
}
