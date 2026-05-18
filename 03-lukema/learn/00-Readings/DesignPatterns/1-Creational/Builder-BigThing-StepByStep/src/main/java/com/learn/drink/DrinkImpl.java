package com.learn.drink;


import org.apache.log4j.Logger;


public abstract class DrinkImpl
   implements Drink
{
   private static final Logger LOG = Logger.getLogger(DrinkImpl.class);

   public abstract DrinkType getType();
   
   public void printDrink()
   {
      LOG.info("Drink: " + getType() + ". Price: " + getPrice());
   }
}
