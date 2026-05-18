package com.learn;


import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.learn.logging.MyLogConfig;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class.getName());

   static
   {
      try
      {
         Class.forName(MyLogConfig.class.getName());
      }
      catch (ClassNotFoundException e)
      {
         LOG.log(Level.SEVERE, "Unable to load class: " + MyLogConfig.class.getName(), e);
      }
   }

   @MyAnnotation(doSomething = "What to do?")
   public static Hello getService()
   {
      ServiceLoader<Hello> serviceLoader = ServiceLoader.load(Hello.class);
         
      for (Hello hello : serviceLoader)
      {
         LOG.info("Looping: " + hello.getMessage());
      }
      
      for (Hello hello : serviceLoader)
      {
         return hello;
      }

      throw new Error("No Hello registered");
   }

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      Hello hello = getService();

      LOG.info(hello.getMessage());

   }
}
