package com.learn;


import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class AppOne 
{
   private static final Logger LOGGER    = Logger.getLogger(AppOne.class);
   //private static final LogHelper LOGGER = new LogHelper(AppOne.class);

   public static void main( String[] args )
   {
      testLog();
   }

   public static void testLog()
   {
      for (;;)
      {
         LOGGER.info("11111111111111");

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
