package com.learn.burger;


public class BigMac
   extends BurgerImpl
{
   public BurgerType getType()
   {
      return BurgerType.BigMacType;
   }
   
   public float getPrice()
   {
      return 1.3F;
   }
}
