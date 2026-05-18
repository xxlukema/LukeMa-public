package com.learn.boot.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * @ConfigurationProperties("server")
 * @ConfigurationProperties(prefix = "currencies")
 * @PropertySource(value = "classpath:application-test.properties")
 * @PropertySources({
 *     @PropertySource("classpath:foo.properties"),
 *     @PropertySource("classpath:bar.properties")
 *  })
 * @ConfigurationProperties("my-propertes.yml")
 *  
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@PropertySource(value = "classpath:my-properties.yml", factory = YamlPropertySourceFactory.class)
public class PropertieSourcesConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer createPropertyConfigurer() {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setTrimValues(true);
        return propertyConfigurer;
    }

}
