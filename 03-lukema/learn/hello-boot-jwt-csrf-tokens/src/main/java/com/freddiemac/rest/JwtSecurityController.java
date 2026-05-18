package com.freddiemac.rest;


import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/spring-security-method-security
 * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
 * 
 * 1. Generate JWS Cookie:
 * 
 *    curl -u "admin:admin" -c cookies.txt -X POST 'http://localhost:8080/jwt/login'
 *    
 * 2. Use that cookie to access secured sites:
 * 
 *    curl -i -k -b cookies.txt -X GET 'http://localhost:8080/jwt/ping'
 * 
 */
@Log4j2
@RequestMapping("/jwt")
@RestController
public class JwtSecurityController {

    /**
     * To use Method level of @PreAuthorize("hasRole('ADMIN')"), a @Configuration file must define this:
     * @throws Exception 
     * @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
     */
    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ping(Principal principal, Authentication authentication)
        throws Exception {

        log.info(() -> "Called. ping()");

        if (principal == null) {
            log.info(() -> "principal is null");
        } else {
            log.info(() -> "principal.getName(): " + principal.getName());
        }

        if (authentication == null) {
            log.info(() -> "authentication is null.");
        } else {
            log.info(() -> "authentication.getName(): " + authentication.getName());

            authentication.getAuthorities().forEach(item -> {
                log.info(() -> item.getAuthority().toString());
            });
        }

        // throw new Exception("Text Throw");

        // return "{\"status\":\"OK\"}\n";

        return ResponseEntity.status(HttpStatus.OK).body(new JwtPojo("OK", ""));
    }

}
