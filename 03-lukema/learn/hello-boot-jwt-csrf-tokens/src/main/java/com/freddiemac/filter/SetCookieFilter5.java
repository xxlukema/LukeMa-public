package com.freddiemac.filter;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

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
@Order(Ordered.HIGHEST_PRECEDENCE + 500)
public class SetCookieFilter5
    extends GenericFilterBean {

    private static final String BAR_BEGIN = "--------------------- SetCookieFilter5 begin ---------------------";
    private static final String BAR_END = "--------------------- SetCookieFilter5 end ---------------------";

    private static final String MyCookieName = "MyCookie-1";
    // private static final String SetCookie = "Set-Cookie";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        log.debug(() -> BAR_BEGIN);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        StringBuilder sb = new StringBuilder();
        Cookie[] cookies = req.getCookies();
        if (cookies == null || cookies.length == 0) {
            log.info(() -> "No cookie.");
        } else {
            // cookies are in cookie header.
            // Arrays.asList(cookies).stream().filter(item -> MyCookieName.equals(item.getName())).forEach(item -> {
            Arrays.asList(cookies).stream().forEach(item -> {
                log.info("cookie {}: {}", () -> item.getName(), () -> item.getValue());
                if (sb.length() == 0) {
                    sb.append(item.getValue());
                }
            });
        }

        int cookieValue = 0;
        if (sb.length() != 0) {
            try {
                cookieValue = Integer.parseInt(sb.toString()) + 1;
            } catch (Exception e) {
            }
        }

        /**
         * res.addHeader(SetCookie, MyCookieName + "=" + cookieValue);
         * 
         * Or idem
         * 
         */
        Cookie cookie = new Cookie(MyCookieName, String.valueOf(cookieValue));
        /**
         * The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's sub directories.
         * 
         * default: path of current page.
         */
        cookie.setPath("/");
        /**
         * cookie.setHttpOnly(true): The HttpOnly flag (optional) is included in the HTTP response header, the cookie cannot be
         * accessed through client side script (again if the browser supports this flag). As a result, even if a cross-site
         * scripting (XSS) flaw exists, and a user accidentally accesses a link that exploits this flaw, the browser
         * (primarily Internet Explorer) will not reveal the cookie to a third party.
         * 
         * default false: HttpOnly flag will not be included, and hence script can access the cookie.
         */
        cookie.setHttpOnly(true); // cookie.setHttpOnly(true): script cannot access the cookie.
        /**
         * cookie.setSecure(true): This cookie is visible to https only. 
         * cookie.setSecure(false): This cookie is visible to both http and https. 
         */
        cookie.setSecure(false);
        /**
         * positive: expire an integer specifying the maximum age of the cookie in seconds.
         * negative: means the cookie is not stored persistently and will be deleted when the Web browser exits.
         * 0: deletes the cookie.
         * 
         * default -1: indicating the cookie will persist until browser shutdown.
         */
        // cookie.setMaxAge((int) 30 * 60);
        cookie.setMaxAge(100);
        /**
         * IE 11 does not like a domain value in the cookie. Leave it empty.
         * 
         * cookie.setDomain("localhost"); --- IE 11 does not support cookie.setDomain using localhost. It require a dot ('.') in the
         * domain name.
         */
        cookie.setDomain("127.0.0.1");

        res.addCookie(cookie);

        chain.doFilter(request, response);

        log.debug("Response Status Code is: {}", () -> res.getStatus());

        log.debug(() -> BAR_END);
    }

    public void destroy() {
    }

}
