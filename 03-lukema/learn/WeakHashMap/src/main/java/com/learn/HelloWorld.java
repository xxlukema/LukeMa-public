package com.learn;


import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      WeakHashMap<Integer, WeakReference<FinalizeTester>> weakHashMap = new WeakHashMap<Integer, WeakReference<FinalizeTester>>();

      int counter = 0;

      while (counter < 300)
      {
         LOG.info("Counter: " + counter + ". weakHashMap.size(): " + weakHashMap.size());

         weakHashMap.put(counter, new WeakReference<FinalizeTester>(new FinalizeTester()));
         counter++;

         try
         {
            Thread.sleep(50);
         }
         catch (InterruptedException ie)
         {
            LOG.info("Thread.sleep interrupted", ie);
         }
      }
   }
}
