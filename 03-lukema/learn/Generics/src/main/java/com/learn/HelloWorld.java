package com.learn;


import java.util.*;

import org.apache.log4j.Logger;


public class HelloWorld 
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main( String[] args )
   {
      LOG.info( "Hello World!" );

      iterateList();


      LOG.info("====================================");

      Integer i = dynamicReturnTypeInteger();

      LOG.info(i);

      Float f = dynamicReturnTypeFloat();

      LOG.info(f);

      LOG.info("====================================");

      A a = printType(AA.class);

      B b = printType(B.class);
   }

   public static void iterateList()
   {
      List<String> list = new LinkedList<String>();

      list.add("String one");
      list.add("String two");
      list.add("String three");

      // 1st way
      LOG.info("The first way to iterate the list:");

      for (int i=0; i<list.size(); i++)
      {
         String value = (String) list.get(i);
         LOG.info(value);
      }

      // 2nd way
      LOG.info("The second way to iterate the list:");

      for (Iterator it=list.iterator(); it.hasNext();)
      {
         String value = (String) it.next();
         LOG.info(value);
      }

      // 3rd way
      LOG.info("The third way to iterate the list:");

      for (String str:list)
      {
         LOG.info(str);
      }

      // 4th way
      LOG.info("The fourth way to iterate the list:");

      for (Iterator<String> it=list.iterator(); it.hasNext(); )
      {
         LOG.info(it.next());
      }

      // 5st way
      LOG.info("The fifth way to iterate the list:");

      for (int i=0; i<list.size(); i++)
      {
         String value = list.get(i);
         LOG.info(value);
      }
   }

   @SuppressWarnings("unchecked")
   public static <T> T dynamicReturnTypeInteger()
   {
      return(T) new Integer(1);
   }

   public static <T> T dynamicReturnTypeFloat()
   {
      return (T) new Float(2.3);
   }

   public static <T> T printType(Class<? extends T> clazz)
   {
      LOG.info("class name: " + clazz.getName());

      T t = null;
      
      try
      {
         t = clazz.newInstance();
      }
      catch (IllegalAccessException iae)
      {
         LOG.error(iae);
      }
      catch (InstantiationException ie)
      {
         LOG.error(ie);
      }

      return t;
   }
}


class A
{
   private static final Logger LOG = Logger.getLogger(A.class);

   public A()
   {
      LOG.info("Constructor called.");
   }
}

class AA extends A
{
   private static final Logger LOG = Logger.getLogger(AA.class);

   public AA()
   {
      LOG.info("Constructor called.");
   }
}

class B
{
   private static final Logger LOG = Logger.getLogger(B.class);

   public B()
   {
      LOG.info("Constructor called.");
   }
}


