package com.learn.filter;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
 */
@Component
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
// @WebFilter(filterName = "myBootCorsFilter", urlPatterns = { "/spring/*", "/rest/*" })
public class BootCorsFilter1
    implements Filter {

    private static final String BAR_BEGIN = "-------------------- BootCorsFilter1 begin --------------------";
    /** private static final String BAR_END = "-------------------- BootCorsFilter1 end --------------------"; */

    @Value("${Access-Control-Allow-Origin:none}")
    String accessControlAllowOrigin;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (!isOptions) {
            log.debug(() -> BAR_BEGIN);
        }

        HttpServletResponse res = (HttpServletResponse) response;

        if (!"none".equals(accessControlAllowOrigin)) {
            res.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "100_000");

            res.setHeader("Access-Control-Allow-Headers",
                    "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version, Event, SurveyId, Cache-Control, Pragma, EXPIRES");
            res.setHeader("Access-Control-Expose-Headers",
                    "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Set-Cookie");
            res.setHeader("Access-Control-Allow-Credentials", "true");
        }

        chain.doFilter(request, response);

        /** log.debug("Response StatusCode: {}", () -> res.getStatus()); */

        /** log.debug(() -> BAR_END); */
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug(() -> "BootCorsFilter1 initialized");
    }

    @Override
    public void destroy() {
        log.debug(() -> "BootCorsFilter1 destroyed");
    }

}
