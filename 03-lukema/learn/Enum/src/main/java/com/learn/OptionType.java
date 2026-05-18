package com.learn;


public enum OptionType
{
   UP_AND_OUT('U', "Up-and-out"),
   DOWN_AND_OUT('D', "Down-and-out"),
   PLAIN('V', "Plain");

   private char value;
   private String desc;

   OptionType(char value, String desc)
   {
      this.value = value;
      this.desc = desc;
   }

   public String getDesc()
   {
      return desc;
   }

   public char getValue()
   {
      return value;
   }
   
   
}


