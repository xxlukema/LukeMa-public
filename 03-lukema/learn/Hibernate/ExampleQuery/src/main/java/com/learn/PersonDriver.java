package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      queryRecord_WrongWay();
      queryRecord_RightWay();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Hong Lin");
      person.setAge(20);
      person.setGender(Gender.FEMALE);
      Address address = new Address();
      address.setStreet("1317 Kingsley CT");
      address.setCity("Allen 75013");
      person.setAddress(address);
      HibernateUtils.save(person);

      person = new Person();
      person.setName("Luke Ma");
      person.setAge(20);
      person.setGender(Gender.MALE);
      address = new Address();
      address.setStreet("5623 Horseshoe Falls");
      address.setCity("Missouri City 77459");
      person.setAddress(address);
      HibernateUtils.save(person);
   }

   /**
    * The right way to query association using criteria.
    */
   public static void queryRecord_RightWay()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Person person = new Person();
         person.setAge(20);
         person.setGender(Gender.MALE);
         person.setName("Luke Ma");

         Example personExample = Example.create(person);

         Address address = new Address();
         address.setCity("missouri %");

         Example addressExample = Example.create(address);
         addressExample.enableLike();
         addressExample.ignoreCase();

         Criteria criteria = session.createCriteria(Person.class);
         criteria.add(personExample);
         Criteria subCriteria = criteria.createCriteria("address");
         subCriteria.add(addressExample);

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();
         LOG.info("people.size() = " + people.size());
         Assert.assertTrue(people.size() > 0);

         for (Person dbPerson : people)
         {
            LOG.info("Person: " + dbPerson.getName());

            Address adAddress = dbPerson.getAddress();
            LOG.info("Address: " + adAddress.getStreet() + ", " + adAddress.getCity());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }

   }

   /**
    * The wrong way to query association using criteria.
    */
   public static void queryRecord_WrongWay()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Person person = new Person();
         person.setAge(20);
         person.setGender(Gender.MALE);
         person.setName("Luke Ma");

         Address address = new Address();
         address.setCity("Cheng Du");

         // The association will be ignored. 
         person.setAddress(address);

         Example personExample = Example.create(person);

         Criteria criteria = session.createCriteria(Person.class);
         criteria.add(personExample);

         @SuppressWarnings("unchecked")
         List<Person> people = criteria.list();
         LOG.info("people.size() = " + people.size());
         Assert.assertTrue(people.size() > 0);

         for (Person dbPerson : people)
         {
            LOG.info("Person: " + dbPerson.getName());

            Address adAddress = dbPerson.getAddress();
            LOG.info("Address: " + adAddress.getStreet() + ", " + adAddress.getCity());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
