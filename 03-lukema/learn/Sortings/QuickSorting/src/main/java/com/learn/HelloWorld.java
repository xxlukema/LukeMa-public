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
   {
      sort(array, 0, array.length - 1);

      System.out.println();
      for (int i : array)
      {
         System.out.println(i);
      }
      System.out.println();
   }

   public static void sort(int[] array, int left, int right)
   {
      if (array.length < 2)
      {
         return;
      }

      if (left >= right)
      {
         return;
      }

      if (left == right)
      {
         return;
      }

      if (left == (right - 1))
      {
         if (array[left] > array[right])
         {
            swap(array, left, right);
         }

         return;
      }

      int povit = array[left];
      int i = left + 1;
      int k = right;

      while (true)
      {
         while (array[i] < povit && i < right)
         {
            i++;
         }

         while (array[k] >= povit && k > left + 1)
         {
            k--;
         }

         if (i < k)
         {
            swap(array, i, k);

            continue;
         }

         if (i >= k)
         {
            swap(array, left, k);
            sort(array, left, k);
            sort(array, k, right);

            return;
         }
      }
   }

   public static void swap(int[] array, int a, int b)
   {
      int tmp = array[a];
      array[a] = array[b];
      array[b] = tmp;
   }

}
