package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
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
      queryRecord();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");
      person.setIntEnum(IntEnum.OPEN);
      person.setStringEnum(StringEnum.StringOne);

      HibernateUtils.save(person);
   }

   public static void queryRecord()
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
            LOG.info("Person:     " + person.getName());
            LOG.info("IntEnum:    " + person.getIntEnum());
            LOG.info("StringEnum: " + person.getStringEnum());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
