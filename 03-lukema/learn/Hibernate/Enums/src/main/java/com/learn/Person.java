package com.learn;


public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;

   private IntEnum           intEnum;

   private StringEnum        stringEnum;

   public void setStringEnum(StringEnum stringEnum)
   {
      this.stringEnum = stringEnum;
   }

   public StringEnum getStringEnum()
   {
      return stringEnum;
   }

   public void setIntEnum(IntEnum intEnum)
   {
      this.intEnum = intEnum;
   }

   public IntEnum getIntEnum()
   {
      return intEnum;
   }

}
