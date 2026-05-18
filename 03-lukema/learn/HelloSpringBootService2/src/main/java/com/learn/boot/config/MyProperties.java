package com.learn.boot.config;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @Configuration - This annotation makes this bean injectable.
 * 
 * Validations donot work?
 * 
 */
@Configuration
@PropertySource("classpath:my.properties")
@ConfigurationProperties(prefix = "my.properties")
@Getter
@Setter
@ToString
public class MyProperties {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * From my.properties
     */
    @NotBlank
    @Size(min = 1, max = 40)
    // @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    private String name;

    /**
     * From my.properties
     */
    @Min(200)
    @Max(300)
    private Integer age;

    /**
     * From application.properties
     */
    @Value("${spring.datasource.username}")
    private String jasyptUsername;

    /**
     * From application.properties
     */
    @Value("${my.property.age}")
    private String youngerAge;

    @Autowired
    public void displayJaspt() {
        LOG.info("jasypt username = '{}'", jasyptUsername);
    }

}
