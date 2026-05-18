package com.learn;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyThreadBase
   implements Runnable
{
   public void run()
   {
      String id = getClass().getSimpleName() + "-" + MyThreadLocal.generateId();
            
      Date d = new Date();
      DateFormat df = new SimpleDateFormat("hh:mm:ss.SSS");
      long startTime = System.currentTimeMillis();
      d.setTime(startTime);
      System.out.println("Starting task " + id + " at " + df.format(d));

      try
      {
         Thread.sleep(4000);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      long endTime = System.currentTimeMillis();
      d.setTime(endTime);
      System.out.println("Ending task " + id + " at " + df.format(d) + " after " + (endTime - startTime) + " milliseconds");
   }
}
