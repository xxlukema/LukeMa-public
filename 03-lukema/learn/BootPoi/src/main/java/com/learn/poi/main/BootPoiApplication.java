package com.learn.poi.main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.learn" })
public class BootPoiApplication {

    private static final Logger LOG = LogManager.getLogger();
    
    public static void main(String[] args) {
        LOG.info("POI server starting...");

        SpringApplication.run(BootPoiApplication.class, args);
        
        LOG.info("POI server completed.");
    }
}
