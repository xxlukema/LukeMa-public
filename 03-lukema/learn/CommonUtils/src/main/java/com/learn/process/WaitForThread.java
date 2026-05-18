package com.learn.process;


import org.apache.log4j.Logger;


/**
  * If run ping at infinite time, the output of the process need to be 
  * displayed realtime. That is, whenever this is data in InputStream, 
  * display it.
  * However, Internet Explorer does not allow creation of Threads.
  * The Java Virtual Machine exits when the only threads running are all daemon threads.
  */
public class WaitForThread extends Thread
{
   private static final Logger LOG = Logger.getLogger(WaitForThread.class);

   private Process process = null;

   /**
     * The Java Virtual Machine exits when the only threads running are all daemon threads.
     */
   public WaitForThread(Process p, boolean isDaemon)
   {
      this.process = p;

      setDaemon(isDaemon);
   }

   /**
     * The Java Virtual Machine exits when the only threads running are all daemon threads.
     * Default to user thread, not daemon thread, so that the main process wait until 
     * this thread ends.
     */
   public WaitForThread(Process p)
   {
      this(p, false);
   }

   public void run() 
   {
      try
      {
         LOG.debug("Calling process.waitFor()...");

         process.waitFor();

         LOG.debug("process.waitFor() completed.");
      }
      catch (Throwable t)
      {
         LOG.error("Exception with process.waitFor(): ", t);
      }
   }
}

