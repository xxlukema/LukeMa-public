package com.learn.ejb;


import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.session.LukeProcSessionBeanRemote;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class LukeProcSessionBeanRemoteClient
{
   protected static final Logger LOG = Logger.getLogger(LukeProcSessionBeanRemoteClient.class);

   @Test
   public void testAssociation()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_LukeProcSessionBean);

      LukeProcSessionBeanRemote beanRemote = (LukeProcSessionBeanRemote) PortableRemoteObject.narrow(object,
            LukeProcSessionBeanRemote.class);

      beanRemote.execute();
   }

}
