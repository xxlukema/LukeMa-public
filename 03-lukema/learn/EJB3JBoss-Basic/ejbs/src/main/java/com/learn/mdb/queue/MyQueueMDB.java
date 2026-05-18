package com.learn.mdb.queue;


import javax.annotation.Resource;
import javax.annotation.security.RunAs;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;


@MessageDriven(name = "MyQueueMDB", activationConfig = {
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/MyQueue"),
      @ActivationConfigProperty(propertyName = "user", propertyValue = "guest"),
      @ActivationConfigProperty(propertyName = "password", propertyValue = "guest"),
      @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MyQueueMDB"),
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
      })
@RunAs("guest")    
public class MyQueueMDB
   implements MessageListener
{
   protected static final Logger LOG = Logger.getLogger(MyQueueMDB.class);

   @Resource
   private MessageDrivenContext  messageDrivenContext;

   //@Resource(mappedName = "jnp://localhost:1100/queue/test")
   //private Queue                         testQ;

   public void onMessage(Message message)
   {
      try
      {
         LOG.info("----------------");
         LOG.info("Received queue/MyQueue.");

         if (message instanceof TextMessage)
         {
            String text = ((TextMessage) message).getText();
            LOG.info("Received text: " + text);
         }

         LOG.info("----------------");
      }
      catch (Exception e)
      {
         e.printStackTrace();

         messageDrivenContext.setRollbackOnly();
      }
   }

}
