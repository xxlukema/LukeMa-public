package com.learn;

import java.util.Random;


public class LockedObject
{
   private static int counter = 0;

   public void print()
   {
      synchronized (LockedObject.class)
      {
         System.out.println("LockedObject: " + counter);

         Random random = new Random();
         
         try
         {
            Thread.sleep(random.nextInt(1000));
         }
         catch (Exception e)
         {
         }

         System.out.println("Finished: " + counter);
         
         counter++;
      }
   }
}
