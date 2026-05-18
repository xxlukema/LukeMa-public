package com.learn.boot.config;


import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@ServletComponentScan(basePackages = { "com.learn" })
@ComponentScan(basePackages = { "com.learn" })
public class WebAppConfig {

}
