package com.learn.boot.main;


import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.server.ServerAcl.AclFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.learn.boot.config.HsqldbInitConfig;
import com.learn.boot.config.MyProperties;

import lombok.extern.log4j.Log4j2;


// @EnableEurekaClient
@SpringBootApplication(scanBasePackages = { "com.learn" })
// @SpringBootApplication(scanBasePackages = { "com.learn" }, exclude = { SecurityAutoConfiguration.class })
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
public class HelloHsqldbApplication
    extends SpringBootServletInitializer
    implements CommandLineRunner {

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HelloHsqldbApplication.class);
    }

    public static void main(String[] args)
        throws ClassNotFoundException, SQLException, IOException, AclFormatException {

        log.info("==================================== Boot Starting {}... ====================================",
                () -> HelloHsqldbApplication.class.getSimpleName());

        HsqldbInitConfig.startDBServer();

        SpringApplication.run(HelloHsqldbApplication.class, args);

        log.info("====================================== Boot Started {} ======================================",
                () -> HelloHsqldbApplication.class.getSimpleName());
    }

    @Override
    public void run(String... args)
        throws Exception {

        log.info("DataSource = {}", () -> dataSource);

        log.info("myProperties = {}", () -> myProperties);
    }

}
