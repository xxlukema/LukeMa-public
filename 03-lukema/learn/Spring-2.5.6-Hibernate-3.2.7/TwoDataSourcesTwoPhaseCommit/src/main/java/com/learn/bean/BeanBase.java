package com.learn.bean;


import java.io.Serializable;


public class BeanBase
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   private Long              id;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }
}
