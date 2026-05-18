package com.learn;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
{
   private static final Logger LOG      = Logger.getLogger(PersonDriver.class);

   private static final long   MILI_SEC = System.currentTimeMillis();

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      queryRecord_Found();
      queryRecord_NotFound();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");
      Calendar calendar = new GregorianCalendar();
      calendar.setTimeInMillis(MILI_SEC);
      person.setDate(calendar.getTime());

      LOG.info("Date: " + Calendar.getInstance().getTime());

      HibernateUtils.save(person);
   }

   /**
    * ">=" is sensitive to (MILI_SEC + 1000 * 3600 * 24) (24 hours)
    */
   public static void queryRecord_Found()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Query query = session.getNamedQuery("person.findByDate");

         Calendar calendar = new GregorianCalendar();
         calendar.setTimeInMillis(MILI_SEC);
         query.setDate("date", calendar.getTime());
         LOG.info("Date: " + calendar.getTime());

         @SuppressWarnings("unchecked")
         List<Person> people = query.list();

         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            LOG.info("Person: " + person.getName());
            LOG.info("Date:   " + person.getDate());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   /**
    * ">=" is sensitive to (MILI_SEC + 1000 * 3600 * 24) (24 hours)
    */
   public static void queryRecord_NotFound()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Query query = session.getNamedQuery("person.findByDate");

         Calendar calendar = new GregorianCalendar();
         calendar.setTimeInMillis(MILI_SEC + 1000 * 3600 * 24);
         query.setDate("date", calendar.getTime());
         LOG.info("Date: " + calendar.getTime());

         @SuppressWarnings("unchecked")
         List<Person> people = query.list();

         Assert.assertEquals(0, people.size());
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
