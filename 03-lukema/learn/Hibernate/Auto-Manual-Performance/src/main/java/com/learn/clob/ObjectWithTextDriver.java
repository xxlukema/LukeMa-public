package com.learn.clob;


import java.io.FileReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.learn.clob.bean.ObjectWithText;
import com.learn.hibernate.HibernateUtils;


public class ObjectWithTextDriver
{
   private static final Logger LOG     = Logger.getLogger(ObjectWithTextDriver.class);

   private static final String IN_FILE = "pom.xml";

   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      retrieveData();

      retrieveData_Like();
   }

   public static void addRecord()
      throws Exception
   {
      Reader reader = new FileReader(IN_FILE);
      CharBuffer charBuffer = CharBuffer.allocate(4000);
      reader.read(charBuffer);
      String text = new String(charBuffer.array());
      reader.close();

      ObjectWithText objectWithText = new ObjectWithText();
      objectWithText.setText(text);
      HibernateUtils.saveOrUpdate(objectWithText);

      LOG.info("Data Initiated.");
   }

   public static void retrieveData()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("objectWithText.findAll");

         @SuppressWarnings("unchecked")
         List<ObjectWithText> list = query.list();

         if (list.size() > 0)
         {
            for (ObjectWithText objectWithText : list)
            {
               String text = objectWithText.getText();
               System.out.println(text);
            }
         }
         else
         {
            LOG.error("No ObjectWithText found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveData_Like()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("objectWithText.like");
         query.setString("key", "%artifactId%");

         @SuppressWarnings("unchecked")
         List<ObjectWithText> list = query.list();

         if (list.size() > 0)
         {
            for (ObjectWithText objectWithText : list)
            {
               String text = objectWithText.getText();
               System.out.println(text);
            }
         }
         else
         {
            LOG.error("No ObjectWithText found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
