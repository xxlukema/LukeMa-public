package com.aviall.webservice;

import java.io.*;
import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
extends TestCase
{
   private static final Properties PROP = new Properties();

   static 
   {
      try
      {
         PROP.load(new FileInputStream("target/test-classes/test.properties"));
      }
      catch (Throwable e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Create the test case
    *
    * @param testName name of the test case
    */
   public AppTest( String testName )
   {
      super( testName );
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite()
   {
      return new TestSuite( AppTest.class );
   }

   /**
    * Rigourous Test :-)
    */
   public void testApp()
   {
      // assertTrue( true );

      System.out.println("##############################");

      try
      {
         doTest();
      }
      catch (Throwable e)
      {
         e.printStackTrace();
      }
   }

   private static void doTest()
   throws Exception
   {
      String xmlFileName    = PROP.getProperty("xmlFileName");
      String schemaFileName = PROP.getProperty("schemaFileName");

      try
      {
         App.validate(xmlFileName, schemaFileName);
      }
      catch (Throwable e)
      {
         e.printStackTrace();
      }
   }


   public static void main(String [] args)
   {
      new AppTest("AppTest").testApp();
   }
}
