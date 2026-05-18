package com.learn.serverless.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootApplication(scanBasePackages = { "com.learn.serverless" })
public class HelloServerlessApplication {

    public static void main(String[] args) {

        log.info("Starting server...");

        SpringApplication.run(HelloServerlessApplication.class, args);

        log.info("Server started.");
    }

}
