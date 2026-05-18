package com.learn.bean;


public class Address
{
   private int    addressId;

   private String province;

   private String country;

   private String postcode;

   private String street;

   private String city;

   public int getAddressId()
   {
      return addressId;
   }

   public void setAddressId(int addressId)
   {
      this.addressId = addressId;
   }

   public String getProvince()
   {
      return province;
   }

   public void setProvince(String province)
   {
      this.province = province;
   }

   public String getCountry()
   {
      return country;
   }

   public void setCountry(String country)
   {
      this.country = country;
   }

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode(String postcode)
   {
      this.postcode = postcode;
   }

   public String getStreet()
   {
      return street;
   }

   public void setStreet(String street)
   {
      this.street = street;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   @Override
   public String toString()
   {
      return "Address [addressId=" + addressId + ", city=" + city + ", country=" + country + ", postcode=" + postcode + ", province=" + province + ", street="
            + street + "]";
   }

}
