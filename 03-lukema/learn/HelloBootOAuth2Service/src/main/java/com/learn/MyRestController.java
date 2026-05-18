package com.learn;


import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class MyRestController {

    @GetMapping("/")
    public String hello() {
        log.info(() -> "Here.");
        return "Hello World!";
    }

    /**
     * curl --cookie "JSESSIONID=FCFDAC8E5D3A09E766250A916D0864A0" -X GET 'http://localhost:8080/ping'
     */
    @GetMapping("/ping")
    public String restricted() {
        log.info(() -> "Here.");
        return "Restricted!";
    }

    /**
     * curl --cookie "JSESSIONID=FCFDAC8E5D3A09E766250A916D0864A0" -X GET 'http://localhost:8080/user'
     */
    @GetMapping("/user")
    public Principal user(Principal principal) {
        log.info(() -> "Here.");
        return principal;
    }

}
