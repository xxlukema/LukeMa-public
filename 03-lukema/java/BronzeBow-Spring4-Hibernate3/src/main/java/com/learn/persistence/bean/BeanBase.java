package com.learn.persistence.bean;


import java.io.Serializable;
import java.util.Date;


public class BeanBase
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private Long              id;

   private Date              dateCreated;

   private Date              dateUpdated;

   private String            notes;

   public void setId(Long id)
   {
      this.id = id;
   }

   public Long getId()
   {
      return id;
   }

   public void setDateCreated(Date dateCreated)
   {
      this.dateCreated = dateCreated;
   }

   public Date getDateCreated()
   {
      return dateCreated;
   }

   public void setDateUpdated(Date dateUpdated)
   {
      this.dateUpdated = dateUpdated;
   }

   public Date getDateUpdated()
   {
      return dateUpdated;
   }

   public void setNotes(String notes)
   {
      this.notes = notes;
   }

   public String getNotes()
   {
      return notes;
   }
}
