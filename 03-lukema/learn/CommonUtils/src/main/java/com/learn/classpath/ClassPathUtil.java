package com.learn.classpath;


import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.learn.io.IOResourceManager;


public class ClassPathUtil
{
   private static final Logger LOG = Logger.getLogger(ClassPathUtil.class);

   public static Properties loadProperties(String propertyFileName)
   {
      Properties prop = new Properties();

      InputStream is = newInputStream(propertyFileName);

      if (is != null)
      {
         try
         {
            prop.load(is);
         }
         catch (Throwable th)
         {
            LOG.error("Unable to open the property file from CLASSPATH. " + propertyFileName, th);
         }
         finally
         {
            IOResourceManager.close(is);
         }
      }
      else
      {
         LOG.error("InputStream is null: " + propertyFileName);
      }

      return prop;
   }

   public static InputStream newInputStream(String fileName)
   {
      URL url = getResource(fileName);
      InputStream is = null;

      if (url != null)
      {
         try
         {
            is = url.openStream();
         }
         catch (Throwable th)
         {
            LOG.error("Unable to open the property file from CLASSPATH. " + fileName, th);
         }
      }
      else
      {
         LOG.error("Resource URL is null: " + fileName);
      }

      return is;
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

   public static URL getResource(String fileName)
   {
      if (fileName == null)
      {
         LOG.error("File name is null.");
         return null;
      }

      fileName = fileName.trim();

      if (fileName.length() == 0)
      {
         LOG.error("File name is empty.");
         return null;
      }

      URL url = null;

      ClassLoader cl = ClassPathUtil.class.getClassLoader();
      if (cl != null)
      {
         url = cl.getResource(fileName);
      }
      else
      {
         LOG.error("ClassLoader is null: " + ClassPathUtil.class.getName());
      }

      if (url == null)
      {
         cl = ClassLoader.getSystemClassLoader();
         if (cl != null)
         {
            url = cl.getResource(fileName);
         }
         else
         {
            LOG.error("System ClassLoader is null.");
         }
      }

      if (url == null)
      {
         LOG.error("Resource URL is null: " + fileName);
      }

      return url;
   }
}
