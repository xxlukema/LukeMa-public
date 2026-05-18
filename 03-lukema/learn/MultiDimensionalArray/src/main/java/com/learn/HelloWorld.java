package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      String[][] array = { { "one 1", "one 2", "one 3", "one 4" }, { "two 1", "two 2", "two 3", "two 4" } };

      for (String[] subarray : array)
      {
         for (String str : subarray)
         {
            LOG.info(str);
         }
      }

      LOG.info("array.length = " + array.length);
      LOG.info("array[0].length = " + array[0].length);
     
      for(int i=0; i<array.length; i++)
      {
         for(int k=0; k<array[i].length; k++)
         {
            String str = array[i][k];
            LOG.info(str);
         }
      }
   }
}
