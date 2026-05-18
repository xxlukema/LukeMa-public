package com.learn.bean;


import java.util.ArrayList;
import java.util.List;


public class Parent
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            name;

   private List<Child>       children;

   public void setName(String value)
   {
      this.name = value;
   }

   public String getName()
   {
      return name;
   }

   public void setChildren(List<Child> children)
   {
      this.children = children;
   }

   public List<Child> getChildren()
   {
      if (children == null)
      {
         children = new ArrayList<Child>();
      }

      return children;
   }

}
