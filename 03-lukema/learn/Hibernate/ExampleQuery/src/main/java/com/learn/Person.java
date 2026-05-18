package com.learn;




public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;
   
   private Address address;

   public void setAddress(Address address)
   {
      this.address = address;
   }

   public Address getAddress()
   {
      return address;
   }
}
