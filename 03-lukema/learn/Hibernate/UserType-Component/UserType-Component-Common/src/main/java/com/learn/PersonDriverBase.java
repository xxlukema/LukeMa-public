package com.learn;


import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriverBase
{
   private static final Logger LOG  = Logger.getLogger(PersonDriverBase.class);

   private static final String NAME = "Luke Ma";

   public static void addRecord()
      throws Exception
   {
      PersonalData personalData = new PersonalData();
      personalData.setName(NAME);
      personalData.setDate(new Date());

      Person person = new Person();
      person.setPersonalData(personalData);

      HibernateUtils.save(person);
   }

   public static void queryRecord()
      throws Exception
   {
      Session session = null;
      
      try
      {
         session = HibernateUtils.openSession();
         String hql = "from Person person where person.personalData.name = '" + NAME + "'";
         Query query = session.createQuery(hql);

         @SuppressWarnings("unchecked")
         List<Person> people = query.list();

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
