package com.learn.boot.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.learn" })
/**
 * Entity vs Repository:
 *   + Entity lives in between java object (java class) and relational table. It is about ORM mapping.
 *   + Repository helps to perform CRUD related operations with Entity.
 */
// @ImportXml("classpath:com/company/data-access-config.xml") // XML with DataSource bean
public class BootAppConfig {

}
