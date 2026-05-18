package com.learn;


import org.apache.log4j.Logger;


public class HashCode
{
   private static final Logger LOG = Logger.getLogger(HashCode.class);


   public static void main(String [] args)
   {
      String str1 = "Hello World!";
      String str2 = "Hello World!";

      LOG.info("str1 == str2: " + (str1 == str2));

      Integer int1 = 100;
      Integer int2 = 100;

      LOG.info("int1 == int2: " + (int1 == int2));

      String str3 = new String("Hello World!");
      String str4 = new String("Hello World!");

      LOG.info("str3 == str4: " + (str3 == str4));

      Integer int3 = new Integer(100);
      Integer int4 = new Integer(100);

      LOG.info("int3 == int4: " + (int3 == int4));

      LOG.info("str3.equals(str1): " + str3.equals(str1));

      LOG.info("str1.hashCode() == str2.bashCode(): " + (str1.hashCode() == str2.hashCode()));
      LOG.info("str1.hashCode() == str3.bashCode(): " + (str1.hashCode() == str3.hashCode()));

      LOG.info("int1.hashCode() == int3.bashCode(): " + (int1.hashCode() == int3.hashCode()));
      LOG.info("int1.hashCode() == int3.bashCode(): " + (int1.hashCode() == int3.hashCode()));

   }
}
