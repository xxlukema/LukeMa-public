package com.learn.util;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class LookupUtils
{
   protected static final Logger LOG = Logger.getLogger(LookupUtils.class);

   public static Context getInitialContext()
      throws NamingException
   {
      return getInitialContext(LookupConstants.PROVIDER_URLS[0]);
   }

   public static Context getInitialContext(String providerUrl)
      throws NamingException
   {
      Properties properties = new Properties();
      properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
      properties.setProperty(Context.PROVIDER_URL, providerUrl);
      properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
      properties.setProperty(Context.SECURITY_PRINCIPAL, "employee");
      properties.setProperty(Context.SECURITY_CREDENTIALS, "password");

      return new InitialContext(properties);
   }
}
