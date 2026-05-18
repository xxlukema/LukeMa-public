package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      addRecord();

      queryRecord();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");

      HibernateUtils.save(person);
   }

   public static void queryRecord()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Criteria criteria = session.createCriteria(Person.class);
         criteria.setMaxResults(1);

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();

         Assert.assertTrue(people.size() == 1);

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

}
