package com.freddiemac.rest;


import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.auth.service.SpringSecurityUserDetailsService;
import com.freddiemac.filter.JwtAuthFilter6;
import com.freddiemac.jwt.util.JwtUtils;

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
 *    curl -c cookies.txt -X POST 'http://localhost:8080/jwtlogin' -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'
 *    # curl -u "admin:admin" -c cookies.txt -X POST 'http://localhost:8080/jwtlogin'
 *    
 * 2. Use that cookie to access secured sites:
 * 
 *    curl -i -k -b cookies.txt -X GET 'http://localhost:8080/jwt/ping'
 * 
 */
@Log4j2
@RestController
public class JwtLoginController {

    @Autowired
    SpringSecurityUserDetailsService springSecurityUserDetailsService;

    /**
     * 1. Generate JWS Cookie:
     * 
     *    curl -c cookies.txt -X POST 'http://localhost:8080/jwtlogin' -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'
     *    # curl -u "admin:admin" -c cookies.txt -X POST 'http://localhost:8080/jwtlogin'
     *    
     * 2. Use that cookie to access secured sites:
     * 
     *    curl -i -k -b cookies.txt -X GET 'http://localhost:8080/jwt/ping'
     * 
     */
    @PostMapping("/jwtlogin")
    // public ResponseEntity<?> jwtlogin(Principal principal, Authentication authentication, HttpServletResponse response)
    public ResponseEntity<?> jwtlogin(@RequestBody UserCredentials userCredentials, HttpServletResponse response)
        throws Exception {

        log.info(() -> "Called. /jwtlogin()");

        if (userCredentials == null) {
            log.info(() -> "userCredentials is null");
        } else {
            log.info(() -> "userCredentials.getUsername(): " + userCredentials.getUsername());
        }

        UserDetails userDetails = this.springSecurityUserDetailsService.loadUserByUsername(userCredentials.getUsername());

        String jwtToken = null;
        if (userDetails == null) {
            log.info(() -> "authentication is null.");
            /**
             * echo not found | base64
             * Output: bm90IGZvdW5kCg==
             */
            jwtToken = "bm90IGZvdW5kCg==";

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtPojo("FAIL", jwtToken));
        } else {
            log.info(() -> "userDetails.getUsername(): " + userDetails.getUsername());

            Collection<GrantedAuthority> roles = new ArrayList<>();
            userDetails.getAuthorities().forEach(item -> {
                log.info(() -> item.getAuthority().toString());
                roles.add(() -> item.getAuthority().toString());
            });

            // roles.add(() -> "ROLE_ADMIN");
            // roles.add(() -> "ROLE_USER");

            User user = new User(userDetails.getUsername(), userDetails.getPassword(), roles);
            jwtToken = JwtUtils.generateToken(user);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + jwtToken);

        response.addCookie(new Cookie(JwtAuthFilter6.CookieName, jwtToken));

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(new JwtPojo("OK", jwtToken));
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class JwtPojo {

    private String status;
    private String token;

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class UserCredentials {
    private String username;
    private String password;
}
