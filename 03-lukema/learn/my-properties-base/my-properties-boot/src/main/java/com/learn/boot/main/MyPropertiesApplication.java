package com.learn.boot.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication(scanBasePackages = { "com.learn" })
/**
 * When you use @SpringBootApplication annotation in for example package
 * com.learn.boot.main
 * it will automatically make component scan like this:
 * @ComponentScan("com.learn.boot.main") 
 * So it will NOT scan packages like com.learn.boot.conf etc.. Thats why you have to declare your 
 * @SpringBootApplication in package one level prior to your normal packages like this: com.learn 
 * OR use scanBasePackages property, like this: 
 * @SpringBootApplication(scanBasePackages = { "com.learn" }) 
 * OR componentScan: 
 * @SpringBootApplication 
 * @ComponentScan("com.learn")
 * @ComponentScan(basePackages = { "com.learn" })
 * 
 * @SpringBootApplication annotation automatically provides the features of the following annotations:
 *   + @Configuration
 *   + @EnableAutoConfiguration
 *   + @ComponentScan
 *
 */
@Log4j2
public class MyPropertiesApplication
    extends SpringBootServletInitializer {

    public static void main(String[] args) {

        log.info("=============== {} Starting... ===============", () -> MyPropertiesApplication.class.getSimpleName());

        SpringApplication.run(MyPropertiesApplication.class, args);

        log.info("=============== {} Started. ===============", () -> MyPropertiesApplication.class.getSimpleName());
    }

}
