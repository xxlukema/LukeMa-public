package com.fuelquest.users.model;


public class User implements java.io.Serializable
{
   private static final long serialVersionUID = 0l;
   
   private Long   id;
   private String userName;
   private Long   contactId;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getUserName()
   {
      return this.userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public Long getContactId()
   {
      return this.contactId;
   }

   public void setContactId(Long contactId)
   {
      this.contactId = contactId;
   }
}
