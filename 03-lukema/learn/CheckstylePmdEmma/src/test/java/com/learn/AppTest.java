package com.learn;


import org.junit.Test;


public class AppTest
{
   @Test
   public void testApp()
      throws Exception
   {
      new HelloWorld().func();
   }
   
   @Test
   public void fail()
      throws Exception
   {
      throw new Exception("Fail here.");
   }
}
