package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.learn.bean.Person;
import com.learn.jdbc.PersonJDBC;
import com.learn.jta.PersonJTA;


public class PersonMain
{
   private static final Logger LOG = Logger.getLogger(PersonMain.class);

   public static void main(String args[])
      throws Exception
   {
      PersonDriver.addPerson();
      
      queryPerson();
   }

   public static void queryPerson()
      throws Exception
   {
      Resource resource = new ClassPathResource("jta.xml");
      BeanFactory beanFactory = new XmlBeanFactory(resource);

      // JTA
      PersonJTA personJTA = (PersonJTA) (beanFactory.getBean("personJTA"));

      List<Person> people = personJTA.list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Name = " + person.getName());
      }
      
      // JDBC
      PersonJDBC personJDBC = (PersonJDBC) (beanFactory.getBean("personJDBC"));

      people = personJDBC.list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Name = " + person.getName());
      }
   }
}
