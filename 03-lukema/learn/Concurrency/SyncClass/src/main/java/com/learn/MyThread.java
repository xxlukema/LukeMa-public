package com.learn;


public class MyThread
   extends Thread
{
   public void run()
   {
      new LockedObject().print();
   }

}
