package com.learn;


import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class HelloWorldTest
{
   private static final Logger LOG = Logger.getLogger(HelloWorldTest.class);

   @Before
   public void before()
      throws Exception
   {
      LOG.info("before(). For each test.");
   }

   @After
   public void after()
      throws Exception
   {
      LOG.info("after(). For each test.");
   }

   @BeforeClass
   public static void beforeClass()
      throws Exception
   {
      LOG.info("beforeClass(). Once for the class.");
   }

   @AfterClass
   public static void afterClass()
      throws Exception
   {
      LOG.info("afterClass(). Once for the class.");
   }

   @Test
   public void method()
   {
      LOG.info("method()");
      Assert.assertTrue(new ArrayList<Object>().isEmpty());
   }

   @Test
   @Ignore
   public void ignore()
   {
      LOG.info("method()");
      Assert.assertTrue(new ArrayList<Object>().isEmpty());
   }

   /* @Test
   public int withReturn()
   {
      LOG.info("withReturn()");

      return 0;
   }*/

   @Test(expected = IndexOutOfBoundsException.class)
   public void outOfBounds()
   {
      LOG.info("outOfBounds()");
      new ArrayList<Object>().get(1);
   }

   @Ignore
   @Test(expected = IndexOutOfBoundsException.class)
   public void noThrow()
   {
      LOG.info("noThrow()");
   }

   @Ignore
   @Test
   public void unexpectedThrow()
   {
      LOG.info("unexpectedThrow()");
      new ArrayList<Object>().get(1);
   }

   @Ignore
   @Test(timeout = 100)
   public void infinity()
   {
      LOG.info("infinity(). Begin.");
      while (true)
      {
      }
   }
}
