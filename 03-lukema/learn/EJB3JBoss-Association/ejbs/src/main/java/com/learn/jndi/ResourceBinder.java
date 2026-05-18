/*package com.learn.jndi;


import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.learn.proc.MyResource;


@Startup
@Singleton
@Stateless
public class ResourceBinder
{
   protected static final Logger LOG = Logger.getLogger(ResourceBinder.class);

   public ResourceBinder()
   {
      LOG.info("################### ResourceBinder constructor.");

      try
      {
         Context context = new InitialContext();
         context.rebind("MyResource", new MyResource());

         LOG.info("Resource bound...");
         Object object = context.lookup("MyResource");
         MyResource myResource = (MyResource) PortableRemoteObject.narrow(object, MyResource.class);
         myResource.info();
      }
      catch (NamingException ex)
      {
         LOG.error("Cannot bind resource.", ex);
      }
   }

   @PostConstruct
   public void bindResources()
   {
      LOG.info("################### ResourceBinder.bindResources() invoked.");
   }
}
*/