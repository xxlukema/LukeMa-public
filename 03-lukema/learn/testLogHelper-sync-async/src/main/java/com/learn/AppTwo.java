package com.learn;


import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class AppTwo 
{
   private static final Logger LOGGER    = Logger.getLogger(AppTwo.class);
   //private static final LogHelper LOGGER = new LogHelper(AppTwo.class);

   public static void main( String[] args )
   {
      testLog();
   }

   public static void testLog()
   {
      for (;;)
      {
         LOGGER.info("222222222");

         try
         {
            Thread.sleep(200);
         }
         catch (Throwable t)
         {
            t.printStackTrace();
         }
      }
   }
}
