package com.learn.bean;


public class Customer
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            username;

   private String            password;

   private Portfolio         portfolio;

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public Portfolio getPortfolio()
   {
      return portfolio;
   }

   public void setPortfolio(Portfolio portfolio)
   {
      this.portfolio = portfolio;
   }

}
