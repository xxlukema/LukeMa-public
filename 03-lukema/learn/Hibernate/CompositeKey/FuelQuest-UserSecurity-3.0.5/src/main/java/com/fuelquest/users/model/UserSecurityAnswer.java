package com.fuelquest.users.model;


import java.util.Date;


public class UserSecurityAnswer 
implements java.io.Serializable
{
   private static final long serialVersionUID = 0l;
   
   private UserSecurityAnswerPK id; 
   private String answer;
   private User createdBy;
   private Date createDate;
   private User lastUpdatedBy;
   private Date lastUpdateDate;

   public void setId(UserSecurityAnswerPK value)
   {
      this.id = value;
   }

   public UserSecurityAnswerPK getId()
   {
      return this.id;
   }

   public String getAnswer()
   {
      return this.answer;
   }

   public void setAnswer(String value)
   {
      this.answer = value;
   }

   public User getCreatedBy()
   {
      return this.createdBy;
   }

   public void setCreatedBy(User value)
   {
      this.createdBy = value;
   }

   public Date getCreateDate()
   {
      return this.createDate;
   }

   public void setCreateDate(Date value)
   {
      this.createDate = value;
   }

   public User getLastUpdatedBy()
   {
      return this.lastUpdatedBy;
   }

   public void setLastUpdatedBy(User value)
   {
      this.lastUpdatedBy = value;
   }

   public Date getLastUpdateDate()
   {
      return this.lastUpdateDate;
   }

   public void setLastUpdateDate(Date value)
   {
      this.lastUpdateDate = value;
   }
}

