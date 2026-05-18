package com.learn.test;


import java.util.Date;

import junit.framework.TestCase;


public class TestUtilsTest
   extends TestCase
{

   public void testNewString()
      throws Exception
   {
      String str = TestHelper.newString(TestHelper.getPREFIX());
      System.out.println(str);

      str = TestHelper.newString(null);
      System.out.println(str);
   }

   public void testNewPhoneNumber()
      throws Exception
   {
      String str = TestHelper.newPhoneNumber();
      System.out.println(str);
   }

   public void testRandomArrayElement()
      throws Exception
   {
      Object[] objs = { "Line One.", new Date(), 3 };

      for (int i = 0; i < 20; i++)
      {
         String str = TestHelper.randomArrayElement(objs).toString();
         System.out.println(str);
      }
   }
}
