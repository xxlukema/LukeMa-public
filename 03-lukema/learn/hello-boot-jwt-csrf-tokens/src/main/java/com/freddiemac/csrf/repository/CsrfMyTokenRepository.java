package com.freddiemac.csrf.repository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/java-json-web-tokens-jjwt
 * https://github.com/jwtk/jjwt
 * 
 * 1. CSRF token can be passed by either as request header or a request parameter
 * 
 *    curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK"
 *    Or
 *    curl -i -k -X POST 'http://localhost:8080/csrf/post?_csrf=OK'
 *    
 * 2. The token should be generated based on something from request. For example, sessionId or urlPath.
 * 
 */
@Log4j2
@Repository
public class CsrfMyTokenRepository
    implements CsrfTokenRepository {

    /**
     * 1. Standard header and parameters.
     * 2. CSRF_HEADER_NAME should be added to res.setHeader("Access-Control-Allow-Headers", "X-CSRF-TOKEN, ...");
     */
    public static final String CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    public static final String CSRF_PARAM_NAME = "_csrf";

    public static final String CSRF_DEFAULT_SECRET = "OK";

    /**
     * Generate a token based on something from request. For example, sessionId or urlPath.
     * 
     * @return CsrfToken. Cannot be null.
     */
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {

        log.debug(() -> "------------- generateToken");

        // Should use randomized _csrf token:
        // String token = UUID.randomUUID().toString().replace("-", "");
        String randomizedToken = CSRF_DEFAULT_SECRET;

        /**
         * Decode randomized token to a value  
         */
        // randomizedToken = this.encodeToBase64url(randomizedToken);

        DefaultCsrfToken defaultCsrfToken = new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAM_NAME, randomizedToken);
        // this.saveToken(defaultCsrfToken, request, null);
        return defaultCsrfToken;
    }

    /**
     * Save token into store.
     */
    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        // Save the token into store. 
    }

    /**
     * Load token from request. For example, sessionId or urlPath, or any specialized parameter or header.
     */
    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        log.debug(() -> "------------- loadToken");

        String randomizedToken = request.getHeader(CSRF_HEADER_NAME);
        if (randomizedToken == null) {
            randomizedToken = request.getParameter(CSRF_PARAM_NAME);
        }

        String msg = randomizedToken;

        log.debug("randomizedToken: {}", () -> msg);

        if (randomizedToken == null) {
            return null;
        } else {

            /**
             * Decode randomized token to a value  
             */
            // String decodedToken = this.decodeToFixedBase64url(randomizedToken);
            String decodedToken = randomizedToken;
            if (!CSRF_DEFAULT_SECRET.equals(decodedToken)) {
                decodedToken = CSRF_DEFAULT_SECRET;
            }

            DefaultCsrfToken defaultCsrfToken = new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAM_NAME, decodedToken);
            // this.saveToken(defaultCsrfToken, request, null);
            return defaultCsrfToken;
        }
    }

}
