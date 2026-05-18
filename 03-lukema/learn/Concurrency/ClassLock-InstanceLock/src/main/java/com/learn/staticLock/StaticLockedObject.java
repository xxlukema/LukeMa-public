package com.learn.staticLock;


import com.learn.sleeper.Sleeper;


public class StaticLockedObject
{
   private static final long SLEEP_TIME_MILISEC = 10 * 1000;

   private StaticLockedObject()
   {
   }

   public synchronized static void staticSleepOne()
   {
      Sleeper.sleepMiliSec(StaticLockedObject.class.getSimpleName(), SLEEP_TIME_MILISEC);
   }

   public static void staticSleepTwo()
   {
      synchronized (StaticLockedObject.class)
      {
         Sleeper.sleepMiliSec(StaticLockedObject.class.getSimpleName(), SLEEP_TIME_MILISEC);
      }
   }
}
