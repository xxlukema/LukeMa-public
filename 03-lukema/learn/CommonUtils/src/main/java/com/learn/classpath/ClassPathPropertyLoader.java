package com.learn.classpath;


import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.learn.io.IOResourceManager;


public class ClassPathPropertyLoader extends ClassPathInputStream
{
   private static final Logger LOG = Logger.getLogger(ClassPathPropertyLoader.class);

   public static Properties load(String propertyFileName)
   {
      Properties  prop  = null;

      InputStream is = newInputStream(propertyFileName);

      if (is != null)
      {
         prop = new Properties();
         try
         {
            prop.load(is);
         }
         catch (Throwable th)
         {
            prop = null;
            LOG.error("Unable to open the property file from CLASSPATH. "+propertyFileName);
         }
         finally
         {
            IOResourceManager.close(is, "Exception closing InputStream.");
         }
      }
      else
      {
         LOG.error("Unable to open the property file from CLASSPATH. "+propertyFileName);
      }

      return prop;
   }

   public static int getPropertyAsIntDefault0(Properties prop, String propName)
   {
      int val = 0;
      String str = prop.getProperty(propName);

      if (str != null)
      {
         try
         {
            val = Integer.parseInt(str.trim());
         }
         catch (Exception e)
         {
         }
      }

      return val;
   }
}

