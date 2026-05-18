package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.learn.bean.Person;
import com.learn.dao.CommonDAO;
import com.learn.dao.PersonDAO;
import com.learn.util.SpringBeanFactory;


public class ClientBase
{
   private static final Logger LOG = Logger.getLogger(ClientBase.class);

   public static void addRecord()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");
      person.setWeight(160);

      CommonDAO commonDAO = SpringBeanFactory.getCommonDAO();
      commonDAO.saveOrUpdate(person);
   }

   public static void queryRecords()
      throws Exception
   {
      PersonDAO personDAO = SpringBeanFactory.getBean("personDAO");

      List<Person> people = personDAO.list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Name   = " + person.getName());
         LOG.info("Weight = " + person.getWeight());
      }
   }

}
