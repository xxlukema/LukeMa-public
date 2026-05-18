package com.learn.classpath;


import java.net.URL;

import org.apache.log4j.Logger;


public class ClassPathResource
{
   private static final Logger LOGGER    = Logger.getLogger(ClassPathResource.class);

   public static URL getResource(String fileName)
   {
      if (fileName == null)
      {
         LOGGER.error("File name is null.");
         return null;
      }

      fileName = fileName.trim();

      if (fileName.length() == 0)
      {
         LOGGER.error("File name is empty.");
         return null;
      }

      URL url = null;

      ClassLoader cl = ClassPathResource.class.getClassLoader();
      if (cl != null)
      {
         url = cl.getResource(fileName);
      }
      else
      {
         LOGGER.error("ClassLoader for " + ClassPathResource.class.getName() + " is null.");
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
            LOGGER.error("Unable to get System ClassLoader.");
         }
      }

      if (url == null)
      {
         LOGGER.error("Unable to open the file from CLASSPATH. "+fileName);
      }

      return url;
   }
}

