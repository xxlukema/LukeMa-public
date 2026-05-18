package com.learn;


import org.apache.log4j.Logger;
import org.junit.Assert;

import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class Main
   extends OneToManySetMainBase
{
   private static final Logger LOG = Logger.getLogger(Main.class);

   public static void main(String[] args)
      throws Exception
   {
      addRecord();
      retrieveParents();

      try
      {
         deleteParents();
         Assert.fail("Can not delete a parent when the parent has child/children.");
      }
      catch (Throwable t)
      {
         LOG.info("Detected an integrity constraint violated: " + t.getMessage());
      }

      deleteChildren();
      deleteParents();
      
      retrieveParents();
      retrieveChildren();
   }

   public static void addRecord()
      throws Exception
   {
      Parent luke = new Parent();
      luke.setName("Luke Ma");

      // Since cascade is none, all objects have to be saved individually. 
      HibernateUtils.saveOrUpdate(luke);

      Child candice = new Child();
      candice.setName("Candice Ma");
      candice.setParent(luke);

      // Since cascade is none, all objects have to be saved individually. 
      HibernateUtils.saveOrUpdate(candice);
   }

}
