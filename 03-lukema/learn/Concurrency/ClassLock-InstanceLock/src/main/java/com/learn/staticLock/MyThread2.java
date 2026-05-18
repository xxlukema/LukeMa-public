package com.learn.staticLock;


public class MyThread2
   extends Thread
{
   public void run()
   {
      StaticLockedObject.staticSleepOne();
   }
}
