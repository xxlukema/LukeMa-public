package com.learn;


import org.apache.log4j.Logger;

import com.learn.bean.CheckPayment;
import com.learn.bean.CreditPayment;
import com.learn.hibernate.HibernateUtils;


public class TestCompany
{
   private static final Logger LOG = Logger.getLogger(TestCompany.class);

   public static void main(String[] args)
      throws Exception
   {
      addRecord();
   }

   public static void addRecord() throws Exception
   {
      CreditPayment creditPayment = new CreditPayment();
      creditPayment.setAmount(12.0F);
      creditPayment.setCreditCardType("Master Card");

      HibernateUtils.saveOrUpdate(creditPayment);

      CheckPayment checkPayment1 = new CheckPayment();
      checkPayment1.setAmount(23F);
      checkPayment1.setBankName("Capital One");

      HibernateUtils.saveOrUpdate(checkPayment1);

      CheckPayment checkPayment2 = new CheckPayment();
      checkPayment2.setAmount(101.2F);
      checkPayment2.setBankName("JPM Chase");

      HibernateUtils.saveOrUpdate(checkPayment2);

      LOG.info("Company and employees saved.");
   }
}
