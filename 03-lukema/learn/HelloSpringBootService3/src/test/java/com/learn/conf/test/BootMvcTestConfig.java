package com.learn.conf.test;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * The following line will cause exception of: No qualifying bean of type 'javax.sql.DataSource' available
 * @ComponentScan({ "com.learn" })
 */
@Configuration
@EnableWebMvc
public class BootMvcTestConfig {

    /*
    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
    */

}
