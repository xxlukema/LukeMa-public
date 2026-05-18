package com.learn;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@PrimaryKeyJoinColumn(name = "CREDIT_PAYMENT_ID")
@Table(name = "Credit_Payment")
public class CreditPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

   @Column(name = "CC_TYPE", length = 20)
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
