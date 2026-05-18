package com.fuelquest.users.model;


public class UserSecurityComputerIdPK 
implements java.io.Serializable
{
   private static final long serialVersionUID = 0l;
   
   private User    user;
   private String  computerId;

   public User getUser()
   {
      return this.user;
   }

   public void setUser(User value)
   {
      this.user = value;
   }

   public String getComputerId()
   {
      return this.computerId;
   }

   public void setComputerId(String value)
   {
      this.computerId = value;
   }

   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }

      if (this == o)
      {
         return true;
      }

      if (! (o instanceof UserSecurityComputerIdPK))
      {
         return false;
      }

      final UserSecurityComputerIdPK pk = (UserSecurityComputerIdPK) o;

      if (! user.equals(pk.getUser()))
      {
         return false;
      }

      if (! computerId.equals(pk.getComputerId()))
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      return user.hashCode() + computerId.hashCode();
   }
}

