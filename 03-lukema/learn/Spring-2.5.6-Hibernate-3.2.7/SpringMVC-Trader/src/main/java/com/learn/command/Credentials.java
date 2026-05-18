package com.learn.command;


import java.io.Serializable;


public class Credentials
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            username;

   private String            password;

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String value)
   {
      this.password = value;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String value)
   {
      this.username = value;
   }
}
