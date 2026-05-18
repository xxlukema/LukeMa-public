package com.learn.session;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.learn.proc.LukeProcExecutor;
import com.learn.proc.MyResource;
import com.learn.spring.SpringBeanFactory;


@DependsOn({ "ResourceBinder", "SpringJndiNameBinder" })
@Stateless
public class LukeProcSessionBean
   implements LukeProcSessionBeanLocal, LukeProcSessionBeanRemote
{
   private static final long     serialVersionUID = 1L;

   protected static final Logger LOG              = Logger.getLogger(LukeProcSessionBean.class);

   /*@Resource(name = "LukeProcExecutor")
   private LukeProcExecutor      lukeProcExecutor;*/

   /*@Resource(name = "MyResource")
   private MyResource            myResource;*/

   @Override
   public void execute()
      throws Exception
   {
      Map<String, Object> inputParams = new HashMap<String, Object>();

      inputParams.put("ticker", ".CSI300UT");

      LukeProcExecutor lukeProcExecutor = SpringBeanFactory.getBean("lukeProcExecutor");

      Map<String, Object> outputParams = lukeProcExecutor.executeProc(inputParams);

      for (String key : outputParams.keySet())
      {
         LOG.info(key + ": " + outputParams.get(key));
      }

      //myResource.info();
   }

   @PostConstruct
   public void bindResources()
   {
      LOG.info("################### LukeProcSessionBean.bindResources() invoked.");

      try
      {
         Context context = new InitialContext();

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

}
