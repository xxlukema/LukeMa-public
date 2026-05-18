package com.learn.sleep;


import com.learn.sleeper.Sleeper;


public class MySleepThread
   extends Thread
{
   public void run()
   {
      Sleeper.sleepMiliSec(getName(), 100 * 1000);
   }
}
