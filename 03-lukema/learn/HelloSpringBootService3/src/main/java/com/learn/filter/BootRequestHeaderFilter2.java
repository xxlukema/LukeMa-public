package com.learn.filter;


import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@Log4j2
public class BootRequestHeaderFilter2
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- BootRequestHeaderFilter2 begin ---------------------";
    // private static final String BAR_END = "--------------------- BootRequestHeaderFilter2 end ---------------------";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        /**
         * 1. Set @RequestAttribute
         */
        request.setAttribute("requestId", 1);

        // HttpServletResponse res = (HttpServletResponse) response;

        StringBuilder sb = new StringBuilder(BAR_BEGIN).append(System.lineSeparator());

        /**
         * 2. Request [Method] Full URL
         */
        String requestStr = String.format("[%s] %s", req.getMethod(), req.getRequestURL());
        sb.append(requestStr).append(System.lineSeparator());

        /**
         * 3. Request header.
         */
        Enumeration<String> it = req.getHeaderNames();
        while (it.hasMoreElements()) {
            String key = it.nextElement();
            String value = req.getHeader(key);
            sb.append("Header['").append(key).append("']: \t").append(value).append(System.lineSeparator());
        }

        Cookie[] cookies = req.getCookies();
        if (cookies == null || cookies.length == 0) {
            sb.append("No cookie.").append(System.lineSeparator());
        } else {
            // cookies are in cookie header.
        }

        /**
         * 4. Request parameters
         */
        if (req.getParameterMap().isEmpty()) {
            sb.append("Parameters: none").append(System.lineSeparator());
        } else {
            req.getParameterMap().forEach((key, value) -> {
                sb.append("Parameter ").append(key).append(": ").append(Arrays.asList(value)).append(System.lineSeparator());
            });
        }

        int index = sb.lastIndexOf(System.lineSeparator());
        if (index > -1) {
            sb.delete(index, sb.length());
        }
        log.debug(() -> sb.toString());

        chain.doFilter(request, response);

        // log.debug("Response Status Code is: {}", () -> res.getStatus());

        // log.debug(() -> BAR_END);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
