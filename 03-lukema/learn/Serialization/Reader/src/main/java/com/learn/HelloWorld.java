package com.learn;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.apache.log4j.Logger;

import com.learn.classpath.ClassPathInputStream;


public class HelloWorld
{
   private static final Logger LOG      = Logger.getLogger(HelloWorld.class);

   private static final String IN_FILE = "MyObject.ser";

   public static void main(String[] args)
   {
      LOG.info("Hello World!");
      
      read();
   }

   public static void read()
   {
      InputStream is = ClassPathInputStream.newInputStream(IN_FILE);
      ObjectInputStream ois = null;

      try
      {
         ois = new ObjectInputStream(is);

         MySerializable ms = (MySerializable) ois.readObject();
         
         Map<String, String> map = ms.getMap();
         
         for(String key : map.keySet())
         {
            LOG.info(key + ": " + map.get(key));
         }
      }
      catch (Throwable t)
      {
         LOG.error("Exception saving the object", t);
      }
      finally
      {
         if (ois != null)
         {
            try
            {
               ois.close();
            }
            catch (Throwable t)
            {
               LOG.error("Exception closing input resource", t);
            }
         }

         if (is != null)
         {
            try
            {
               is.close();
            }
            catch (Throwable t)
            {
               LOG.error("Exception closing input resource", t);
            }
         }
      }
   }
}
