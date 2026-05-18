/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.learn.esb;


import org.apache.log4j.Logger;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.junit.Test;


public class SendEsbMessage
{
   protected static final Logger LOG = Logger.getLogger(SendEsbMessage.class);

   @Test
   public void sendTextToQueue()
      throws Exception
   {
      System.setProperty("javax.xml.registry.ConnectionFactoryClass",
            "org.apache.ws.scout.registry.ConnectionFactoryImpl");

      String category = "FirstServiceESB";
      String name = "SimpleListener";
      String text = "Message sent from Esb client.";

      Message esbMessage = MessageFactory.getInstance().getMessage();
      esbMessage.getBody().add(text);

      ServiceInvoker serviceInvoker = new ServiceInvoker(category, name);
      serviceInvoker.deliverAsync(esbMessage);

      Message replyMessage = serviceInvoker.deliverSync(esbMessage, 20000);
      LOG.info("Reply: " + replyMessage.getBody().get());

   }

}
