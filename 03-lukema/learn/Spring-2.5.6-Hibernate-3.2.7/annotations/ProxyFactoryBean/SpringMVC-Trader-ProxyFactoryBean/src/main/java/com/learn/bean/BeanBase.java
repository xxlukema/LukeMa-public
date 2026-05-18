package com.learn.bean;


import java.io.Serializable;


public class BeanBase
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private Long              id;

   public void setId(Long id)
   {
      this.id = id;
   }

   public Long getId()
   {
      return id;
   }
}
