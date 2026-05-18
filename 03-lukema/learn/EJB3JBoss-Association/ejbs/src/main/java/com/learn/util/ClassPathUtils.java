package com.learn.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ClassPathUtils
{
   private static final Logger LOG = Logger.getLogger(ClassPathUtils.class);

   public static Properties loadProperties(String propertyFileName)
      throws IOException
   {
      Properties prop = new Properties();

      InputStream inputStream = newInputStream(propertyFileName);

      if (inputStream != null)
      {
         try
         {
            prop.load(inputStream);
         }
         catch (IOException ioe)
         {
            LOG.error("Unable to open the property file from CLASSPATH. " + propertyFileName, ioe);
            throw ioe;
         }
         finally
         {
            inputStream.close();
         }
      }
      else
      {
         LOG.error("InputStream is null: " + propertyFileName);
      }

      return prop;
   }

   public static InputStream newInputStream(String fileName)
      throws IOException
   {
      URL url = getResource(fileName);
      InputStream inputStream = null;

      if (url != null)
      {
         try
         {
            inputStream = url.openStream();
         }
         catch (IOException ioe)
         {
            LOG.error("Unable to open the property file from CLASSPATH. " + fileName, ioe);
            throw ioe;
         }
      }
      else
      {
         LOG.error("Resource URL is null: " + fileName);
      }

      return inputStream;
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

      ClassLoader classLoader = ClassPathUtils.class.getClassLoader();
      if (classLoader != null)
      {
         url = classLoader.getResource(fileName);
      }
      else
      {
         LOG.error("ClasspathUtils classLoader is null: " + ClassPathUtils.class.getName());
      }

      if (url == null)
      {
         classLoader = ClassLoader.getSystemClassLoader();
         if (classLoader != null)
         {
            url = classLoader.getResource(fileName);
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
