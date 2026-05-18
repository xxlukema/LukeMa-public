package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;


public class PersonDriver
{
   private static final Logger LOG          = Logger.getLogger(PersonDriver.class);

   private static final String SCALAR_QUERY = "select id, ssn, height, city, state, name, height from People";

   //private static final String SCALAR_QUERY = "select *, height from People";

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      doScalarQuery();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      Person persion = new Person();
      persion.setName("Luke Ma");
      persion.setSsn("123-12-1234");
      persion.setHeight(5.6f);
      persion.setCity("Missouri City");
      persion.setState("TX");

      HibernateUtils.save(persion);
   }

   public static void doScalarQuery()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         SQLQuery query = session.createSQLQuery(SCALAR_QUERY);

         query.addEntity("person", Person.class).addScalar("height", Hibernate.FLOAT);

         @SuppressWarnings("unchecked")
         List<Object> results = query.list();

         for (Object result : results)
         {
            Object[] scalar = (Object[]) result;

            Person person = (Person) scalar[0];
            float height = (Float) scalar[1];

            LOG.info("Person: " + person);
            LOG.info("Height: " + height);
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
