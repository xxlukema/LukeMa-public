package com.learn.repository;


import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


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
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // @formatter:off
        String token = Jwts.builder()
                               .setId(id)
                               .setIssuedAt(now)
                               .setNotBefore(now)
                               .setExpiration(exp)
                               .signWith(key)
                               .compact();
        // @formatter:on

        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
