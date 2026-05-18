package com.learn;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Payment")
public class Payment
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   @Id
   @Column(name = "PAYMENT_ID")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long              id               = null;

   @Column(name = "AMOUNT")
   private Float             amount           = null;

   @Column(name = "PAYMENT_TYPE", length=20)
   private String            type             = null;

   public void setId(Long value)
   {
      this.id = value;
   }

   public Long getId()
   {
      return id;
   }

   public Float getAmount()
   {
      return amount;
   }

   public void setAmount(Float amount)
   {
      this.amount = amount;
   }

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }
}
