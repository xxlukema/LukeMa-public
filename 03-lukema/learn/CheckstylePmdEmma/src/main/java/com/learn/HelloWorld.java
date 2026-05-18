package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public void func()
      throws Exception
   {
      LOG.info("Hello World!");
   }
}
