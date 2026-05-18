package com.learn.ejb;


import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.session.AutoAssociationSessionBeanRemote;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class AutoAssociationSessionBeanRemoteClientJNDI
{
   protected static final Logger LOG = Logger.getLogger(AutoAssociationSessionBeanRemoteClientJNDI.class);

   @Test
   public void testAssociation()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      Object object = context.lookup(LookupConstants.JNDI_AutoAssociationSessionBean);

      AutoAssociationSessionBeanRemote beanRemote = (AutoAssociationSessionBeanRemote) PortableRemoteObject
            .narrow(object, AutoAssociationSessionBeanRemote.class);

      beanRemote.addRecord();
      beanRemote.retrieveData();
   }

}
