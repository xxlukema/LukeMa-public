package com.learn.common.domain;


import java.io.Serializable;


public class Dividend
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            date;

   private float             value;

   public String getDate()
   {
      return date;
   }

   public void setDate(String date)
   {
      this.date = date;
   }

   public float getValue()
   {
      return value;
   }

   public void setValue(float value)
   {
      this.value = value;
   }

}
