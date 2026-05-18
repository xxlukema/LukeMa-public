package com.learn;


public class RightWay
{
   public static void main(String[] args)
   {
      System.out.println("Hello World!");

      MyThread myThread = new MyThread();
      myThread.start();

      // Create a new thread instance and run.
      myThread = new MyThread();
      myThread.start();
   }
}
