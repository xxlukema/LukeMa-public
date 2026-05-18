package com.learn;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@PrimaryKeyJoinColumn(name = "CHECK_PAYMENT_ID")
@Table(name = "Check_Payment")
public class CheckPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

   @Column(name = "BANK_NAME", length = 20)
   private String bankName = null;

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
