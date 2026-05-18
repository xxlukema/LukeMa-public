package com.learn.rest.controller;


import java.security.Principal;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.boot.auth.jwt.JwtUtils;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.shein.psql.service.SpringSecurityUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/spring-security-method-security
 * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
 *
 * 1. Generate JWS Cookie:
 *
 *    curl -k -i -u "admin:admin" -c cookies.txt -X POST 'https://localhost:8443/jwt/login'
 *
 * 2. Use that cookie to access secured sites:
 *
 *    curl -k -i -b cookies.txt -X GET 'https://localhost:8443/jwt/ping'
 *
 */
@Log4j2
@RequestMapping("/jwt")
@RestController
@AllArgsConstructor
public class JwtSecurityController {

    private SpringSecurityUserService springSecurityUserService;

    /**
     * 1. Generate JWS Cookie:
     *
     *    curl -k -i -u "admin:admin" -c cookies.txt -X POST 'https://localhost:8443/jwt/login'
     *
     * 2. Use that cookie to access secured sites:
     *
     *    curl -k -i -b cookies.txt -X GET 'https://localhost:8443/jwt/ping'
     *
     */
    @PostMapping("/login") /** for test purpose only */
    public ResponseEntity<?> jwtlogin(Principal principal, Authentication authentication, HttpServletResponse response)
        throws Exception {

        log.info(() -> "Called. login()");

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
                log.info(() -> item.getAuthority());
            });
        }

        /**
        SpringSecurityUserEntity suser = this.springSecurityUserService.findByUsername("admin");
        
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> "ROLE_ADMIN");
        roles.add(() -> "ROLE_USER");
        User user = new User("admin", "admin", roles);
        */

        SpringSecurityUserEntity entity = this.springSecurityUserService.findByUsername("admin");
        String token = JwtUtils.generateToken(entity);

        Cookie cookie = new Cookie(JwtUtils.COOKIE_NAME, token);
        /** cookie.setDomain("127.0.0.1"); */
        cookie.setPath("/");
        cookie.setHttpOnly(true); // cookie.setHttpOnly(true): Javascript cannot access the cookie through document.cookie (to mitigate XSS attacks).
        cookie.setSecure(false); // (1) Chrome: If SameSite==None, then secure==true. (2) This cookie (false) is visible to both http and https.
        cookie.setMaxAge(JwtUtils.EXPIRE_IN_SESONDS);
        cookie.setAttribute("SameSite", SameSite.LAX.toString());

        response.addCookie(cookie);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(new JwtPojo("OK", token));
    }

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

            authentication.getAuthorities().forEach(item -> log.info(() -> item.getAuthority()));
        }

        /** throw new Exception("Text Throw"); */

        /** return "{\"status\":\"OK\"}\n"; */

        return ResponseEntity.status(HttpStatus.OK).body(new JwtPojo("OK", ""));
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class JwtPojo {

    private String status;
    private String token;

}
