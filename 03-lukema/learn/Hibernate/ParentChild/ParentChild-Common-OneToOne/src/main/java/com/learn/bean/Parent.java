package com.learn.bean;


public class Parent
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            name;

   private Child             child;

   public void setName(String value)
   {
      this.name = value;
   }

   public String getName()
   {
      return name;
   }

   public void setChild(Child child)
   {
      this.child = child;
   }

   public Child getChild()
   {
      return child;
   }

}
