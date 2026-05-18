package com.learn.classpath;


import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;


public class ClassPathInputStream extends ClassPathURL
{
   private static final Logger LOGGER = Logger.getLogger(ClassPathInputStream.class);

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
            LOGGER.error("Unable to open the property file from CLASSPATH. "+fileName);
         }
      }

      return is;
   }
}

