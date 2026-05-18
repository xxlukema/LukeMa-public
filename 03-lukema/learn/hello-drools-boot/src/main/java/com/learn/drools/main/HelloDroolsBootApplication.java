package com.learn.drools.main;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication(scanBasePackages = { "com.learn" })
@Log4j2
public class HelloDroolsBootApplication
    extends SpringBootServletInitializer
    implements CommandLineRunner {

    public static void main(String[] args) {

        log.info("======== Boot Starting {}... ========", () -> "HelloDroolsBootApplication");

        SpringApplication.run(HelloDroolsBootApplication.class, args);

        log.info("======== Boot Started {} ========", () -> "HelloDroolsBootApplication");
    }

    @Override
    public void run(String... args)
        throws Exception {
        log.info("run");
    }
}
