package com.learn.mdb.topic;


import java.util.Enumeration;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;


@MessageDriven(name = "BasketEventMDB", activationConfig = {
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "/topic/ChangeNotificationTopic"),
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class BasketEventMDB
   implements MessageListener
{
   @Resource
   private MessageDrivenContext  messageDrivenContext;

   protected static final Logger LOG = Logger.getLogger(BasketEventMDB.class);

   public void onMessage(Message message)
   {
      try
      {
         LOG.info("----------------");
         LOG.info("### BasketEventMDB: Received topic/ChangeNotificationTopic.");

         if (message instanceof TextMessage)
         {
            LOG.info("### BasketEventMDB: Received text message.");
            String text = ((TextMessage) message).getText();
            LOG.info("### BasketEventMDB: Received text: " + text);
         }
         else
         {
            if (message instanceof MapMessage)
            {
               LOG.info("### BasketEventMDB: Received map message.");
            }
            else
            {
               LOG.info("### BasketEventMDB: Received non-text/non-map message.");
            }
            
            @SuppressWarnings("unchecked")
            Enumeration<String> enumeration = message.getPropertyNames();
            while (enumeration.hasMoreElements())
            {
               String peroperty = enumeration.nextElement();
               Object object = message.getObjectProperty(peroperty);

               LOG.info("### " + peroperty + ": " + object);
            }
         }

         LOG.info("----------------");
      }
      catch (Exception e)
      {
         LOG.error("### Error with BasketEventMDB.", e);
         messageDrivenContext.setRollbackOnly();
      }
   }

}
