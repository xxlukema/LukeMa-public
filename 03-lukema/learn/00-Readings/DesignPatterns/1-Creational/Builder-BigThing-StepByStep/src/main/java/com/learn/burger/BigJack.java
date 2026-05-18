package com.learn.burger;


public class BigJack
   extends BurgerImpl
{
   public BurgerType getType()
   {
      return BurgerType.BigJackType;
   }
   
   public float getPrice()
   {
      return 1.2F;
   }
}
