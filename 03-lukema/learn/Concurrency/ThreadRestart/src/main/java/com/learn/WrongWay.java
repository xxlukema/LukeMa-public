package com.learn;


public class WrongWay
{
   public static void main(String[] args)
   {
      System.out.println("Hello World!");

      MyThread myThread = new MyThread();
      myThread.start();

      // Wrong: After a thread has finished run() or has been called stop(), trying to start it again. 
      myThread.start();
   }
}
