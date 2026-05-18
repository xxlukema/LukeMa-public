package com.learn.bean;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public class EntityBase
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "Date_Created")
   private Date              dateCreated;

   public void setDateCreated(Date dateCreated)
   {
      this.dateCreated = dateCreated;
   }

   public Date getDateCreated()
   {
      return dateCreated;
   }
}
