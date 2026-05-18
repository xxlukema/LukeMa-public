package com.learn.client.ejb;


import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.exception.AppException;
import com.learn.session.BookSessionBeanRemote;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class FirstEJB3TutorialClientJNDI
{
   protected static final Logger LOG = Logger.getLogger(FirstEJB3TutorialClientJNDI.class);

   @Test
   public void testBookBeanCommit()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_Book);

      BookSessionBeanRemote beanRemote = (BookSessionBeanRemote) PortableRemoteObject.narrow(object,
            BookSessionBeanRemote.class);

      beanRemote.testCommit();
   }

   @Test(expected = AppException.class)
   public void testBookBeanRollback()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_Book);

      BookSessionBeanRemote beanRemote = (BookSessionBeanRemote) PortableRemoteObject.narrow(object,
            BookSessionBeanRemote.class);

      beanRemote.testRollback();
   }

}
