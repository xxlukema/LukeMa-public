package com.learn.nonStaticLock;


public class MyThreadTwo
   extends Thread
{
   public void run()
   {
      NonStaticLockedObject.getInstance().sleepTwo();
   }
}
