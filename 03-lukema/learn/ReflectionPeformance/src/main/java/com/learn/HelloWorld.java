package com.learn;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class HelloWorld
{
   public static void main(String[] args)
      throws Exception
   {
      System.out.println("Hello World!");

      int counter = 1000000;
      
      long start = System.currentTimeMillis();

      for (int i = 0; i < counter; i++)
      {
         reflection();
      }

      long end = System.currentTimeMillis();

      System.out.println();
      System.out.println("Time milisec: " + (end - start));
      System.out.println("Nanosec per call (1 construct+ 4 get invokes): " + (double) (end - start) * 1000 / counter );

      for (int i = 0; i < counter; i++)
      {
         direct();
      }

      long end2 = System.currentTimeMillis();

      System.out.println();
      System.out.println("Time milisec: " + (end2 - end));
      System.out.println("Nanosec per call (1 construct+ 4 get invokes): " + (double) (end2 - end) * 1000 / counter );

   }

   public static void direct()
      throws Exception
   {
      Bean bean = new Bean();
      bean.getClass();
      bean.getI();
      bean.getObject();
      bean.getStr();
   }

   public static void reflection()
      throws Exception
   {
      ClassLoader classLoader = ClassLoader.getSystemClassLoader();

      Class<?> clazz = classLoader.loadClass(Bean.class.getName());
      if (clazz == null)
      {
         throw new Exception("Bean.class not found.");
      }

      Constructor<?> constructor = clazz.getConstructor((Class<?>[]) null);
      Object object = constructor.newInstance((Object[]) null);

      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods)
      {
         String name = method.getName();
         if (name.startsWith("get"))
         {
            method.invoke(object, (Object[]) null);
         }
      }
   }
}
