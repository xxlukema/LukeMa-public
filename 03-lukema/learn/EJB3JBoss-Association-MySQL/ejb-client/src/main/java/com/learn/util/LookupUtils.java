package com.learn.util;


import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class LookupUtils
{
   protected static final Logger LOG                             = Logger.getLogger(LookupUtils.class);

   public static final String    JNDI_AutoAssociationSessionBean = "EJB3JBoss-packaging/AutoAssociationSessionBean/remote";

   public static final String    JNDI_QUEUE_NAME                 = "queue/MyQueue";

   public static final String    JNDI_TOPIC_NAME                 = "topic/GreetingsTopic";

   private static Context        context                         = null;

   private static final Lock     LOCK                            = new ReentrantLock();

   public static Context getInitialContext()
      throws NamingException
   {
      if (context == null)
      {
         LOCK.lock();
         try
         {
            if (context == null)
            {
               Properties properties = new Properties();
               properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
               properties.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
               properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");

               properties.setProperty(Context.SECURITY_PRINCIPAL, "user1");
               properties.setProperty(Context.SECURITY_CREDENTIALS, "password1");

               context = new InitialContext(properties);
            }
         }
         finally
         {
            LOCK.unlock();
         }
      }

      return context;
   }

}
