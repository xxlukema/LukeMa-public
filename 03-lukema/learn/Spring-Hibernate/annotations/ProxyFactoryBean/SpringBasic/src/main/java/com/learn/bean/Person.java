package com.learn.bean;

import com.learn.bean.BeanBase;


public class Person
   extends BeanBase
{
   private static final long serialVersionUID = 0L;

   private String            name;

   private float             weight;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public float getWeight()
   {
      return weight;
   }

   public void setWeight(float weight)
   {
      this.weight = weight;
   }

}
