package com.freddiemac.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.freddiemac.csrf.repository.CsrfMyTokenRepository;

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
@Order(Ordered.LOWEST_PRECEDENCE - 990)
public class CsrfDebugFilter
    extends GenericFilterBean {

    private static final String BAR_BEGIN = "--------------------- CsrfDebugFilter begin ---------------------";
    private static final String BAR_END = "--------------------- CsrfDebugFilter end ---------------------";

    @Autowired
    CsrfMyTokenRepository csrfMyTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        log.debug(() -> BAR_BEGIN);

        /*
         * Start: Debug X-CSRF-TOKEN
         * 
         * CSRF token can be passed by either as request header or a request parameter:
         * 
         *    curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK"
         *    Or
         *    curl -i -k -X POST 'http://localhost:8080/csrf/post?_csrf=OK'
         *    
         */
        CsrfToken csrfToken = this.csrfMyTokenRepository.loadToken((HttpServletRequest) request);
        boolean missingToken = (csrfToken == null);
        if (missingToken) {
            csrfToken = this.csrfMyTokenRepository.generateToken((HttpServletRequest) request);
            this.csrfMyTokenRepository.saveToken(csrfToken, (HttpServletRequest) request, (HttpServletResponse) response);
        }
        request.setAttribute(CsrfToken.class.getName(), csrfToken);
        request.setAttribute(csrfToken.getParameterName(), csrfToken);
        String actualToken = ((HttpServletRequest) request).getHeader(csrfToken.getHeaderName());
        if (actualToken == null) {
            actualToken = request.getParameter(csrfToken.getParameterName());
        }

        log.info("csrfToken.getToken(): {} - actualToken {}", csrfToken.getToken(), actualToken);

        if (!csrfToken.getToken().equals(actualToken)) {
            log.debug(() -> "Invalid CSRF token found for ...........");
        }
        // End: Debug X-CSRF-TOKEN

        chain.doFilter(request, response);

        log.debug(() -> BAR_END);
    }

    public void destroy() {
    }

}
