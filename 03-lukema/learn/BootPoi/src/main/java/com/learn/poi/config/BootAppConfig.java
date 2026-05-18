package com.learn.poi.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan(basePackages = { "com.learn" })
@EnableAutoConfiguration
@Configuration
public class BootAppConfig {

}
