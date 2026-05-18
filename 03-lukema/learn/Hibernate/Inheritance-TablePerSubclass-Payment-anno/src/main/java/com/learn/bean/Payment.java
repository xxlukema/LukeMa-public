package com.learn.bean;


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
   extends EntityBase
{
   private static final long serialVersionUID = 0L;

   @Id
   //@TableGenerator(name = "idGenerator", table = "Sys_Next_Id", pkColumnName = "Pk_Column_Name", pkColumnValue = "Pk_Column_Value", valueColumnName = "Value_Column_Name")
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "Payment_Id")
   private Long              id               = null;

   @Column(name = "Amount")
   private Float             amount           = null;

   @Column(name = "Payment_Type")
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
