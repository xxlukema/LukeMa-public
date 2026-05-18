package com.freddiemac.filter;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.freddiemac.exception.CookieExpiredException;
import com.freddiemac.jwt.util.JwtUtils;

import lombok.extern.log4j.Log4j2;


/**
 * Either use @Component + @Order(Ordered.HIGHEST_PRECEDENCE + 700) to specify order, 
 * or use OncePerRequestFilterRegistrationConfig7 to specify order.
 * 
 * OncePerRequestFilterRegistrationConfig7 is a more correct way, because of two reasons:
 * (1) It can be registered before UsernamePasswordAuthenticationFilter.class:
 *     httpSecurity.addFilterBefore(oncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
 * (2) It can register special url patterns
 *     registrationBean.addUrlPatterns("/jwt/*", "/rest/*");
 *
 */
@Log4j2
// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE + 700)
public class JwtRequestFilter7
    extends OncePerRequestFilter {

    private static final String BAR_BEGIN = "--------------------- JwtRequestFilter7 begin ---------------------";
    private static final String BAR_END = "--------------------- JwtRequestFilter7 end ---------------------";

    public static final String CookieName = "Authorization-Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        log.debug(() -> BAR_BEGIN);

        StringBuilder sb = new StringBuilder();

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

                        JwtUtils.authorizeUser(user.getUsername(), user.getPassword());
                        isValidJwtToken = true;

                        sb.append("isAuthenticated.").append(System.lineSeparator());
                    } catch (Exception e) {
                        log.error("Authentication failed", e);
                        sb.append("Authentication failed.").append(System.lineSeparator());
                    }
                }
            }
        }

        log.debug(() -> sb.toString().trim());

        if (foundJwtTokenCookie && !isValidJwtToken) {
            throw new ServletException(new CookieExpiredException("JWT Cookie expired."));
        }

        log.debug("{} {}", () -> req.getMethod(), () -> req.getRequestURI());

        filterChain.doFilter(request, response);

        log.debug("Response StatusCode: {}", () -> res.getStatus());

        log.debug(() -> BAR_END);
    }

}
