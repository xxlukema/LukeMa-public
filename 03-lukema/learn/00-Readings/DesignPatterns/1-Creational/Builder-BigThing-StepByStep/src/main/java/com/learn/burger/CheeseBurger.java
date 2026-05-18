package com.learn.burger;


public class CheeseBurger
   extends BurgerImpl
{
   public BurgerType getType()
   {
      return BurgerType.CheeseBugerType;
   }
   
   public float getPrice()
   {
      return 2.2F;
   }
}
