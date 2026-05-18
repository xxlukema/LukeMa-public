package com.learn;


import java.util.Date;


public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;

   // To learn how to map date.
   private Date              date;

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

}
