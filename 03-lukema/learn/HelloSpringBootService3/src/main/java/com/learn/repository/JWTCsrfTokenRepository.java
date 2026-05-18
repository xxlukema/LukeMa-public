package com.learn.repository;


import java.util.Date;
import java.util.UUID;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import com.learn.boot.auth.jwt.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * https://www.baeldung.com/java-json-web-tokens-jjwt
 * https://github.com/jwtk/jjwt
 */
public class JWTCsrfTokenRepository
    implements CsrfTokenRepository {

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {

        String id = UUID.randomUUID().toString().replace("-", "");

        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (1000 * 30)); // 30 seconds

        /**
         * We need a signing key, so we'll create one just for this example. Usually
         * the key would be read from your application configuration instead.
         */
        Claims claims = Jwts.claims().build();

        // @formatter:off
        String token = Jwts.builder()
                           .claims()
                           .add(claims)
                           .and()
                           .id(id)
                           .issuedAt(now)
                           .notBefore(now)
                           .expiration(exp)
                           .signWith(JwtUtils.SECRET_KEY)
                           .compact();
        // @formatter:on

        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return null;
    }

}
