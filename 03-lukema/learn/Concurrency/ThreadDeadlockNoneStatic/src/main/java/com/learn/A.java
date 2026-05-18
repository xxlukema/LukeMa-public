package com.learn;


import org.apache.log4j.Logger;

import com.learn.sleeper.Sleeper;


public class A
{
   private static final Logger LOG      = Logger.getLogger(A.class);

   private static final A      instance = new A();

   private A()
   {
   }

   synchronized public void a1()
   {
      LOG.info("A.a1(): Entering...");

      LOG.info("A.a1(): Thread1 got lock on A. Thread1 sleeping for 1 sec to wait for Thread2 to get lock on B...");
      Sleeper.sleep4OneSec(A.class.getSimpleName());
      LOG.info("A.a1(): Awake.");

      LOG.info("A.a1(): Calling B.b1() while holding lock on A...");
      B.getInstance().b1();
      LOG.info("A.a1(): Back from B.b1().");

      LOG.info("A.a1(): FINISH.");
   }

   synchronized public void a2()
   {
      LOG.info("A.a2(): Entering...");

      LOG.info("A.a2(): FINISH.");
   }

   public static A getInstance()
   {
      return instance;
   }
}
