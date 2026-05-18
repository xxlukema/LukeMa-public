package com.learn.rest.filter;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
 * 
 * https://www.baeldung.com/spring-security-session
 * 
 * Next, we’ll discuss how to secure our session cookie.
 *
 * We can use the httpOnly and secure flags to secure our session cookie:
 * 
 *     httpOnly: if true then browser script won't be able to access the cookie
 *     secure: if true then the cookie will be sent only over HTTPS connection
 * 
 * We can set those flags for our session cookie in the web.xml:
 * 
 * If we’re using Spring Boot, we can set these flags in our application.properties:
 *     
 * server.servlet.session.cookie.http-only=true
 * server.servlet.session.cookie.secure=true
 *
 * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
 */
@Log4j2
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
// @WebFilter(filterName = "myBootSessionFilter", urlPatterns = { "/*" })
public class BootAuthFilter
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- BootAuthFilter begin ---------------------";
    private static final String BAR_END = "--------------------- BootAuthFilter end ---------------------";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        StringBuilder sb = new StringBuilder(BAR_BEGIN);
        sb.append(System.lineSeparator());

        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] allCookies = req.getCookies();
        if (allCookies == null || allCookies.length == 0) {
            sb.append("No cookies.").append(System.lineSeparator());
        } else {
            Cookie session = Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID")).findFirst().orElse(null);

            if (session != null) {
                /*
                session.setHttpOnly(true);
                session.setSecure(true);
                res.addCookie(session);
                 */

                sb.append("JSESSIONID domain=" + session.getDomain() + ", path=" + session.getPath() + ", name=" + session.getName() + ", value=" + session.getValue()
                        + ", maxAge=" + session.getMaxAge()).append(System.lineSeparator());
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            sb.append("Authentication is null.").append(System.lineSeparator());
        } else {
            Object principal = authentication.getPrincipal();

            if (principal == null) {
                sb.append("principal is null.").append(System.lineSeparator());
            } else {
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    String username = userDetails.getUsername();
                    sb.append("username=" + username).append(System.lineSeparator());

                    userDetails.getAuthorities().forEach(item -> {
                        sb.append("Role: ").append(item.toString()).append(System.lineSeparator());
                    });
                } else {
                    String username = principal.toString();
                    sb.append("username=" + username).append(System.lineSeparator());
                }
            }
        }

        log.debug(() -> sb.toString());

        chain.doFilter(request, response);

        log.debug(() -> BAR_END);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
