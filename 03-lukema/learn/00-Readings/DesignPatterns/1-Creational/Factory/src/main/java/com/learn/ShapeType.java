package com.learn;


public enum ShapeType
{
   Square("Square Shape"), Circle("Circle"), Triangle("Triangle");

   private String desc = null;

   private ShapeType(String desc)
   {
      this.desc = desc;
   }

   public String getDesc()
   {
      return desc;
   }
}
