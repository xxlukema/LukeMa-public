package com.learn.filter;


import java.io.IOException;
import java.util.Arrays;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
 *
 * https://www.baeldung.com/spring-security-session
 *
 * Next, we will discuss how to secure our session cookie.
 *
 * We can use the httpOnly and secure flags to secure our session cookie:
 *
 *     httpOnly: if true then browser script won't be able to access the cookie
 *     secure: if true then the cookie will be sent only over HTTPS connection
 *
 * We can set those flags for our session cookie in the web.xml:
 *
 * If we are using Spring Boot, we can set these flags in our application.properties:
 *
 * server.servlet.session.cookie.http-only=true
 * server.servlet.session.cookie.secure=true
 *
 * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
 *
 * The GenericFilterBean is a simple jakarta.servlet.Filter implementation implementation that is Spring aware.
 * public class CustomFilter extends GenericFilterBean ...
 *
 */
@Log4j2
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
// @WebFilter(filterName = "myBootSessionFilter", urlPatterns = { "/*" })
public class BootAuthFilter9
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- BootAuthFilter9 begin ---------------------";
    // private static final String BAR_END = "--------------------- BootAuthFilter9 end ---------------------";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        StringBuilder sb = new StringBuilder(BAR_BEGIN).append(System.lineSeparator());

        HttpServletResponse res = (HttpServletResponse) response;

        String requestStr = String.format("[%s] %s", req.getMethod(), req.getRequestURL());
        sb.append(requestStr).append(System.lineSeparator());

        Cookie[] allCookies = req.getCookies();
        if (allCookies == null || allCookies.length == 0) {
            sb.append("No cookies.").append(System.lineSeparator());
        } else {
            Cookie sessionCookie = Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID")).findFirst().orElse(null);

            if (sessionCookie != null) {
                /**
                 * Filters cannot change HttpServletResponse cookies.
                 */
                sessionCookie.setHttpOnly(true); // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
                sessionCookie.setSecure(false); // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
                sessionCookie.setAttribute("SameSite", SameSite.LAX.toString());
                res.addCookie(sessionCookie);

                sb.append("JSESSIONID domain=").append(sessionCookie.getDomain())
                        .append(", path=").append(sessionCookie.getPath())
                        .append(", name=").append(sessionCookie.getName())
                        .append(", value=").append(sessionCookie.getValue())
                        .append(", maxAge=").append(sessionCookie.getMaxAge()).append(System.lineSeparator());
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            sb.append("Authentication is null.").append(System.lineSeparator());
        } else {
            sb.append("authentication class: ").append(authentication.getClass().getCanonicalName()).append(System.lineSeparator());

            if (authentication.isAuthenticated()) {
                sb.append("isAuthenticated: true").append(System.lineSeparator());
                sb.append("authentication.name: ").append(authentication.getName()).append(System.lineSeparator());
            } else {
                sb.append("isAuthenticated: false").append(System.lineSeparator());
            }

            /**
             * Print Roles:
             */
            if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
                usernamePasswordAuthenticationToken.getAuthorities().forEach(item -> {
                    sb.append("Role: ").append(item.getAuthority()).append(System.lineSeparator());
                });
            } else {
                Object principal = authentication.getPrincipal();

                if (principal == null) {
                    sb.append("principal is null.").append(System.lineSeparator());
                } else {
                    sb.append("principal class: ").append(principal.getClass().getCanonicalName()).append(System.lineSeparator());

                    switch (principal) {
                        case UserDetails userDetails -> {
                            String username = userDetails.getUsername();
                            sb.append("username=").append(username).append(System.lineSeparator());

                            userDetails.getAuthorities().forEach(item -> {
                                sb.append("Role: ").append(item.getAuthority()).append(System.lineSeparator());
                            });
                        }
                        case AnonymousAuthenticationToken anonymousAuthenticationToken -> {
                            String username = anonymousAuthenticationToken.getName();
                            sb.append("anonymous username=").append(username).append(System.lineSeparator());

                            anonymousAuthenticationToken.getAuthorities().forEach(item -> {
                                sb.append("anonymous Role: ").append(item.toString()).append(System.lineSeparator());
                            });
                        }
                        default -> {
                            String username = principal.toString();
                            sb.append("unknown username=").append(username).append(System.lineSeparator());
                        }
                    }
                }
            }
        }

        int index = sb.lastIndexOf(System.lineSeparator());
        if (index > -1) {
            sb.delete(index, sb.length());
        }

        log.debug(sb.toString());

        chain.doFilter(request, response);

        // log.debug(() -> BAR_END);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
