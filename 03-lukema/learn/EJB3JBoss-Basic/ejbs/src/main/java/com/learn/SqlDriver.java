package com.learn;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.learn.util.ClassPathUtils;
import com.learn.util.HibernateUtils;


public class SqlDriver
{
   private static final Logger LOG = Logger.getLogger(SqlDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      queryRecordInvoice();
      
     // queryRecordExpense();

      LOG.debug("Test complete.");
   }

   public static void queryRecordInvoice()
      throws Exception
   {
      Session session = null;
      InputStreamReader inputStreamReader = null;
      BufferedReader bufferedReader = null;

      try
      {
         session = HibernateUtils.openSession();

         inputStreamReader = new InputStreamReader(ClassPathUtils.newInputStream("InvoiceFields.txt"));
         bufferedReader = new BufferedReader(inputStreamReader);

         String queryString = "select count(*) from invoice where type='exp'";

         SQLQuery sqlQuery = session.createSQLQuery(queryString);
         @SuppressWarnings("unchecked")
         List<Object> tableSize = sqlQuery.list();
         LOG.info("Invoice Table size: " + tableSize.get(0));

         System.out.println();

         for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine())
         {
            if ((line = line.trim()).trim().length() > 0)
            {
               queryString = "select distinct " + line + " from invoice where type='exp'";

               sqlQuery = session.createSQLQuery(queryString);

               @SuppressWarnings("unchecked")
               List<Object> list = sqlQuery.list();

               if (list.size() < 20 && list.size() > 2)
               {
                  while (line.length() < 20)
                  {
                     line += ' ';
                  }

                  System.out.print("\n" + line + " \t" + list.size() + " \t");
                  for (Object object : list)
                  {
                     System.out.print(object + " \t");
                  }
               }
            }
         }

         System.out.println();
         System.out.println();
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void queryRecordExpense()
      throws Exception
   {
      Session session = null;
      InputStreamReader inputStreamReader = null;
      BufferedReader bufferedReader = null;

      try
      {
         session = HibernateUtils.openSession();

         inputStreamReader = new InputStreamReader(ClassPathUtils.newInputStream("ExpenseFields.txt"));
         bufferedReader = new BufferedReader(inputStreamReader);

         String queryString = "select count(*) from expense where type='minv'";

         SQLQuery sqlQuery = session.createSQLQuery(queryString);
         @SuppressWarnings("unchecked")
         List<Object> tableSize = sqlQuery.list();
         LOG.info("Expense Table size: " + tableSize.get(0));

         System.out.println();

         for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine())
         {
            if ((line = line.trim()).trim().length() > 0)
            {
               queryString = "select distinct " + line + " from expense where type='minv'";

               sqlQuery = session.createSQLQuery(queryString);

               @SuppressWarnings("unchecked")
               List<Object> list = sqlQuery.list();

               if (list.size() < 2)
               {
                  while (line.length() < 20)
                  {
                     line += ' ';
                  }

                  System.out.print("\n" + line + " \t" + list.size() + " \t");
                  for (Object object : list)
                  {
                     System.out.print(object + " \t");
                  }
               }
            }
         }

         System.out.println();
         System.out.println();
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
