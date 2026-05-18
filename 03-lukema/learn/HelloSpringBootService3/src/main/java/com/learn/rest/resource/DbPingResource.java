package com.learn.rest.resource;


import jakarta.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.exception.AppException;
import com.learn.pojo.CurrentDatePojo;
import com.learn.service.DbPingService;

import lombok.extern.log4j.Log4j2;


@RequestMapping("/spring/db")
@RestController
@Log4j2
public class DbPingResource {
    /**
     * For reference
     * private static final Logger log = LogManager.getLogger();
     */

    @Autowired
    private DbPingService dbPingService;

    @Value("${jasypt.encryptor.algorithm:none}")
    private String jasyptEncryptAlgorithm;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "ping", produces = { MediaType.APPLICATION_JSON })
    public CurrentDatePojo selectCurrentDate()
        throws AppException {
        log.debug(() -> "Enter...");

        try {
            return dbPingService.selectCurrentDate();
        } finally {
            log.debug(() -> "Leave.");
        }
    }

    @Autowired(required = false)
    public void println() {
        log.debug(() -> "######################## DbPingResource: This line will display on boot start up. ########################");
        log.debug("JasyptEncrypt Algorithm = {}", jasyptEncryptAlgorithm);
    }

}
