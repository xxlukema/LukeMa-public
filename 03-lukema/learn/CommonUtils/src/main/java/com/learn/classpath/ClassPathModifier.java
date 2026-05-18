package com.learn.classpath;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


public class ClassPathModifier
{
   @SuppressWarnings("unchecked")
   private static final Class[] parameters = new Class[] { URL.class };

   static
   {
      try
      {
         addFile("config");
      }
      catch (IOException e)
      {
         e.printStackTrace();
         System.err.println(e.getMessage());
      }
   }

   public static void printClassPath()
   {
      URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
      URL[] urls = sysloader.getURLs();

      for (int i = 0; i < urls.length; i++)
      {
         System.out.println(urls[i].toString());
      }
   }

   public static void addFile(String s)
      throws IOException
   {
      File f = new File(s);
      addFile(f);
   }

   public static void addFile(File f)
      throws IOException
   {
      addURL(f.toURL());
   }

   public static void addURL(URL url)
      throws IOException
   {
      URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
      Class<URLClassLoader> sysclass = URLClassLoader.class;

      try
      {
         Method method = sysclass.getDeclaredMethod("addURL", parameters);
         method.setAccessible(true);
         method.invoke(sysloader, new Object[] { url });
      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new IOException("Error, could not add URL to system classloader");
      }
   }
}
