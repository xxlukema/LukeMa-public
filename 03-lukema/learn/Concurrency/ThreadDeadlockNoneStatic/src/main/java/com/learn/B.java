package com.learn;


import org.apache.log4j.Logger;

import com.learn.sleeper.Sleeper;


public class B
{
   private static final Logger LOG      = Logger.getLogger(B.class);

   private static final B      instance = new B();

   private B()
   {
   }

   synchronized public void b1()
   {
      LOG.info("B.b1(): Entering...");

      LOG.info("B.b1(): FINISH.");
   }

   synchronized public void b2()
   {
      LOG.info("B.b2(): Entering...");

      LOG.info("B.b2(): Thread2 got lock on B. Thread2 sleeping for 1 sec to wait for Thread1 to lock on A...");
      Sleeper.sleep4OneSec(B.class.getSimpleName());
      LOG.info("B.b2(): Awake.");

      LOG.info("B.b2(): Calling A.a2() while holding lock on B...");
      A.getInstance().a2();
      LOG.info("B.b2(): Back from A.a2().");

      LOG.info("B.b2(): FINISH.");
   }

   public static B getInstance()
   {
      return instance;
   }
}
