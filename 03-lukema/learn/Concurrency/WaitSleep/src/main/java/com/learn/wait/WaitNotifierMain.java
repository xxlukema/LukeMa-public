package com.learn.wait;

public class WaitNotifierMain
{
   public static void main(String[] args)
   {
      MyWaitThread myWaitThread = new MyWaitThread();
      MyNotifyAllThread myNotifyAllThread = new MyNotifyAllThread();
      
      myWaitThread.start();
      myNotifyAllThread.start();
   }
}
