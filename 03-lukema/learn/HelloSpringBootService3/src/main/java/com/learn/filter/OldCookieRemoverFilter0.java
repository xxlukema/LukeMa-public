package com.learn.filter;


import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

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
import jakarta.servlet.http.HttpServletResponse;
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
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class OldCookieRemoverFilter0
    implements Filter {

    private static final String BAR_BEGIN = "\n--------------------- OldCookieRemoverFilter0 begin ---------------------";
    private static final String BAR_END = "--------------------- OldCookieRemoverFilter0 end ---------------------\n";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse res = (HttpServletResponse) response;

        StringBuilder sb = new StringBuilder(BAR_BEGIN).append(System.lineSeparator());

        /**
         * 2. Request [Method] Full URL
         */
        String requestStr = String.format("[%s] %s", req.getMethod(), req.getRequestURL());
        sb.append(requestStr);

        log.debug(() -> sb);

        Cookie[] cookies = req.getCookies();

        AtomicBoolean isCookieFound = new AtomicBoolean();

        if (cookies == null || cookies.length == 0) {
            log.debug(() -> "No cookie.");
        } else {
            log.debug("cookie size: {}", () -> cookies.length);

            // cookies are in cookie header.
            Arrays.asList(cookies).stream()
                    // .filter(item -> item.getMaxAge())
                    .forEach(item -> {
                        log.debug("cookie name: {}, maxAge: {}", item.getName(), item.getMaxAge());
                        isCookieFound.set(true);
                    });
        }

        chain.doFilter(request, response);

        log.debug("Response Status Code is: {}", () -> res.getStatus());
        log.debug(() -> BAR_END);

    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
