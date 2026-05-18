package com.learn;


import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class LogThread
implements Runnable
{
   private static final Logger LOGGER = Logger.getLogger(LogThread.class);
   private static int idCounter = 0;
   private int id = 0;

   public LogThread()
   {
      id = idCounter++;
   }

   public void run()
   {
      LOGGER.info(id+": info:   Hello world!");
      LOGGER.debug(id+": debug: Hello world!");
   }
}
