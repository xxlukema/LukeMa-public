package com.learn.auto;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.learn.auto.bean.AutoChildOne;
import com.learn.auto.bean.AutoChildOneChild;
import com.learn.auto.bean.AutoChildTwo;
import com.learn.auto.bean.AutoParent;
import com.learn.hibernate.HibernateUtils;


public class AutoSaveOrUpdateDriver
{
   private static final Logger LOG = Logger.getLogger(AutoSaveOrUpdateDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      addRecord();
      retrieveData();
   }

   public static void addRecord()
      throws Exception
   {
      AutoParent parent1 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent1);

      AutoChildOne childOne1 = new AutoChildOne();
      childOne1.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne1);

      AutoChildOne childOne2 = new AutoChildOne();
      childOne2.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne2);

      AutoChildOne childOne3 = new AutoChildOne();
      childOne3.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne3);

      AutoChildTwo childTwo1 = new AutoChildTwo();
      childTwo1.setParent(parent1);
      HibernateUtils.saveOrUpdate(childTwo1);

      AutoChildOneChild childOneChild1 = new AutoChildOneChild();
      childOneChild1.setParent(childOne2);
      HibernateUtils.saveOrUpdate(childOneChild1);

      AutoParent parent2 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent2);

      AutoChildOne childOne4 = new AutoChildOne();
      childOne4.setParent(parent2);
      HibernateUtils.saveOrUpdate(childOne4);

      AutoParent parent3 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent3);

      LOG.info("Data Initiated.");
   }

   public static void retrieveData()
      throws Exception
   {
      LOG.info("Inside retrieveData()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Criteria criteria = session.createCriteria(AutoParent.class);

         @SuppressWarnings("unchecked")
         List<AutoParent> autoParents = criteria.list();
         for (AutoParent autoParent : autoParents)
         {
            autoParent.print();
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
