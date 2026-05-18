package com.learn;


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

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");
   }
}
