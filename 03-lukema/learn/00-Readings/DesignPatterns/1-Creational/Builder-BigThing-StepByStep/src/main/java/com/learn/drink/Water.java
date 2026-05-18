package com.learn.drink;


public class Water
   extends DrinkImpl
{
   public DrinkType getType()
   {
      return DrinkType.WaterType;
   }
   
   public float getPrice()
   {
      return 0.0F;
   }
}
