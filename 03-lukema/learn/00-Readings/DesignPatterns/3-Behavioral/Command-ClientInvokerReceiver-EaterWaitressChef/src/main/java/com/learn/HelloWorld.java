package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      Light light = new Light();
      Command switchUp = new TurnOnLightCommand(light);
      Command switchDown = new TurnOffLightCommand(light);

      Switch s = new Switch(switchUp, switchDown);

      s.flipUp();
      s.flipDown();
   }
}
