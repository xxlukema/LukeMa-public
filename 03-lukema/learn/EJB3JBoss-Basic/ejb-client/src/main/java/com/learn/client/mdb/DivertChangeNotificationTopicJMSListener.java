package com.learn.client.mdb;


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

import com.learn.util.LookupUtils;


public class DivertChangeNotificationTopicJMSListener
{
    protected static final Logger LOG = Logger.getLogger(DivertChangeNotificationTopicJMSListener.class);

    @Test
    public void receive()
        throws Exception
    {
        Context context = LookupUtils.getInitialContext();

        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context
                .lookup("ConnectionFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        Topic topic = (Topic) context.lookup("/topic/ChangeNotificationTopic");

        topicConnection.start();
        TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);

        try
        {
            Message message = topicSubscriber.receive();

            LOG.info("########################## DivertChangeNotificationTopicJMSListener Message Received: #####################");
            LOG.info(message.toString());
            
            if (message instanceof TextMessage)
            {
               String text = ((TextMessage) message).getText();
               LOG.info("###### DivertChangeNotificationTopicJMSListener: Received text: " + text);
            }
            
            LOG.info("##########################   DivertChangeNotificationTopicJMSListener End of Message  #####################");
        }
        finally
        {
            try
            {
                topicSubscriber.close();
            }
            catch (Throwable t)
            {
            }

            try
            {
                topicSession.close();
            }
            catch (Throwable t)
            {
            }

            try
            {
                topicConnection.close();
            }
            catch (Throwable t)
            {
            }
        }
    }
}
