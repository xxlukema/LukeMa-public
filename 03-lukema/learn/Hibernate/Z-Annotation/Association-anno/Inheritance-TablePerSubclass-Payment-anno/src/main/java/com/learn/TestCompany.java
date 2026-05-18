package com.learn;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learn.hibernate.HibernateResourceManager;
import com.learn.hibernate.HibernateUtil;


/**
 * For transactions work with MySQL, the tables must be of InnoDB type.
 * That is, use InnoDB dialect for hibernate.
 */
public class TestCompany
{
   private static final Logger LOG = Logger.getLogger(TestCompany.class);

   public static void main(String[] args)
   {
      addRecord();
   }

   public static void addRecord()
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtil.getSession();

         transaction = session.beginTransaction();

         CreditPayment creditPayment = new CreditPayment();
         creditPayment.setAmount(12.0F);
         creditPayment.setCreditCardType("Master Card");

         session.saveOrUpdate(creditPayment);

         CheckPayment checkPayment1 = new CheckPayment();
         checkPayment1.setAmount(23F);
         checkPayment1.setBankName("Capital One");

         session.saveOrUpdate(checkPayment1);
         
         CheckPayment checkPayment2 = new CheckPayment();
         checkPayment2.setAmount(101.2F);
         checkPayment2.setBankName("JPM Chase");

         session.saveOrUpdate(checkPayment2);

         transaction.commit();

         LOG.info("Company and employees saved.");
      }
      catch (Throwable t)
      {
         // rollback transaction
         if (transaction != null)
         {
            transaction.rollback();
         }

         LOG.error("Exception with transaction. Rollback", t);

         t.printStackTrace();

         System.exit(1);
      }
      finally
      {
         HibernateResourceManager.close(session);
      }
   }
}
