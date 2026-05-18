package com.learn.entity;


public enum StructureType
{
   EQUITY(0), INTEREST(1);

   private int value;

   StructureType(int value)
   {
      this.value = value;
   }

   public int getValue()
   {
      return value;
   }
   
   
}
