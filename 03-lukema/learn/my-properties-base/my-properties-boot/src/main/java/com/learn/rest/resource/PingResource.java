package com.learn.rest.resource;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import jakarta.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/rest")
@RestController
public class PingResource {

    /**
     * curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/ping
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "ping", produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<String> ping() {
        log.debug(() -> "Enter.");

        Date date = new Date();

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostname = inetAddress.getCanonicalHostName();
            String ip = inetAddress.getHostAddress();

            log.debug("hostname: {}, ip: {}, datetime: {}", hostname, ip, date);

            return ResponseEntity.status(HttpStatus.OK).body(String.format("echo OK from %s %s at %s", hostname, ip, date));
        } catch (UnknownHostException e) {
            log.error("UnknownHostException", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("echo error: %s %s", e.getMessage() == null ? "No Message" : e.getMessage(), date));
        }

    }

    /**
     * curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/exit
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "exit", produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<String> exit() {
        log.debug(() -> "Enter.");

        Date date = new Date();

        try {
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Test `System.exit(-1)` at %s", date));
        } finally {
            // System.exit(-1);
        }

    }

}
