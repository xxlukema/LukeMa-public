package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.Person;
import com.learn.mysql.dao.WidgetDAO;
import com.learn.oracle.dao.PersonDAO;
import com.learn.service.MyService;
import com.learn.util.SpringBeanFactory;


public class TestDriver
{
   private static final Logger    LOG        = Logger.getLogger(TestDriver.class);

   private static final MyService MY_SERVICE = SpringBeanFactory.getBean("myService");

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecords();
      testTransaction();
      queryRecords();

      LOG.debug("Test complete.");
   }

   public static void addRecords()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");

      Widget widget = new Widget();
      widget.setName("My Widget");

      MY_SERVICE.saveObjects(person, widget);
   }

   public static void queryRecords()
      throws Exception
   {
      // Oracle Person
      PersonDAO personDAO = SpringBeanFactory.getBean("personDAO");

      List<Person> people = personDAO.list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Person name = " + person.getName());
      }

      // MySQL Widget
      WidgetDAO widgetDAO = SpringBeanFactory.getBean("widgetDAO");

      List<Widget> widgets = widgetDAO.list();
      Assert.assertTrue(widgets.size() > 0);
      LOG.info("widgets.size() = " + widgets.size());

      for (Widget widget : widgets)
      {
         LOG.info("Widget name = " + widget.getName());
      }
   }

   public static void testTransaction()
      throws Exception
   {
      try
      {
         MY_SERVICE.updatePersonCommit();
      }
      catch (Exception e)
      {
         LOG.info("Caught exception: " + e.getMessage());
      }
      
      try
      {
         MY_SERVICE.updatePersonRollback();
      }
      catch (Exception e)
      {
         LOG.info("Caught exception: " + e.getMessage());
      }
   }

}
