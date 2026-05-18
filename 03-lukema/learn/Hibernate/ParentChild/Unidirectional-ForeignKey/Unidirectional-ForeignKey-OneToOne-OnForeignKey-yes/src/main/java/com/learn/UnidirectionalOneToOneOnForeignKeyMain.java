package com.learn;


import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class UnidirectionalOneToOneOnForeignKeyMain
   extends OneToOneMainBase
{
   private static final Logger LOG = Logger.getLogger(UnidirectionalOneToOneOnForeignKeyMain.class);

   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      // One directional. Candice sees Luke.
      retrieveChildren();

      // One directional. Luke can not see Candice.
      retrieveParents();
   }

   public static void addRecord()
      throws Exception
   {
      // Luke Ma
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      Child candice = new Child();
      candice.setName("Candice Ma");
      candice.setParent(luke);

      HibernateUtils.saveOrUpdate(candice);

      // This will cause exceptions because this is one-to-one relationship. 
      try
      {
         Child natalie = new Child();
         natalie.setName("Natalie Ma");
         natalie.setParent(luke);

         HibernateUtils.saveOrUpdate(natalie);

         Assert.fail("Unique constraint violation.");
      }
      catch (Exception e)
      {
         LOG.info("Unique key violation: " + e.getMessage());
      }
   }
}
