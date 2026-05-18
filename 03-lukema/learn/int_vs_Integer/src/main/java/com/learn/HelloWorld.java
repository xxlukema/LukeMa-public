package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");

      A a = new A();
      int tmpA = a.getAge();
      tmpA = 20;
      LOG.info("A age: " + a.getAge());

      B b = new B();
      Integer tmpB = b.getAge();
      tmpB = 20;
      LOG.info("B age: " + b.getAge());
   }
}

class A
{
   int age = 10;

   public int getAge()
   {
      return age;
   }
}

class B
{
   Integer age = 10;

   public Integer getAge()
   {
      return age;
   }
}


