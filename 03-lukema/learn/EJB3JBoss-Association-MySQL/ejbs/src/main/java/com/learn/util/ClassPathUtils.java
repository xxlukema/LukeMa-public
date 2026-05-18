package com.learn.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ClassPathUtils
{
   public static InputStream newInputStream(String fileName)
      throws IOException
   {
      URL url = getResource(fileName);

      return url.openStream();
   }

   public static URL getResource(String fileName)
   {
      if (fileName == null)
      {
         return null;
      }

      fileName = fileName.trim();

      if (fileName.length() == 0)
      {
         return null;
      }

      URL url = null;

      ClassLoader classLoader = ClassPathUtils.class.getClassLoader();
      if (classLoader != null)
      {
         url = classLoader.getResource(fileName);
      }

      if (url == null)
      {
         classLoader = ClassLoader.getSystemClassLoader();
         if (classLoader != null)
         {
            url = classLoader.getResource(fileName);
         }
      }

      return url;
   }
}
