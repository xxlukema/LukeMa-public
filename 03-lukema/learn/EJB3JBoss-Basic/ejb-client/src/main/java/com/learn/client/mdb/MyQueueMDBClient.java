package com.learn.client.mdb;


import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;

import org.junit.Test;

import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class MyQueueMDBClient
{
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
      try
      {
         queueSender.send(textMessage);
         System.out.println("Message sent successfully to remote queue.");
      }
      finally
      {
          try
          {
              queueSender.close();
          }
          catch (Throwable t)
          {
          }
          
          try
          {
              queueSession.close();
          }
          catch (Throwable t)
          {
          }

          try
          {
              queueConnection.close();
          }
          catch (Throwable t)
          {
          }
      }
   }
}
