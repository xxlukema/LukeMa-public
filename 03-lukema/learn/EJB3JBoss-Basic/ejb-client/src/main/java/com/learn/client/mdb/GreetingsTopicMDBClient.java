package com.learn.client.mdb;


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


public class GreetingsTopicMDBClient
{
    protected static final Logger LOG = Logger.getLogger(GreetingsTopicMDBClient.class);

    @Test
    public void sendTextToTopic()
        throws Exception
    {
        Context context = LookupUtils.getInitialContext();

        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context
                .lookup("ConnectionFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        TopicSession topicSession = topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        Topic topic = (Topic) context.lookup(LookupConstants.JNDI_TOPIC_NAME);
        TopicPublisher topicPublisher = topicSession.createPublisher(topic);

        try
        {
            /**
             * For messageSelector
             */
            TextMessage textMessage1 = topicSession.createTextMessage("Hello World With Red Color and intSelectorValue = 1");
            textMessage1.setStringProperty("color", "Red");
            textMessage1.setIntProperty("intSelectorValue", 1);
            topicPublisher.send(textMessage1);

            TextMessage textMessage2 = topicSession.createTextMessage("Hello World Green");
            textMessage1.setStringProperty("color", "Green");
            textMessage2.setIntProperty("selectorValue", 2);
            topicPublisher.send(textMessage2);

            TextMessage textMessage3 = topicSession.createTextMessage("Hello World");
            topicPublisher.send(textMessage3);

            LOG.info("Three messages sent successfully to remote topic.");
        }
        finally
        {
            try
            {
                topicPublisher.close();
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
