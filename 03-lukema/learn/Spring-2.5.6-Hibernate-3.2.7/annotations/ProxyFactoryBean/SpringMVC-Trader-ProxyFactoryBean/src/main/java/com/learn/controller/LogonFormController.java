package com.learn.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.learn.bean.Customer;
import com.learn.command.Credentials;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.util.SpringBeanFactory;
import com.learn.util.StringConstants;


public class LogonFormController
   extends SimpleFormController
{
   private static final Logger LOG = Logger.getLogger(LogonFormController.class);

   protected Object formBackingObject(HttpServletRequest request)
      throws Exception
   {
      LOG.debug("Inside formBackingObject()...");

      Credentials command = (Credentials) super.formBackingObject(request);

      // Set default
      command.setUsername(StringConstants.GUEST_USERNAME);
      command.setPassword(StringConstants.GUEST_PASSWORD);

      return command;
   }

   public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
      throws ServletException
   {
      LOG.debug("Inside onSubmit()...");

      String formView = getFormView();
      String successView = getSuccessView();

      LOG.debug("formView: " + formView);
      LOG.debug("successView: " + successView);

      boolean logonPassed = false;
      Credentials credentials = (Credentials) command;

      String username = credentials.getUsername();
      if (username.equals(StringConstants.GUEST_USERNAME))
      {
         String password = credentials.getPassword();

         CustomerService customerService = SpringBeanFactory.getBean("customerService");

         Customer customer = null;
         
         try
         {
            customer = customerService.getCustomer();
         }
         catch (AppException ae)
         {
            throw new ServletException("Exception getting Customer.", ae);
         }
         
         if (customer.getPassword().equals(password))
         {
            logonPassed = true;
         }
      }

      if (logonPassed)
      {
         request.getSession().setAttribute(StringConstants.SESSION_ATTRIBUTE_USERNAME, StringConstants.GUEST_USERNAME);
         return new ModelAndView(new RedirectView(getSuccessView()));
      }
      else
      {
         return new ModelAndView(new RedirectView(StringConstants.LOGON_PAGE));
      }
   }

}
