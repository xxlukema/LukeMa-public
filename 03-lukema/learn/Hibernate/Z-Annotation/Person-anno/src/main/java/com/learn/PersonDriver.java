package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.learn.hibernate.HibernateUtil;
import com.learn.model.Person;


public class PersonDriver
{
   private static final Logger LOG = Logger.getLogger(PersonDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      insert();
      namedQuerySelect();
      //      namedNativeQuerySelect();
      scalarQuerySelect();
   }

   public static void insert()
      throws Exception
   {
      Person persion = new Person();
      persion.setFirstName("Luke");
      persion.setLastName("Ma");
      persion.setSsn("123-12-1234");
      persion.setHeight(5.6f);
      persion.setCity("Missouri City");
      persion.setState("TX");

      HibernateUtil.save(persion);
   }

   public static void namedQuerySelect()
   {
      HibernateUtil hibernateUtil = HibernateUtil.newInstance();
      Query query = hibernateUtil.getNamedQuery("named.query.object");

      @SuppressWarnings("unchecked")
      List results = hibernateUtil.list(query);

      for (Object result : results)
      {
         String name1 = result.getClass().getName();

         LOG.info("result type: " + name1);

         LOG.info(((Person) result).toString());
      }
   }

   /**
     * Pure named native query not supported yet.
     */
   public static void namedNativeQuerySelect()
   {
      HibernateUtil hibernateUtil = HibernateUtil.newInstance();
      Query query = hibernateUtil.getNamedQuery("named.query.native");

      @SuppressWarnings("unchecked")
      List results = hibernateUtil.list(query);

      for (Object result : results)
      {
         String name1 = result.getClass().getName();

         LOG.info("result type: " + name1);

         LOG.info(((Person) result).toString());
      }
   }

   public static void scalarQuerySelect()
   {
      //final String SCALAR_QUERY = "select *, height from People";
      final String SCALAR_QUERY = "select id, firstName, lastName, height, city, state, ssn, height from People";
      HibernateUtil hibernateUtil = HibernateUtil.newInstance();
      SQLQuery query = hibernateUtil.createSQLQuery(SCALAR_QUERY);

      query.addEntity(Person.class).addScalar("height");

      @SuppressWarnings("unchecked")
      List results = hibernateUtil.list(query);

      for (Object result : results)
      {
         Object[] scalar = (Object[]) result;

         Person person = (Person) scalar[0];
         float height = (Float) scalar[1];

         LOG.info("Person: " + person);
         LOG.info("Height: " + height);
      }
   }
}
