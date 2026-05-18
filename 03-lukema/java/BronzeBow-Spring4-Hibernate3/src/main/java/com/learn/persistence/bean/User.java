package com.learn.persistence.bean;


public class User
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            username;

   private String            password;

   private String            email;

   private String            stockList;

   private String            remoteAddress;

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public void setUsername(String username)
   {
      this.username = username;
   }

   public String getUsername()
   {
      return username;
   }

   public void setStockList(String stockList)
   {
      this.stockList = stockList;
   }

   public String getStockList()
   {
      return stockList;
   }

   public void setRemoteAddress(String remoteAddress)
   {
      this.remoteAddress = remoteAddress;
   }

   public String getRemoteAddress()
   {
      return remoteAddress;
   }

}
