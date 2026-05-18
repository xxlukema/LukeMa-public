package com.learn.jms.topic;


import java.util.Enumeration;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class RemoteChangeNotificationTopicSubscriber
{
   protected static final Logger LOG = Logger.getLogger(RemoteChangeNotificationTopicSubscriber.class);

   @Test
   public void receive()
      throws Exception
   {
      /*RemoteServiceLocator remoteServiceLocator = RemoteServiceLocator
            .getInstance("jnp://loneqessappd1.uk.db.com:1199");
      TopicConnectionFactory topicConnectionFactory = remoteServiceLocator
            .getTopicConnectionFactory("ConnectionFactory");
      topic = remoteServiceLocator.getTopic("/topic/ChangeNotificationTopic");
      topicConnection = topicConnectionFactory.createTopicConnection();
      topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);*/

      //Context context = LookupUtils.getInitialContext();
      Context context = LookupUtils.getInitialContext(LookupConstants.PROVIDER_URL_DB);

      TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context
            .lookup("ConnectionFactory");
      TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
      TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

      Topic topic = (Topic) context.lookup(LookupConstants.JNDI_TOPIC_NAME_CHANGE_NOTIFICATION);

      topicConnection.start();
      TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
      Message message = topicSubscriber.receive();

      LOG.info("########################## Message Received: #####################");

      if (message instanceof TextMessage)
      {
         LOG.info("### BasketEventJMSListener: Received text message.");
         String text = ((TextMessage) message).getText();
         LOG.info("### BasketEventJMSListener: Received text: " + text);
      }
      else
      {
         if (message instanceof MapMessage)
         {
            LOG.info("### BasketEventJMSListener: Received map message.");
         }
         else
         {
            LOG.info("### BasketEventJMSListener: Received non-text/non-map message.");
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

      LOG.info("########################## End of Message #####################");

      topicConnection.close();

   }
}
