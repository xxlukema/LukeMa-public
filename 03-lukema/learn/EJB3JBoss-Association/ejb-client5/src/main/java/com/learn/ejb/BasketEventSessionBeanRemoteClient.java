package com.learn.ejb;


import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.learn.session.BasketEventSessionBeanRemote;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class BasketEventSessionBeanRemoteClient
{
   protected static final Logger        LOG = Logger.getLogger(BasketEventSessionBeanRemoteClient.class);

   private BasketEventSessionBeanRemote beanRemote;

   @Before
   public void setUp()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_BasketEventSessionBean);

      beanRemote = (BasketEventSessionBeanRemote) PortableRemoteObject.narrow(object,
            BasketEventSessionBeanRemote.class);
   }

   @After
   public void tearDown()
      throws Exception
   {
   }

   @Test
   public void runMe()
      throws Exception
   {
      addRecord();
      retrieveData();
      
      updateRecord();
      retrieveData();
      
      removeRecord();
      retrieveData();
   }

   @Ignore
   @Test
   public void addRecord()
      throws Exception
   {
      beanRemote.addRecord();
   }

   @Ignore
   @Test
   public void retrieveData()
      throws Exception
   {
      beanRemote.retrieveData();
   }

   @Ignore
   @Test
   public void updateRecord()
      throws Exception
   {
      beanRemote.updateRecord();
   }

   @Ignore
   @Test
   public void removeRecord()
      throws Exception
   {
      beanRemote.removeRecord();
   }
}
