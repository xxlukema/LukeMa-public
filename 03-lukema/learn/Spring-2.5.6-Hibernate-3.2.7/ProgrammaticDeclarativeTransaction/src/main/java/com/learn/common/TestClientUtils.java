package com.learn.common;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.learn.bean.Person;
import com.learn.service.PersonService;
import com.learn.util.SpringApplicationContext;


public class TestClientUtils
{
   private static final Logger LOG    = Logger.getLogger(TestClientUtils.class);

   public static String        LUKE   = "Luke Ma";

   public static float         WEIGHT = 74.4F;

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName(LUKE);
      person.setWeight(WEIGHT);

      PersonService personService = SpringApplicationContext.getBean("personService");
      personService.saveOrUpdate(person);
   }

   public static void queryRecords()
      throws Exception
   {
      PersonService personService = SpringApplicationContext.getBean("personService");

      List<Person> people = personService.list(Person.class);
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Name   = " + person.getName());
         LOG.info("Weight = " + person.getWeight());
      }
   }

}
