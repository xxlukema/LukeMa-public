package com.learn.bean;


import java.util.HashSet;
import java.util.Set;


public class Parent
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            name;

   private Set<Child>        children;

   public void setName(String value)
   {
      this.name = value;
   }

   public String getName()
   {
      return name;
   }

   public void setChildren(Set<Child> children)
   {
      this.children = children;
   }

   public Set<Child> getChildren()
   {
      if (children == null)
      {
         children = new HashSet<Child>();
      }

      return children;
   }

}
