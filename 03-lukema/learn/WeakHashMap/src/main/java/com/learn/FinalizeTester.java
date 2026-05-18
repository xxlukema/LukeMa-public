package com.learn;


import org.apache.log4j.Logger;


public class FinalizeTester
{
   private static final Logger LOG = Logger.getLogger(FinalizeTester.class);

   public void finalize()
   {
      LOG.warn("Avoid using finalize() method to release resources because you do not know when the gc is going to kick in.");
   }
}
