package com.learn.ws;


import java.util.Date;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;


@Stateless
@WebService
public class MyWebServiceBean
   implements MyWebServiceLocal
{
   protected static final Logger LOG = Logger.getLogger(MyWebServiceBean.class);

   @WebMethod
   public String test(@WebParam(name = "input") Integer input)
   {
      LOG.info("Your input: " + input);

      return new Date().toString();
   }

}
