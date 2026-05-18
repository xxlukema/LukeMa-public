package com.learn.bean;


public class Child
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            name;

   private Parent            parent;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setParent(Parent parent)
   {
      this.parent = parent;
   }

   public Parent getParent()
   {
      return parent;
   }

}
