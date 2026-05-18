package com.learn;


import org.apache.log4j.Logger;


public abstract class ShapeImpl
   implements Shape
{
   private static final Logger LOG = Logger.getLogger(ShapeImpl.class);

   public abstract ShapeType getType();
   
   public void printShape()
   {
      LOG.info("Shape: " + getType());
   }
}
