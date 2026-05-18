package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      queryRecord_NewSession();
      queryRecord_CurrentSession_Right();
      queryRecord_CurrentSession_Wrong();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");

      HibernateUtils.save(person);
   }

   /**
    * New Session queries Do not need to be within an active transaction.
    */
   public static void queryRecord_NewSession()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         List<Person> people = HibernateUtils.list(session, Person.class);
         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            LOG.info("Person: " + person.getName());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   /**
    * CurrentSession queries should be within an active transaction.
    */
   public static void queryRecord_CurrentSession_Right()
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.getCurrentSession();
         transaction = session.beginTransaction();

         Criteria criteria = session.createCriteria(Person.class);

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();
         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            LOG.info("Person: " + person.getName());
         }

         transaction.commit();
      }
      catch (Exception e)
      {
         if (transaction != null)
         {
            transaction.rollback();
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   /*
    * CurrentSession queries should be within an active transaction.
    */
   public static void queryRecord_CurrentSession_Wrong()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.getCurrentSession();
         session.createCriteria(Person.class);
         Assert.fail("Query with CurrentSession should be within a transction.");
      }
      catch (Exception e)
      {
         LOG.info("You should see this message: " + e.getMessage());
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
