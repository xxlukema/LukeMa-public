package com.learn;


import java.io.Serializable;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      String str = returnString().toString();

      LOG.info("str = " + str);

      Long aLong = Long.valueOf(returnLong().toString());

      LOG.info("aLong = " + aLong);

      String key = returnKey().toString();

      LOG.info("key: " + key);
   }

   public static Serializable returnString()
   {
      return "Hello World!";
   }

   public static Serializable returnLong()
   {
      return 12345;
   }

   public static Serializable returnKey()
   {
      Key key = new Key();
      key.setAge(8L);
      key.setName("Candice");

      return key;
   }
}


class Key
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   private String            name;

   private Long              age;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Long getAge()
   {
      return age;
   }

   public void setAge(Long age)
   {
      this.age = age;
   }

   public String toString()
   {
      return "name = " + getName() + ", age = " + getAge();
   }
}
