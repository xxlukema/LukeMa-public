package com.learn.mdb.topic;


import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;


@MessageDriven(name = "GreetingsTopic2MDB", activationConfig = {
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/GreetingsTopic"),
   //   @ActivationConfigProperty(propertyName = "user", propertyValue = "luke"),
      @ActivationConfigProperty(propertyName = "clientId", propertyValue = "GreetingsTopicMDB"),
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class GreetingsTopic2MDB
   implements MessageListener
{
   protected static final Logger LOG = Logger.getLogger(GreetingsTopic2MDB.class);

   @Resource
   private MessageDrivenContext  messageDrivenContext;

   public void onMessage(Message message)
   {
      try
      {
         LOG.info("----------------");
         LOG.info("GreetingsTopic2MDB: Received topic/GreetingsTopic.");

         if (message instanceof TextMessage)
         {
            String text = ((TextMessage) message).getText();
            LOG.info("GreetingsTopic2MDB: Received text: " + text);
         }

         LOG.info("----------------");
      }
      catch (Exception e)
      {
         LOG.error("MDB Exception.", e);

         messageDrivenContext.setRollbackOnly();
      }
   }

}
