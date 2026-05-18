package com.learn.filter;


import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.learn.boot.auth.jwt.JwtUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
 */
@Log4j2
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 600)
// @WebFilter(filterName = "myBootSessionFilter", urlPatterns = { "/*" })
public class JwtAuthFilter6
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- JwtAuthFilter6 begin ---------------------";
    // private static final String BAR_END = "--------------------- JwtAuthFilter6 end ---------------------";

    public static final String COOKIE_NAME = JwtUtils.COOKIE_NAME;

    public static final String BEARER = "Bearer ";

    /**
     * JSON Web Tokens (JWTs)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        StringBuilder sb = new StringBuilder(BAR_BEGIN);
        sb.append(System.lineSeparator());

        boolean foundJwtTokenCookie = false;
        boolean isValidJwtToken = false;

        String jwtToken = req.getHeader("Authorization");
        if (jwtToken != null && jwtToken.indexOf(BEARER) == 0) {
            jwtToken = jwtToken.substring(BEARER.length());
        }

        if (jwtToken == null) {
            sb.append("No Authorization Bearer.");
        } else {
            log.debug("req.getServletPath() {}", req.getServletPath());

            sb.append("Found JWT cookie.").append(System.lineSeparator());

            foundJwtTokenCookie = true;

            try {
                User user = JwtUtils.parseToken(jwtToken);

                // Authentication authentication = ThirdPartyAuthenticationUtils.authorizeUser("admin", "admin");
                // SecurityContextHolder.getContext().setAuthentication(authentication);
                // sb.append("isAuthenticated: ").append(authentication.isAuthenticated()).append(System.lineSeparator());

                // 1. Check cookie timeout
                // 2. Check username
                // 3. Check user enabled
                // 4. Check roles
                // 5. If cookie is expiring in 5 minutes, extend cookie timeout
                // 6. check blacklist

                JwtUtils.authorizeUser(user);
                isValidJwtToken = true;

                sb.append("isAuthenticated.");
            } catch (Exception e) {
                log.error("Authentication failed", e);
                sb.append("Authentication failed.");
            }
        }

        /*
        Cookie[] allCookies = req.getCookies();
        
        if (allCookies == null || allCookies.length == 0) {
            sb.append("No cookies.");
        } else {
            // filter for jwt cookie
            Cookie cookie = Arrays.stream(allCookies).filter(x -> x.getName().equals(COOKIE_NAME)).findFirst().orElse(null);
        
            if (cookie != null) {
                log.debug("req.getServletPath() {}", req.getServletPath());
        
                sb.append("Found JWT cookie.").append(System.lineSeparator());
        
                foundJwtTokenCookie = true;
        
                String jwtToken = cookie.getValue();
        
                try {
                    User user = JwtUtils.parseToken(jwtToken);
        
                    // Authentication authentication = ThirdPartyAuthenticationUtils.authorizeUser("admin", "admin");
                    // SecurityContextHolder.getContext().setAuthentication(authentication);
                    // sb.append("isAuthenticated: ").append(authentication.isAuthenticated()).append(System.lineSeparator());
        
                    // 1. Check cookie timeout
                    // 2. Check username
                    // 3. Check user enabled
                    // 4. Check roles
                    // 5. If cookie is expiring in 5 minutes, extend cookie timeout
                    // 6. check blacklist
        
                    JwtUtils.authorizeUser(user);
                    isValidJwtToken = true;
        
                    sb.append("isAuthenticated.");
                } catch (Exception e) {
                    log.error("Authentication failed", e);
                    sb.append("Authentication failed.");
        
                    // Auth failed. Remove bad cookie.
                    cookie.setMaxAge(0);
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.addCookie(cookie);
                }
            }
        }
        */

        log.debug(() -> sb.toString());

        if (foundJwtTokenCookie && !isValidJwtToken) {
            // throw new ServletException(new CookieExpiredException("JWT Cookie expired."));
        }

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
