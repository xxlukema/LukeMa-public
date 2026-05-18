package com.learn;


public class ThreadDriveMain
{
   public static void main(String[] args)
   {
      MyThread1 myThread1 = new MyThread1();
      MyThread2 myThread2 = new MyThread2();
      MyThread1 myThread3 = new MyThread1();
      MyThread2 myThread4 = new MyThread2();

      myThread1.start();
      myThread2.start();
      myThread3.start();
      myThread4.start();
   }
}
