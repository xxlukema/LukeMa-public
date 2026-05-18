package com.learn;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG      = Logger.getLogger(HelloWorld.class);

   private static final String OUT_DIR  = "target/classes";

   private static final String OUT_FILE = "MyObject.ser";

   public static void main(String[] args)
   {
      LOG.info("Hello World!");
      
      save();
      
      read();
   }

   public static void read()
   {
      FileInputStream fis = null;
      ObjectInputStream ois = null;

      try
      {
         fis = new FileInputStream(OUT_DIR + "/" + OUT_FILE);
         ois = new ObjectInputStream(fis);

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

         if (fis != null)
         {
            try
            {
               fis.close();
            }
            catch (Throwable t)
            {
               LOG.error("Exception closing input resource", t);
            }
         }
      }
   }

   public static void save()
   {
      File dir = new File(OUT_DIR);

      if (dir.exists() && !dir.isDirectory())
      {
         dir.delete();
      }

      if (!dir.exists())
      {
         dir.mkdirs();
      }

      FileOutputStream fos = null;
      ObjectOutputStream oos = null;

      try
      {
         fos = new FileOutputStream(OUT_DIR + "/" + OUT_FILE);
         oos = new ObjectOutputStream(fos);

         MySerializable ms = new MySerializable();
         ms.init();

         oos.writeObject(ms);
      }
      catch (Throwable t)
      {
         LOG.error("Exception saving the object", t);
      }
      finally
      {
         if (oos != null)
         {
            try
            {
               oos.close();
            }
            catch (Throwable t)
            {
               LOG.error("Exception closing output resource", t);
            }
         }

         if (fos != null)
         {
            try
            {
               fos.close();
            }
            catch (Throwable t)
            {
               LOG.error("Exception closing output resource", t);
            }
         }
      }
   }
}
