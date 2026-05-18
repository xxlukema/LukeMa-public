package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      LOG.info("Hello World!".compareTo("Haha"));

      String[] strs = { "1111", "2222", "3333" };

      new HelloWorld().reverse(strs);

      for (String str : strs)
      {
         LOG.info(str);
      }

   }

   public <T> void reverse(T[] array)
   {
      if (array == null)
      {
         throw new RuntimeException("Array to be reversed can not be null");
      }

      int middle = array.length / 2;
      for (int i = 0; i < middle; i++)
      {
         T tmp = array[i];
         array[i] = array[array.length - 1 - i];
         array[array.length - 1 - i] = tmp;
      }
   }
}
