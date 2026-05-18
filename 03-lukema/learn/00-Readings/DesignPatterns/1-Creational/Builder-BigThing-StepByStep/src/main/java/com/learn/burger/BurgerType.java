package com.learn.burger;


public enum BurgerType
{
   CheeseBugerType("Square Shape"), BigMacType("Circle"), BigJackType("Triangle");

   private String desc = null;

   private BurgerType(String desc)
   {
      this.desc = desc;
   }

   public String getDesc()
   {
      return desc;
   }
}
