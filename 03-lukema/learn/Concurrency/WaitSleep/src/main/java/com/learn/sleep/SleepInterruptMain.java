package com.learn.sleep;

public class SleepInterruptMain
{
   public static void main(String[] args)
   {
      MySleepThread myWaitThread = new MySleepThread();
      MyInterruptThread myNotifyAllThread = new MyInterruptThread(myWaitThread);
      
      myWaitThread.start();
      myNotifyAllThread.start();
   }
}
