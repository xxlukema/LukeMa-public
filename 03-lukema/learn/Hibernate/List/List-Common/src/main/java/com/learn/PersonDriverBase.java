package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriverBase
{
   private static final Logger LOG = Logger.getLogger(PersonDriverBase.class);

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");

      for (int i = 0; i < 10; i++)
      {
         person.getStringList().add("Line One");
         person.getStringList().add("Line Two");
      }

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

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();

         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            LOG.info("Person: " + person.getName());

            List<String> stringList = person.getStringList();
            LOG.info("stringList.size() = " + stringList.size());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
