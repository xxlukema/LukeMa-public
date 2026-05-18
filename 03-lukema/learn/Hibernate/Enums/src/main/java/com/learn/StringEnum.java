package com.learn;

public enum StringEnum
{
   StringOne("String One", "one"), StringTwo("String Two", "two"), StringThree("String Three", "three");
   
   private String value;
   private String desc;
   
   StringEnum(String value, String desc)
   {
      this.value = value;
      this.desc = desc;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue(String value)
   {
      this.value = value;
   }

   public String getDesc()
   {
      return desc;
   }

   public void setDesc(String desc)
   {
      this.desc = desc;
   }
}
