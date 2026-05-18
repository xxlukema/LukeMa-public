package com.learn.nonStaticLock;

import com.learn.sleeper.Sleeper;


public class NonStaticLockedObject
{
   private static final NonStaticLockedObject instance = new NonStaticLockedObject();
   
   private final long SLEEP_TIME_MILISEC = 10 * 1000;
   
   public static NonStaticLockedObject getInstance()
   {
      return instance;
   }

   private NonStaticLockedObject()
   {
   }

   public synchronized void sleepOne()
   {
      Sleeper.sleepMiliSec(getClass().getSimpleName(), SLEEP_TIME_MILISEC);
   }
   
   public void sleepTwo()
   {
      synchronized(this)
      {
         Sleeper.sleepMiliSec(getClass().getSimpleName(), SLEEP_TIME_MILISEC);
      }
   }
}
