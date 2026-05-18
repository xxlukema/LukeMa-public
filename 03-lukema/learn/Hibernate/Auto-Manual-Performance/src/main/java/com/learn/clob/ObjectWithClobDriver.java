package com.learn.clob;


import java.io.FileReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.learn.clob.bean.ObjectWithClob;
import com.learn.hibernate.HibernateUtils;


public class ObjectWithClobDriver
{
   private static final Logger LOG     = Logger.getLogger(ObjectWithClobDriver.class);

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
      Clob clob = Hibernate.createClob(reader, 4000);

      ObjectWithClob objectWithClob = new ObjectWithClob();
      objectWithClob.setClob(clob);
      HibernateUtils.saveOrUpdate(objectWithClob);

      reader.close();

      LOG.info("Data Initiated.");
   }

   public static void retrieveData()
      throws SQLException
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("objectWithClob.findAll");

         @SuppressWarnings("unchecked")
         List<ObjectWithClob> list = query.list();

         if (list.size() > 0)
         {
            for (ObjectWithClob objectWithClob : list)
            {
               Clob clob = objectWithClob.getClob();
               String text = clob.getSubString(1, (int) clob.length());
               System.out.println(text);
            }
         }
         else
         {
            LOG.error("No ObjectWithClob found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveData_Like()
      throws SQLException
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("objectWithClob.like");
         query.setString("key", "%artifactId%");

         @SuppressWarnings("unchecked")
         List<ObjectWithClob> list = query.list();

         if (list.size() > 0)
         {
            for (ObjectWithClob objectWithClob : list)
            {
               Clob clob = objectWithClob.getClob();
               String text = clob.getSubString(1, (int) clob.length());
               System.out.println(text);
            }
         }
         else
         {
            LOG.error("No ObjectWithClob found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
