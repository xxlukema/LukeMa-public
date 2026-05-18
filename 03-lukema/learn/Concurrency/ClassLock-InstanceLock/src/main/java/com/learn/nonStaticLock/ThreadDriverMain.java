package com.learn.nonStaticLock;


public class ThreadDriverMain
{
   public static void main(String[] args)
   {
      MyThreadOne myThreadOne = new MyThreadOne();
      MyThreadTwo myThreadTwo = new MyThreadTwo();

      myThreadOne.start();
      myThreadTwo.start();
   }
}
