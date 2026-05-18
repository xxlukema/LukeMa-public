package com.learn.filter;


import java.io.IOException;
import java.util.Arrays;

import javax.crypto.SecretKey;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learn.boot.auth.jwt.JwtUtils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/java-json-web-tokens-jjwt
 * https://github.com/jwtk/jjwt
 * https://www.youtube.com/watch?v=X80nJ5T7YpE&t=30s
 */
@Log4j2
public class JwtCsrfValidatorFilter
    extends OncePerRequestFilter {

    // @formatter:off
    final private String[] ignoreCsrfAntMatchers = {
            "/dynamic-builder-compress",
            "/dynamic-builder-general",
            "/dynamic-builder-specific",
            "/set-secrets"
        };
    // @formatter:on

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // NOTE: A real implementation should have a nonce cache so the token cannot be reused
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

        if (token != null) {
            log.info("received _csrf token: {}", token.getHeaderName());
        }

        /**
         * 1. only care if it's a POST
         * 2. ignore if the request path is in our list
         * 3. ignore if no this token
         */
        // @formatter:off
        if ("POST".equals(request.getMethod()) &&
                Arrays.binarySearch(ignoreCsrfAntMatchers, request.getServletPath()) < 0 &&
                token != null) {
                // @formatter:on

            /**
             * We need a signing key, so we'll create one just for this example. Usually
             * the key would be read from your application configuration instead.
             */
            SecretKey Secret_Key = JwtUtils.SECRET_KEY;

            try {
                // @formatter:off
                Jwts.parser()
                    .verifyWith(Secret_Key)
                    .build()
                    .parseSignedClaims(token.getToken());

                // @formatter:on
            } catch (JwtException e) {
                // most likely an ExpiredJwtException, but this will handle any
                request.setAttribute("exception", e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
                dispatcher.forward(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

}
