package com.learn.webservice;


import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.learn.session.BookSessionBeanLocal;


@Stateless
@WebService
public class MyWebServiceBean
   implements MyWebServiceLocal
{
   protected static final Logger  LOG = Logger.getLogger(MyWebServiceBean.class);

   @EJB
   protected BookSessionBeanLocal bookSessionBeanLocal;

   @WebMethod
   public String test(@WebParam(name = "input") Integer input)
   {
      LOG.info("Your input: " + input);

      try
      {
         bookSessionBeanLocal.testCommit();
      }
      catch (Exception e)
      {
         LOG.error("Exception with WebService", e);
      }

      return new Date().toString();
   }

}
