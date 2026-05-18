package com.learn.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;


/**
 * Unit test for simple App.
 */
public class AppTest 
extends TestCase
{
   protected static final Logger LOGGER = Logger.getLogger(AppTest.class);

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
      /*
      for (int i=0; i<5; i++)
      {
         Mailer.sendMail("Subject 1", "Message line 1. <br> line 2.");
         Mailer.sendMail("Subject 2", (new Exception("test exception")));
      }
      */
   }
}
