package com.learn.client;


import org.apache.log4j.Logger;

import com.learn.webservice.MyWebServiceBean;
import com.learn.webservice.MyWebServiceBeanService;


public class WebServiceClient
{
   protected static final Logger            LOG     = Logger.getLogger(WebServiceClient.class);

   protected static MyWebServiceBeanService service = new MyWebServiceBeanService();

   protected static MyWebServiceBean        proxy   = service.getMyWebServiceBeanPort();

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Begin test: ");

      String response = proxy.test(12);

      LOG.info("Response: " + response);
   }

}
