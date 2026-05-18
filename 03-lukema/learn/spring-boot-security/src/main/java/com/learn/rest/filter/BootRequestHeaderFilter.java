package com.learn.rest.filter;


import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


/**
 * @Component vs @WebFilter(filterName = "myBootWebFilter", urlPatterns = { "/*" }):
 * 
 * 1. @WebFilter can add url pattern, but @Component cannot.
 * 2. @Component requires to add @ServletComponentScan annotation in your @SpringBootApplication.
 * 3. @Component can be used together with @Order(Ordered.HIGHEST_PRECEDENCE). The lower the @Order number, the higher the precedence.
 * 4. When @WebFilter is used with @Order, the @Order is ignored. 
 * 5. WARNING: When using @Component, if the Controller in Spring Boot returns to a JSP file, the request will pass the filter twice.
 * 6. @WebFilter(filterName = "bootRequestHeaderFilter", urlPatterns = { "/*" })
 */
@Log4j2
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class BootRequestHeaderFilter
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- BootRequestHeaderFilter begin ---------------------";
    private static final String BAR_END = "--------------------- BootRequestHeaderFilter end ---------------------";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        /**
         * 1. Set @RequestAttribute
         */
        request.setAttribute("requestId", 1);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        /**
         * 2. Request header.
         */
        StringBuilder sb = new StringBuilder(BAR_BEGIN);
        sb.append(System.lineSeparator());
        Enumeration<String> it = req.getHeaderNames();
        while (it.hasMoreElements()) {
            String key = it.nextElement();
            String value = req.getHeader(key);
            sb.append(key).append(": \t").append(value);
            sb.append(System.lineSeparator());
        }

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            sb.append("No Cookies.");
            sb.append(System.lineSeparator());
        } else {
            sb.append("Cookies: ").append(Arrays.asList(cookies).toString());
            sb.append(System.lineSeparator());
        }

        /**
         * 3. Request Path and Method
         */
        sb.append("requestURL: \t").append(req.getRequestURL()).append(System.lineSeparator());
        sb.append("method: \t").append(req.getMethod());

        log.debug(() -> sb.toString());

        chain.doFilter(request, response);

        log.debug("Response Status Code is: {}", () -> res.getStatus());

        log.debug(() -> BAR_END);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
