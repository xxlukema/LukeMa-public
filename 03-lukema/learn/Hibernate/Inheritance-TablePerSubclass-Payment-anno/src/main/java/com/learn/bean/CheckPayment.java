package com.learn.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "Check_Payment")
@PrimaryKeyJoinColumn(name = "Check_Payment_Id", referencedColumnName="Payment_Id")
public class CheckPayment
   extends Payment
{
   private static final long serialVersionUID = 0L;

   @Column(name = "Bank_Name")
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
