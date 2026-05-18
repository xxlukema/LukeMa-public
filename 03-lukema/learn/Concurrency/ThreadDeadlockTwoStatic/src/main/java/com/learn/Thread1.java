package com.learn;


import org.apache.log4j.Logger;


public class Thread1
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(Thread1.class);

   public void run()
   {
      LOG.info("Thread1 started.");

      A.a1();

      LOG.info("Thread1 FINISHED.");

   }

   public void finalize()
   {
      LOG.info("Thread1 Finized.");
   }
}
