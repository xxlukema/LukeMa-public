package com.learn;


public class App
{
   public static void main(String[] args)
   {
      System.out.println("Hello World!");
      doLockTest();
      System.out.println("Hello World Finished!");
   }

   public static void doLockTest()
   {
      Thread t1 = new Thread1();
      t1.setDaemon(false);

      Thread t2 = new Thread2();
      t2.setDaemon(false);

      t1.start();
      t2.start();
   }

}
