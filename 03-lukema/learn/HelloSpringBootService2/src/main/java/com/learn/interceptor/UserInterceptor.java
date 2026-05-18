package com.learn.interceptor;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;


/**
 * Or implements AsyncHandlerInterceptor
 */
@Log4j2
public class UserInterceptor
    implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception exception)
        throws Exception {

        log.debug(() -> "Called");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView)
        throws Exception {

        log.debug(() -> "Called");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        log.debug(() -> "Called");

        // HandlerMethod handlerMethod = (HandlerMethod) handler;

        Enumeration<String> enumeration = request.getHeaderNames();

        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement();
            String value = request.getHeader(header);
            log.debug(() -> header + ": " + value);
        }

        return true;
    }

}
