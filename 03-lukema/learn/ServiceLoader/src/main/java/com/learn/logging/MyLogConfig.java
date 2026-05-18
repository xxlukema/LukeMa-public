package com.learn.logging;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;


public class MyLogConfig
{
   private static final String LogConfigFileName = "logging.properties";

   static
   {
      LogManager logManager = LogManager.getLogManager();

      InputStream inputStream = null;
      try
      {
         inputStream = ClassPathUtils.newInputStream(LogConfigFileName);
         logManager.readConfiguration(inputStream);
      }
      catch (IOException e)
      {
         System.out.println("Exception reading logging config file: " + LogConfigFileName + ". " + e.getMessage());
      }
      finally
      {
         if (inputStream != null)
         {
            try
            {
               inputStream.close();
            }
            catch (IOException e)
            {
            }
         }
      }
   }
}
