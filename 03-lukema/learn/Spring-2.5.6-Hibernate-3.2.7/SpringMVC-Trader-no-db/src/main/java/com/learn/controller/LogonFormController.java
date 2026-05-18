package com.learn.controller;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.BindException;

import org.apache.log4j.Logger;

import com.learn.command.Credentials;
import com.learn.common.Constant;


public class LogonFormController extends SimpleFormController
{
   private static final Logger LOG = Logger.getLogger(LogonFormController.class);

   protected Object formBackingObject(HttpServletRequest request)
   throws Exception
   {
      LOG.debug("Inside formBackingObject()...");

      Credentials command = (Credentials) super.formBackingObject(request);

      command.setUsername("guest");
      command.setPassword("guest");

      request.setAttribute("group", "FuelQuest Group");

      return command;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors)
   throws ServletException
   {
      LOG.debug("Inside onSubmit()...");

      String fv = getFormView();
      String sv = getSuccessView();

      LOG.debug("formView: " + fv);
      LOG.debug("successView: " + sv);

      request.getSession().setAttribute("usr", "guest");

      //return new ModelAndView(new RedirectView(Constant.LOGON_PAGE));

      return new ModelAndView(new RedirectView(getSuccessView()));
   }
}

