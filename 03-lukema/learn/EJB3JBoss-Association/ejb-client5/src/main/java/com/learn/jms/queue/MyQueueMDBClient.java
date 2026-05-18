package com.learn.jms.queue;


import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class MyQueueMDBClient
{
   protected static final Logger LOG = Logger.getLogger(MyQueueMDBClient.class);

   @Test
   public void sendTextToQueue()
      throws Exception
   {
      Context context = LookupUtils.getInitialContext();

      QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
            .lookup("ConnectionFactory");
      QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
      QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

      Queue queue = (Queue) context.lookup(LookupConstants.JNDI_QUEUE_NAME);
      QueueSender queueSender = queueSession.createSender(queue);

      TextMessage textMessage = queueSession.createTextMessage("Hello World");
      queueSender.send(textMessage);
      LOG.info("Message sent successfully to remote queue.");
      queueConnection.close();

   }
}
