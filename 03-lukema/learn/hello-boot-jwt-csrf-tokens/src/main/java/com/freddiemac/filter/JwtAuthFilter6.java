package com.freddiemac.filter;


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
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.User;

import com.freddiemac.exception.CookieExpiredException;
import com.freddiemac.jwt.util.JwtUtils;

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

// Comment out @Component to disable this filter, because the correct filter should be
// OncePerRequestJwtFilter7 for JWT user authentication. By using OncePerRequestJwtFilter7,
// It is guaranteed user is authenticated once only:
// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE + 600)

// @WebFilter(filterName = "myBootSessionFilter", urlPatterns = { "/*" })

@Log4j2
public class JwtAuthFilter6
    implements Filter {

    private static final String BAR_BEGIN = "--------------------- JwtAuthFilter6 begin ---------------------";
    private static final String BAR_END = "--------------------- JwtAuthFilter6 end ---------------------";

    public static final String CookieName = "Authorization-Bearer";

    /**
     * JSON Web Tokens (JWTs)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        log.debug(() -> BAR_BEGIN);

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] allCookies = req.getCookies();

        boolean foundJwtTokenCookie = false;
        boolean isValidJwtToken = false;
        if (allCookies == null || allCookies.length == 0) {
            sb.append("No cookies.").append(System.lineSeparator());
        } else {
            Cookie cookie = Arrays.stream(allCookies).filter(x -> x.getName().equals(CookieName)).findFirst().orElse(null);

            if (cookie != null) {

                sb.append("Found JWT cookie.").append(System.lineSeparator());

                if (CookieName.equals(cookie.getName())) {

                    foundJwtTokenCookie = true;

                    String jwtToken = cookie.getValue();

                    try {
                        User user = JwtUtils.parseToken(jwtToken);

                        // Authentication authentication = ThirdPartyAuthenticationUtils.authorizeUser("admin", "admin");
                        // SecurityContextHolder.getContext().setAuthentication(authentication);
                        // sb.append("isAuthenticated: ").append(authentication.isAuthenticated()).append(System.lineSeparator());

                        JwtUtils.authorizeUser(user.getPassword(), user.getPassword());
                        isValidJwtToken = true;

                        sb.append("isAuthenticated.").append(System.lineSeparator());
                    } catch (Exception e) {
                        log.error("Authentication failed", e);
                        sb.append("Authentication failed.").append(System.lineSeparator());
                    }
                }
            }
        }

        log.debug(() -> sb.toString());

        if (foundJwtTokenCookie && !isValidJwtToken) {
            throw new ServletException(new CookieExpiredException("JWT Cookie expired."));
        }

        chain.doFilter(request, response);

        log.debug("Response StatusCode: {}", () -> res.getStatus());

        log.debug(() -> BAR_END);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
