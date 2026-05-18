package com.learn.ejb;


import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.session.SwapSessionBeanRemote;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class SwapSessionBeanRemoteClient
{
   protected static final Logger LOG = Logger.getLogger(SwapSessionBeanRemoteClient.class);

   // @Ignore("View 'idobj_gen_view' is not updatable because the FROM clause names multiple tables.")
   @Test
   public void testAssociation()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_SwapSessionBean);

      SwapSessionBeanRemote beanRemote = (SwapSessionBeanRemote) PortableRemoteObject.narrow(object,
            SwapSessionBeanRemote.class);

      beanRemote.addRecord();

      beanRemote.retrieveData();

     // beanRemote.removeData();

   }

}
