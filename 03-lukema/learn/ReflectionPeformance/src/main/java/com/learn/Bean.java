package com.learn;


public class Bean
{
   private Integer i      = 0;

   private String  str    = null;

   private Object  object = null;

   public Integer getI()
   {
      p('i');

      return i;
   }

   public void setI(Integer i)
   {
      this.i = i;
   }

   public String getStr()
   {
      p('s');

      return str;
   }

   public void setStr(String str)
   {
      this.str = str;
   }

   public Object getObject()
   {
      p('o');

      return object;
   }

   public void setObject(Object object)
   {
      this.object = object;
   }

   public void p(char ch)
   {
      //System.out.print(ch);
   }
}
