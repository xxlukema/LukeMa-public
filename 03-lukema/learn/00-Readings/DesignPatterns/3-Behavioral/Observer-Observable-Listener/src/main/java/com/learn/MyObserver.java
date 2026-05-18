package com.learn;


import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;


public class MyObserver implements Observer
{
   private static final Logger LOG = Logger.getLogger(MyObserver.class);

   
   public void update(Observable observable, Object obj)
   {
	   if(obj instanceof Date)
	   {
	      LOG.info("Observed Date: " + ((Date) obj).toString());
	   } 
	   else if (obj instanceof String)
	   {
	      LOG.info("Observed String: " + ((String) obj));
	   }
   }
}
