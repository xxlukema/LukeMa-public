package com.learn.rest.resource;


import java.security.Principal;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.Book;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/spring-security-method-security
 *
 * (1) Generate a cookie file using GET to a permitted action:
 *     curl -k -i --user admin:admin -c cookies.txt -X GET "https://localhost:8443/rest/ping"
 *     Or Generate a cookie file using POST to a permitted action:
 *     curl -k -i -X POST -d username=admin -d password=admin -c cookies.txt https://localhost:8443/login
 *
 * (2) Use the generated cookie to call secured action:
 *     curl -k -i -b cookies.txt -X GET "https://localhost:8443/spring/security/ping"
 *
 * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
 */
@Log4j2
@RequestMapping("/spring/security")
@RestController
@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
public class SpringSecurityController {

    /**
     * To use Method level of @PreAuthorize("hasRole('ADMIN')"), a @Configuration file must define this:
     * @throws Exception
     * @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
     */
    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN')")
    public String ping(Principal principal, Authentication authentication)
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

        return "{\"status\":\"OK\"}\n";
    }

    @PostMapping("/ping")
    @PreAuthorize("hasRole('ADMIN')")
    public String ping2(Principal principal, Authentication authentication)
        throws Exception {

        log.info(() -> "Called. ping()");

        String response = String.format("{\"status\":\"{}\"}\n", "OK");

        return response;
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "featuredpost", produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> featuredPost(@RequestBody String payload) {
        log.debug("Enter. payload: {}", payload);

        Book book = new Book();
        book.setId(1);
        book.setName("Luke's Text Book");

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
