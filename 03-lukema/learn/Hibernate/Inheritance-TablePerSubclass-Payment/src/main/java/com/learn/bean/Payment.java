package com.learn.bean;


import java.io.Serializable;


public class Payment
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   private Long              id               = null;

   private Float             amount           = null;

   private String            type             = null;

   public void setId(Long value)
   {
      this.id = value;
   }

   public Long getId()
   {
      return id;
   }

   public Float getAmount()
   {
      return amount;
   }

   public void setAmount(Float amount)
   {
      this.amount = amount;
   }

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }
}
