package com.learn.rest.resource;


import jakarta.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/rest")
@RestController
public class ContentSecurityPolicyReport {

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "csp-report", produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> cspReport(@RequestBody String payload) {
        log.debug("Enter. payload: {}", () -> payload);

        String response = String.format("{\"status\":\"{}\"}", "OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
