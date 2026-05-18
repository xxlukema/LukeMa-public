package com.learn;


import org.apache.log4j.Logger;

import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.Person;
import com.learn.service.MyService;
import com.learn.util.SpringApplicationContext;


public class TestDriver
{
   private static final Logger    LOG        = Logger.getLogger(TestDriver.class);

   private static final MyService MY_SERVICE = SpringApplicationContext.getBean("myService");

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Test begin.");

      addRecords();
      addRecordsRollBack();
      queryRecords();

      LOG.info("Test complete.");
   }

   public static void addRecords()
   {
      Person person = new Person();
      person.setName("Luke Ma");

      Widget widget = new Widget();
      widget.setName("My Widget");

      try
      {
         MY_SERVICE.saveObjects(person, widget);
      }
      catch (Exception e)
      {
         LOG.info("Got Exception: " + e.getMessage());
      }
   }

   public static void addRecordsRollBack()
      throws Exception
   {
      Person person = new Person();
      person.setName("Luke Ma - Rollback");

      Widget widget = new Widget();
      widget.setName("My Widget - Rollback");

      try
      {
         MY_SERVICE.saveObjectsRollback(person, widget);
      }
      catch (Exception e)
      {
         LOG.info("Got Exception: " + e.getMessage());
      }
   }

   public static void queryRecords()
      throws Exception
   {
      MY_SERVICE.list();
   }

}
