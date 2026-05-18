package com.learn;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.Person;
import com.learn.hibernate.HibernateUtils;
import com.learn.util.HibernateMultipleDataSourceUtils;


public class TestDriver
{
   private static final Logger LOG = Logger.getLogger(TestDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecords();
      queryRecords();

      LOG.debug("Test complete.");
   }

   public static void addRecords()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma");

      HibernateMultipleDataSourceUtils.save(person);

      Widget widget = new Widget();
      widget.setName("My Widget");

      HibernateMultipleDataSourceUtils.save(widget);
   }

   public static void queryRecords()
      throws Exception
   {
      Session session = null;
      Criteria criteria = null;

      try
      {
         session = HibernateMultipleDataSourceUtils.openSession(Person.class);
         criteria = session.createCriteria(Person.class);

         @SuppressWarnings("unchecked")
         List<Person> list = criteria.list();

         Assert.assertTrue(list.size() > 0);

         for (Person person : list)
         {
            LOG.info("person.getName() = " + person.getName());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }

      try
      {
         session = HibernateMultipleDataSourceUtils.openSession(Widget.class);
         criteria = session.createCriteria(Widget.class);

         @SuppressWarnings("unchecked")
         List<Widget> list = criteria.list();

         Assert.assertTrue(list.size() > 0);

         for (Widget widget : list)
         {
            LOG.info("widget.getName() = " + widget.getName());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
