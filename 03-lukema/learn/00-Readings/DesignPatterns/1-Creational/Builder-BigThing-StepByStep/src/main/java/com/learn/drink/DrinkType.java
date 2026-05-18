package com.learn.drink;


public enum DrinkType
{
   WaterType("Whater Type"), CokeType("Coco cola"), OrangeJuiceType("Orange Juice");

   private String desc = null;

   private DrinkType(String desc)
   {
      this.desc = desc;
   }

   public String getDesc()
   {
      return desc;
   }
}
