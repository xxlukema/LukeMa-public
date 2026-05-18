package com.learn.bean;


import java.util.HashSet;
import java.util.Set;


public class Child
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            name;

   private Set<Parent>       parents;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setParents(Set<Parent> parents)
   {
      this.parents = parents;
   }

   public Set<Parent> getParents()
   {
      if(parents == null)
      {
         parents = new HashSet<Parent>();
      }
      return parents;
   }

}
