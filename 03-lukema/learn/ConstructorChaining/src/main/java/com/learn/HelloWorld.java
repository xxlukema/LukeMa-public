package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");
   }
}
