package com.learn;


import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG    = Logger.getLogger(HelloWorld.class);

   protected static final int[]  Array0 = { 9, 8, 7, 6, 5 };

   protected static final int[]  Array1 = { 3, 2, 4, 7, 1 };

   protected static final int[]  Array2 = { 5, 0, 1, 2, 3, 4, 7, 1, 0, 9, 8, 6, 5 };

   protected static final int[]  Array3 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

   protected static final int[]  Array4 = { 2, 3, 4, 7, 1, 0, 9, 8, 6, 5 };

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      sort(Array0);
      sort(Array1);
      sort(Array2);
      sort(Array3);
      sort(Array4);

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
      if (right + 1 - left < 2)
      {
         return;
      }

      if (right + 1 - left == 2)
      {
         if (array[left] > array[right])
         {
            swap(array, left, right);
         }

         return;
      }

      int pos = left + (right + 1 - left) / 2;
      sort(array, left, pos - 1);
      sort(array, pos, right);
      merge(array, left, pos, right);
   }

   public static void merge(int[] array, int left, int pos, int right)
   {
      List<Integer> list = new LinkedList<Integer>();

      int leftCounter = left;
      int rightCounter = pos;
      while (leftCounter < pos)
      {
         if (rightCounter <= right && array[leftCounter] >= array[rightCounter])
         {
            list.add(array[rightCounter++]);
         }
         else
         {
            list.add(array[leftCounter++]);
         }
      }

      while (rightCounter <= right)
      {
         list.add(array[rightCounter++]);
      }

      int index = 0;
      for (int i = left; i <= right; i++)
      {
         array[i] = list.get(index++);
      }
   }

   public static void swap(int[] array, int a, int b)
   {
      int tmp = array[a];
      array[a] = array[b];
      array[b] = tmp;
   }
}
