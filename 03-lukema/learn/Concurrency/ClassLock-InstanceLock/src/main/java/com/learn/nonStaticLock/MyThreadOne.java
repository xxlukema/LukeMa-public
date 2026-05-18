package com.learn.nonStaticLock;


public class MyThreadOne
   extends Thread
{
   public void run()
   {
      NonStaticLockedObject.getInstance().sleepOne();
   }
}
