package com.learn.boot.main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.learn" })
public class BootTransactionPropagationApplication {

    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {

        LOG.info("Boot Starting...");

        SpringApplication.run(BootTransactionPropagationApplication.class, args);

        LOG.info("Boot Started.");
    }
}
