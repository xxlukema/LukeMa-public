package com.learn.drink;


public class OrangeJuice
   extends DrinkImpl
{
   public DrinkType getType()
   {
      return DrinkType.OrangeJuiceType;
   }
   
   public float getPrice()
   {
      return 0.3F;
   }
}
