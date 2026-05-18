package com.learn.entity;


public enum LongShort
{
   LONG(1), SHORT(2);

   private int value;

   LongShort(int value)
   {
      this.value = value;
   }

   public int getValue()
   {
      return value;
   }
}
