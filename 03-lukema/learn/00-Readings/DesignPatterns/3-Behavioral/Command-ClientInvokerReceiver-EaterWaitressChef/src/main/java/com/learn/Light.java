package com.learn;

import org.apache.log4j.Logger;


public class Light
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);
   

   public void turnOn()
   {
      LOG.info("The light is on");
   }

   public void turnOff()
   {
      LOG.info("The light is off");
   }
}
