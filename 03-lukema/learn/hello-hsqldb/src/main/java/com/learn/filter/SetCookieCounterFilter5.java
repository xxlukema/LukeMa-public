package com.learn.filter;


import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.web.server.Cookie.SameSite;
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
@Order(Ordered.HIGHEST_PRECEDENCE + 500)
public class SetCookieCounterFilter5
    implements Filter {

    private static final AtomicInteger counter = new AtomicInteger();

    private static final String BAR_BEGIN = "--------------------- SetCookieCounterFilter5 begin ---------------------";
    // private static final String BAR_END = "--------------------- SetCookieCounterFilter5 end ---------------------";

    private static final String COOKIE_COUNTER_NAME = "MyCookie-counter";
    // private static final String Header_SetCookie = "Set-Cookie";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        boolean isOptions = "OPTIONS".equals(req.getMethod());
        if (isOptions) {
            chain.doFilter(request, response);
            return;
        }

        log.debug(() -> BAR_BEGIN);

        HttpServletResponse res = (HttpServletResponse) response;

        // req.path

        Cookie[] cookies = req.getCookies();

        AtomicBoolean isCookieFound = new AtomicBoolean();

        if (cookies == null || cookies.length == 0) {
            log.info(() -> "No cookie.");
        } else {
            log.debug("Cookies size: {}", cookies.length);

            // cookies are in cookie header.
            Arrays.asList(cookies).stream()
                    .filter(item -> COOKIE_COUNTER_NAME.equals(item.getName()))
                    .forEach(item -> {
                        log.debug("cookie: {}", item.getName());
                        isCookieFound.set(true);
                    });
        }

        if (isCookieFound.get()) {

            log.debug("Found cookie {}", () -> COOKIE_COUNTER_NAME);

            /**
             * To remove a cookie from a browser, we have to add a new one to the response with the same name, but with a maxAge value set to 0:
             */
            /*
            Cookie userNameCookieRemove = new Cookie(MyCookieName, "");
            userNameCookieRemove.setMaxAge(0);
            res.addCookie(userNameCookieRemove);
            */
        } else {
            /**
             * res.addHeader(SetCookie, MyCookieName + "=" + cookieValue);
             *
             * Or idem
             *
             */
            Cookie cookie = new Cookie(COOKIE_COUNTER_NAME, String.valueOf(counter.getAndIncrement()));
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
            cookie.setHttpOnly(true); // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
            /**
             * cookie.setSecure(true): This cookie (true) is visible to https only.
             * cookie.setSecure(false): This cookie (false) is visible to both http and https.
             * cookie.setSecure(false): (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
             */
            cookie.setSecure(false); // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
            /**
             * (Do not use) Expires=<date>. When a cookie passes its expiry date, it will no longer be sent with browser requests, and instead will be deleted.
             *                              The date value is a HTTP timestamp.
             */
            // cookie.setAttribute("SameSite", SameSite.LAX.toString());
            /**
             * When a cookie passes its expiry date, it will no longer be sent with browser requests, and instead will be deleted. The date value is a HTTP timestamp.
             *
             * `positive`: (permanent cookie) expire an integer specifying the maximum age of the cookie in seconds.
             * `negative` or `not set`: (session cookie) means the cookie is not stored persistently and will be deleted when the Web browser exits.
             * `0`: deletes the cookie.
             *
             * default -1: indicating the cookie will persist until browser shutdown.
             *
             * Cookie **without** an `Expires` or `Max-Age` attribute are treated as `session cookies`, which means they are removed once the browser is closed.
             * Setting a **positive** value on either Expires or `Max-Age` makes them `permanent cookies`, since they will exist until they hit their expiry date.
             */
            // cookie.setMaxAge((int) 30 * 60);
            cookie.setMaxAge(100);
            /**
             * IE 11 does not like a domain value in the cookie. Leave it empty.
             *
             * cookie.setDomain("localhost"); --- IE 11 does not support cookie.setDomain using localhost. It require a dot ('.') in the
             * domain name.
             */
            // cookie.setDomain("127.0.0.1");
            /**
             * `Strict` means the cookie is only sent for requests originating from the same URL as the current one.
             * `Lax`    means the cookie is not sent on cross-site requests, but will be sent if the user navigates to the origin site from an external site.
             * `None`   means the cookie will be sent on both `same-site` and `cross-site` requests, but can **ONLY** be used if the `Secure` attribute is also set.
             */
            cookie.setAttribute("SameSite", SameSite.LAX.toString());

            res.addCookie(cookie);
        }

        chain.doFilter(request, response);

        // log.debug("Response Status Code is: {}", () -> res.getStatus());

        // log.debug(() -> BAR_END);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
