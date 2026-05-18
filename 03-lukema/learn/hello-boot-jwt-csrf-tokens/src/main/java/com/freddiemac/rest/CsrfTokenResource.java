package com.freddiemac.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.rest.element.StatusPojo;

import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/csrf")
@Log4j2
public class CsrfTokenResource {

    /**
     * curl -i -k -L -X GET 'http://localhost:8080/csrf/get'
     */
    @GetMapping("/get")
    public ResponseEntity<?> pingGet() {

        log.info("Called. pingGet()");

        // return ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"OK!!!!\"}");
        return ResponseEntity.status(HttpStatus.OK).body(new StatusPojo("OK"));
    }

    /**
     * curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK" -u "admin:admin"
     */
    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> pingPost() {

        log.info("Called. pingPost()");

        // return ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"OK!!!!\"}");
        return ResponseEntity.status(HttpStatus.OK).body(new StatusPojo("OK"));
    }
    
    /**
     * curl -i -k -X POST 'http://localhost:8080/csrf/bypass' -H "X-CSRF-TOKEN: O2K" -u "admin:admin"
     */
    @PostMapping("/bypass")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bypassPost() {
        
        log.info("Called. pingPost()");
        
        // return ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"OK!!!!\"}");
        return ResponseEntity.status(HttpStatus.OK).body(new StatusPojo("OK"));
    }

}
