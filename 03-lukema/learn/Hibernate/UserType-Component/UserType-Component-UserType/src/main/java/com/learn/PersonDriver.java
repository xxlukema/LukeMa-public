package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
   extends PersonDriverBase
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();

      queryRecordLocal();

      LOG.debug("Test complete.");
   }

   public static void queryRecordLocal()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Criteria criteria = session.createCriteria(Person.class);

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();

         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            LOG.info("Name: " + person.getPersonalData().getName());
            LOG.info("Date: " + person.getPersonalData().getDate());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
