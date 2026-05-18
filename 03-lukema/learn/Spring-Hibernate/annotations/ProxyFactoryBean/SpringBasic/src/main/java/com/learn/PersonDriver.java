package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.learn.bean.Person;
import com.learn.dao.CommonDAO;
import com.learn.dao.PersonDAO;
import com.learn.util.SpringBeanFactory;


public class PersonDriver
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      addPerson();
      queryPerson();
   }

   public static void addPerson()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");
      person.setWeight(160);

      CommonDAO commonDAO = SpringBeanFactory.getCommonDAO();
      commonDAO.saveOrUpdate(person);
   }

   public static void queryPerson()
      throws Exception
   {
      PersonDAO personDAO = SpringBeanFactory.getBean("personDAO");

      List<Person> people = personDAO.list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Name = " + person.getName());
      }
   }
}
