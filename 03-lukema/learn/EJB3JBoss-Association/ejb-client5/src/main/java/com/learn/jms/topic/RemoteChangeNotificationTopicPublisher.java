package com.learn.jms.topic;


import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class RemoteChangeNotificationTopicPublisher
{
   protected static final Logger LOG = Logger.getLogger(RemoteChangeNotificationTopicPublisher.class);

   @Test
   public void sendTextToTopic()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext(LookupConstants.PROVIDER_URL_DB);

      TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context
            .lookup("ConnectionFactory");
      TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
      TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

      Topic topic = (Topic) context.lookup(LookupConstants.JNDI_TOPIC_NAME_CHANGE_NOTIFICATION);
      TopicPublisher topicPublisher = topicSession.createPublisher(topic);

      TextMessage textMessage = topicSession.createTextMessage("Hello World.");
      topicPublisher.send(textMessage);
      LOG.info("Message sent successfully to remote queue.");
      topicConnection.close();

   }
}
