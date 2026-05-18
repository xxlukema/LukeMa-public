package com.learn.rest.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootApplication(scanBasePackages = { "com.learn" })
public class SpringBootDemoApplication {

    public static void main(String[] args) {

        log.info("Boot Starting {}...", () -> "SpringBootDemoApplication");

        SpringApplication.run(SpringBootDemoApplication.class, args);

        log.info("Boot Started {}.", () -> "SpringBootDemoApplication");
    }
}
