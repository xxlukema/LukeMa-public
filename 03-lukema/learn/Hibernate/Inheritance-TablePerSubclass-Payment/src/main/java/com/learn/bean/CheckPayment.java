package com.learn.bean;


public class CheckPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

   private String            bankName         = null;

   public String getBankName()
   {
      return bankName;
   }

   public CheckPayment()
   {
      setType("Check");
   }

   public void setBankName(String bankName)
   {
      this.bankName = bankName;
   }
}
