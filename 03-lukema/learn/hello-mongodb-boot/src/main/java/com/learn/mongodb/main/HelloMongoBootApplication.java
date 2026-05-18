package com.learn.mongodb.main;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootApplication(scanBasePackages = { "com.learn.mongodb" })
public class HelloMongoBootApplication
    extends SpringBootServletInitializer
    implements CommandLineRunner {

    public static void main(String[] args) {

        log.info("==================================== Boot Starting {}... ====================================",
                () -> "HelloMongoBootApplication");

        SpringApplication.run(HelloMongoBootApplication.class, args);

        log.info("==================================== Boot Started {} ====================================", () -> "HelloMongoBootApplication");
    }

    @Override
    public void run(String... args)
        throws Exception {
        log.info("==================================== Boot Starting {}... ====================================", () -> "run");
    }
}
