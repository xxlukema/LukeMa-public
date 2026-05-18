package com.learn.jsf.util;


import java.net.UnknownHostException;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JsfUtils {
    private static final Logger LOG = LogManager.getLogger();

    public static UIComponent getUIComponentFromFacesEvent(FacesEvent facesEvent) {
        if (facesEvent == null) {
            LOG.debug("facesEvent == null.");
            return null;
        }

        UIComponent uiComponent = facesEvent.getComponent();
        if (uiComponent == null) {
            LOG.debug("uiComponent == null.");
        }

        return uiComponent;
    }

    public static <T> T getAttributeFromFacesEvent(String attributeName, FacesEvent facesEvent) {
        UIComponent uiComponent = getUIComponentFromFacesEvent(facesEvent);
        if (uiComponent == null) {
            return null;
        }
        else {
            Map<String, Object> attributeMap = uiComponent.getAttributes();
            Object object = attributeMap.get(attributeName);
            if (object == null) {
                LOG.debug("ValueExpression object is null for attribute " + attributeName);
                return null;
            }
            else {
                LOG.debug("Atrribute object type: " + object.getClass().getCanonicalName());

                @SuppressWarnings("unchecked")
                T t = (T) object;
                return t;
            }
        }
    }

    /*
     * public static <T> T getExpressionAttributeFromFacesEvent(String
     * attributeName, FacesEvent facesEvent) { UIComponent uiComponent =
     * getUIComponentFromFacesEvent(facesEvent); if (uiComponent == null) {
     * return null; } else { ValueExpression valueExpression =
     * uiComponent.getValueExpression(attributeName); if (valueExpression ==
     * null) { LOG.debug("valueExpression == null for attribute " +
     * attributeName); return null; } else { ELContext elContext =
     * getELContext();
     * 
     * Object object = valueExpression.getValue(elContext);
     * LOG.debug("ValueExpression type: " +
     * object.getClass().getCanonicalName());
     * 
     * @SuppressWarnings("unchecked") T t = (T) object; return t; } } }
     */
    public static Application getApplication() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();

        return application;
    }

    /* private static ELContext getELContext()
     {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ELContext elContext = facesContext.getELContext();
    
      return elContext;
     }
    
     public static <T> T getBean(String beanName)
     {
      Application application = getApplication();
      ELResolver elResolver = application.getELResolver();
      ELContext elContext = getELContext();
      Object object = elResolver.getValue(elContext, null, beanName);
    
      // LOG.debug("ValueExpression type: " + object.getClass().getCanonicalName());
    
      @SuppressWarnings("unchecked")
      T t = (T) object;
      return t;
     }
    
     public static <T> T createBean(String beanName, Class<?> beanClass)
     {
      Application application = getApplication();
      ExpressionFactory expressionFactory = application.getExpressionFactory();
      ELContext elContext = getELContext();
      String beanExpression = "#{" + beanName + "}";
      ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, beanExpression, beanClass);
      Object object = valueExpression.getValue(elContext);
    
      //LOG.debug("ValueExpression type: " + object.getClass().getCanonicalName());
    
      @SuppressWarnings("unchecked")
      T t = (T) object;
    
      return t;
     }
    */
    public static String getRemoteAddress()
        throws UnknownHostException {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String ip = null;
        if (httpServletRequest != null) {
            ip = httpServletRequest.getRemoteAddr();
            LOG.debug("IP = " + ip);
        }

        return ip;
    }

    public static void addSessionAttribue(String name, Object value) {
        HttpSession httpSession = getHttpSession();
        httpSession.setAttribute(name, value);
    }

    public static <T> T getSessionAttribue(String name) {
        HttpSession httpSession = getHttpSession();
        Object object = httpSession.getAttribute(name);

        @SuppressWarnings("unchecked")
        T t = (T) object;

        return t;
    }

    public static HttpSession getHttpSession() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        HttpSession httpSession = null;
        if (httpServletRequest != null) {
            httpSession = httpServletRequest.getSession();
        }

        return httpSession;
    }

    private static ExternalContext getExternalContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        return externalContext;
    }

    public static HttpServletRequest getHttpServletRequest() {
        ExternalContext externalContext = getExternalContext();

        Object object = externalContext.getRequest();
        if (object instanceof HttpServletRequest) {
            return (HttpServletRequest) object;
        }
        else {
            LOG.error("Request type: " + object.getClass().getCanonicalName());
            return null;
        }
    }

    public static HttpServletResponse getHttpServletResponse() {
        ExternalContext externalContext = getExternalContext();

        Object object = externalContext.getResponse();
        if (object instanceof HttpServletResponse) {
            return (HttpServletResponse) object;
        }
        else {
            LOG.error("Request type: " + object.getClass().getCanonicalName());
            return null;
        }
    }

    public static float timeInSecondsFromStart(long startMilisec) {
        long end = System.currentTimeMillis();
        float seconds = (float) (end - startMilisec) / 1000;
        return seconds;
    }
}
