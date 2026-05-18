package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG    = Logger.getLogger(HelloWorld.class);

   protected static final int[]  Array1 = { 2, 3, 4, 7, 1, 0, 9, 8, 6, 5 };

   protected static final int[]  Array2 = { 5, 0, 1, 2, 3, 4, 7, 1, 0, 9, 8, 6, 5 };

   protected static final int[]  Array3 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      sort(Array1);
      sort(Array2);
      sort(Array3);

   }

   public static void sort(int[] array)
      throws Exception
   {
      LOG.info("Hello World!");

      while (!doSort(array))
      {
      }

      System.out.println();
      for (int i : array)
      {
         System.out.println(i);
      }
      System.out.println();
   }

   public static boolean doSort(int[] array)
   {
      System.out.print('.');

      boolean sorted = true;

      for (int i = 1; i < array.length; i++)
      {
         if (array[i - 1] > array[i])
         {
            int tmp = array[i];
            array[i] = array[i - 1];
            array[i - 1] = tmp;

            sorted = false;
         }
      }

      return sorted;
   }
}
