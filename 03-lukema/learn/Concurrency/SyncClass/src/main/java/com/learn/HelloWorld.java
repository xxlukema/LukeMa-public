package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      HelloWorld helloWorld = new HelloWorld();
      helloWorld.test();
   }
   
   public void test()
   {
      boolean isDaemon = Thread.currentThread().isDaemon();

      LOG.info("Hello World! " + isDaemon);

      for (int i = 0; i < 10; i++)
      {
         new MyThread().start();
      }
   }
}
