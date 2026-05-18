package com.learn;

import com.learn.nonStaticLock.MyThreadOne;
import com.learn.staticLock.MyThread1;


public class ThreadDriverMain
{
   public static void main(String[] args)
   {
      MyThreadOne myThreadOne = new MyThreadOne();
      MyThread1 myThread1 = new MyThread1();

      myThreadOne.start();
      myThread1.start();
   }
}
