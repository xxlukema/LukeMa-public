package com.learn.drink;


public class Coke
   extends DrinkImpl
{
   public DrinkType getType()
   {
      return DrinkType.CokeType;
   }
   
   public float getPrice()
   {
      return 5.2F;
   }
}
