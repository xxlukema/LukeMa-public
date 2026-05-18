package com.learn.bean;


import java.io.Serializable;


public class Address
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            city;

   private String            state;

   public void setCity(String value)
   {
      this.city = value;
   }

   public String getCity()
   {
      return city;
   }

   public void setState(String value)
   {
      this.state = value;
   }

   public String getState()
   {
      return state;
   }
}
