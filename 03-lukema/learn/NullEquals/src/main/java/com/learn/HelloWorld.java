package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");
      LOG.info("null == null ? " + (null == null));
      LOG.info("String.equals(null) ? " + ("String".equals(null)));
   }
}
