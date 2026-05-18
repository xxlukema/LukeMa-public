package com.learn.filter;


import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


/**
 * FilterRegistrationConfig MUST have a very large number!!! Otherwise, authentication will not work.
 *
 * @Order(Ordered.LOWEST_PRECEDENCE)
 * public class FilterRegistrationConfig
 *
 * This filter is not a @Component. It is registered as a filter in FilterRegistrationConfig.
 * This is to show:
 *    (1) How to register an OncePerRequestFilter as a filter with FilterRegistrationBean.
 *    (2) How to set @Order(-100)
 *
 * FilterRegistrationConfig:
 * filterRegistrationBean.setFilter(myOncePerRequestFilter);
 * filterRegistrationBean.addUrlPatterns("/spring/*", "/rest/*");
 * filterRegistrationBean.setOrder(-100);
 * @Order(-100)
 *
 */
@Log4j2
public class OncePerRequestMustHaveLowestPrecedenceFilter8
    extends OncePerRequestFilter {

    private static final String BAR_BEGIN = "--------------------- OncePerRequestMustHaveLowestPrecedenceFilter8 begin ---------------------";
    // private static final String BAR_END = "--------------------- OncePerRequestMustHaveLowestPrecedenceFilter8 end ---------------------";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        log.debug(() -> BAR_BEGIN);

        // HttpServletResponse res = (HttpServletResponse) response;

        log.debug("{} {}", () -> req.getMethod(), () -> req.getRequestURL());

        chain.doFilter(request, response);

        // log.debug("Response StatusCode: {}", () -> res.getStatus());

        // log.debug(() -> BAR_END);
    }

}
