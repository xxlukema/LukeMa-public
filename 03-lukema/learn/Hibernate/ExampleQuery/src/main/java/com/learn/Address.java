package com.learn;


import com.learn.bean.BeanBase;


public class Address
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String street;

   private String city;

   public void setStreet(String street)
   {
      this.street = street;
   }

   public String getStreet()
   {
      return street;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   public String getCity()
   {
      return city;
   }
}
