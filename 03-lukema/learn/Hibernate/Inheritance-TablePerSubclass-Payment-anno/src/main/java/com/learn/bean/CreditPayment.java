package com.learn.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "Credit_Payment")
@PrimaryKeyJoinColumn(name = "Credit_Payment_Id", referencedColumnName="Payment_Id")
public class CreditPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

   @Column(name = "CC_Type")
   private String            creditCardType   = null;

   public CreditPayment()
   {
      setType("Credit");
   }

   public String getCreditCardType()
   {
      return creditCardType;
   }

   public void setCreditCardType(String creditCardType)
   {
      this.creditCardType = creditCardType;
   }
}
