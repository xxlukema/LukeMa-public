package com.learn;


import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");
      
      Date dateNull = null;
      
      if(dateNull instanceof Date)
      {
    	  LOG.info("dateNull instanceof Date is true.");
      }
      else
      {
    	  LOG.info("dateNull instanceof Date is NOT true.");
      }
      
      Date date = new Date();
      
      if(date instanceof Date)
      {
    	  LOG.info("date instanceof Date is true.");
      }
      else
      {
    	  LOG.info("date instanceof Date is NOT true.");
      }

      if(date instanceof Serializable)
      {
    	  LOG.info("date instanceof Serializable is true.");
      }
      else
      {
    	  LOG.info("date instanceof Serializable is NOT true.");
      }
   }
}
