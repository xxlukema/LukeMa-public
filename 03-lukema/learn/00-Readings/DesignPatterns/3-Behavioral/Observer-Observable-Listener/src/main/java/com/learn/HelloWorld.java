package com.learn;


import java.util.Date;
import java.util.Observer;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");
      
      Observer observer = new MyObserver();
      
      MyObservable myObservable = new MyObservable();
      
      myObservable.addObserver(observer);
      
      myObservable.setReady();
      myObservable.notifyObservers(new Date());
      
      myObservable.setReady();
      myObservable.notifyObservers("Hello");
      
      
      
   }
}
