package com.learn.bean;


public class CreditPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

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
