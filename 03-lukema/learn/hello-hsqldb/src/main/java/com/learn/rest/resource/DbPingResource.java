package com.learn.rest.resource;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.CurrentDatePojo;
import com.learn.service.DbPingService;


@RequestMapping("/spring/db")
@RestController
public class DbPingResource {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private DbPingService dbPingService;

    @Value("${jasypt.encryptor.algorithm}")
    private String jasyptEncryptAlgorithm;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "ping", produces = { MediaType.APPLICATION_JSON_VALUE })
    public CurrentDatePojo selectCurrentDate()
        throws Exception {
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
