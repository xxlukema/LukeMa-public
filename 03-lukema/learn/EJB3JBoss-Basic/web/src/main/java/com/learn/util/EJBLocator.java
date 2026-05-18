package com.learn.util;


import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.learn.session.BookSessionBeanLocal;


public class EJBLocator
{
   protected static final Logger LOG  = Logger.getLogger(EJBLocator.class);

   protected static final String JNDI = "EJB3JBoss-packaging/BookSessionBean/local";

   public static void testBean()
   {
      try
      {
         Context context = new InitialContext();

         Object object = context.lookup(JNDI);

         if (object != null)
         {
            BookSessionBeanLocal beanLocal = (BookSessionBeanLocal) object;

            beanLocal.testCommit();
         }
         else
         {
            LOG.error("ERROR Unable to find the bean.");
         }
      }
      catch (Throwable e)
      {
         LOG.error("ERROR with testBean().", e);
      }
   }

   public static void testMDB()
   {
      try
      {
         Context context = new InitialContext();

         Queue queue = (Queue) context.lookup("queue/MyQueue");
         QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
         QueueConnection cnn = factory.createQueueConnection();
         QueueSession session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

         TextMessage msg = session.createTextMessage("Hello World");

         QueueSender sender = session.createSender(queue);
         sender.send(msg);

         LOG.info("Message sent successfully to remote queue.");
      }
      catch (Throwable e)
      {
         LOG.error("ERROR with testMDB().", e);
      }
   }
}
