package com.learn.staticLock;


public class MyThread1
   extends Thread
{
   public void run()
   {
      StaticLockedObject.staticSleepOne();
   }
}
