package com.learn;

import org.apache.log4j.Logger;


public class Thread2
   extends Thread
{
   private static final Logger LOG = Logger.getLogger(Thread2.class);

   public void run()
   {
      LOG.info("Thread2 started.");
      
      B.getInstance().b2();
      
      LOG.info("Thread2 FINISHED.");
   }

   public void finalize()
   {
      LOG.info("Thread2 Finized.");
   }
}
