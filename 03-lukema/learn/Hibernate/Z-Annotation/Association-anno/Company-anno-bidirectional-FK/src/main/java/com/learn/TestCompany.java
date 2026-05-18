package com.learn;


import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.learn.hibernate.HibernateUtil;
import com.learn.hibernate.HibernateResourceManager;


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

      retrieveData();

      //deleteLastCompany();
   }

   public static void addRecord()
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtil.getSession();

         transaction =  session.beginTransaction();

         Company company = new Company();
         company.setName("Luke Company");

         Employee e1 = new Employee();
         e1.setName("Luke Ma 1");
         e1.setAge(16);
         e1.setCompany(company);

         Employee e2 = new Employee();
         e2.setName("Hong Lin 2");
         e2.setAge(20);
         e2.setCompany(company);

         //session.saveOrUpdate(e1);
         //session.saveOrUpdate(e2);

         company.addEmployee(e1);
         company.addEmployee(e2);

         session.saveOrUpdate(company);

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

   public static void retrieveData()
   {
      Session session = null;

      try
      {
         session = HibernateUtil.getSession();

         Query query = session.getNamedQuery("company.findAll");

         @SuppressWarnings("unchecked")
         List<Company> companies = query.list();

         if (companies.size() > 0)
         {
            for (Company company : companies)
            {
               //Company company = (Company) (companies.get(0));

               String name = company.getName();
               LOG.info("Company name: " + name);

               Set<Employee> employeeSet = company.getEmployeeSet();

               int size = employeeSet.size();

               LOG.info("Number of employees: " + size);

               for (Employee e : employeeSet)
               {
                  String eName = e.getName();
                  int eAge = e.getAge();

                  LOG.info("Employee name: " + eName + ". Age: " + eAge);
               }
            }
         }
         else
         {
            LOG.error("No company found.");
         }
      }
      catch (Throwable t)
      {
         LOG.error("Exception with transaction.", t);

         t.printStackTrace();

         System.exit(1);
      }
      finally
      {
         HibernateResourceManager.close(session);
      }
   }

   public static void deleteLastCompany()
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtil.getSession();

         Query query = session.getNamedQuery("company.findAll");

         @SuppressWarnings("unchecked")
         List<Company> companies = query.list();

         if (companies.size() > 0)
         {
            Company company = (Company) (companies.get(companies.size()-1));

            transaction =  session.beginTransaction();

            session.delete(company);

            transaction.commit();
            LOG.info("Last company deleted.");
         }
         else
         {
            LOG.error("No company found.");
         }
      }
      catch (Throwable t)
      {
         LOG.error("Exception with transaction.", t);

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

