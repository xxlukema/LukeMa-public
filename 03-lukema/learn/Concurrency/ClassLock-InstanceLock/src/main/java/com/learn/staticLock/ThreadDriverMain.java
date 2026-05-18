package com.learn.staticLock;


public class ThreadDriverMain
{
   public static void main(String[] args)
   {
      MyThread1 myThread1 = new MyThread1();
      MyThread1 myThread2 = new MyThread1();

      myThread1.start();
      myThread2.start();
   }
}
