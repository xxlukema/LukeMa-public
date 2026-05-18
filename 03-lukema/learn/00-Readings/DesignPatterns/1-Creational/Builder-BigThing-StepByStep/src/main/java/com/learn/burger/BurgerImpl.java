package com.learn.burger;


import org.apache.log4j.Logger;


public abstract class BurgerImpl
   implements Burger
{
   private static final Logger LOG = Logger.getLogger(BurgerImpl.class);

   public abstract BurgerType getType();
   
   public abstract float getPrice();
   
   public void printBurger()
   {
      LOG.info("Burger: " + getType() + ". Price: " + getPrice());
   }
}
