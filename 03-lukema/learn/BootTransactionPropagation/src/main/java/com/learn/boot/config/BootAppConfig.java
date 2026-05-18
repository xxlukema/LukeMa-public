package com.learn.boot.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = { "com.learn.boot" })
// @PropertySource("persistence-student.properties")
@EntityScan(basePackages = { "com.learn.boot.jpa.entity" })
@EnableJpaRepositories(basePackages = { "com.learn.boot.jpa.dao" })
/**
 * <tx:annotation-driven/> is same as @EnableTransactionManagement + @Configuration
 */
@EnableTransactionManagement
public class BootAppConfig {

    // @Autowired
    // private DataSource dataSource;
    
    /**
     * This bean factory is the same as properties defined in application.properties:
     *  
     * spring.datasource.driverClassName=org.h2.Driver
     * spring.datasource.url=jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1
     * 
     */
    /*
    @Bean
    @Primary
    public DataSource dataSource() {
        // @formatter:off
        return new EmbeddedDatabaseBuilder().generateUniqueName(true)
                                            .setType(EmbeddedDatabaseType.H2)
                                            .setScriptEncoding("UTF-8")
                                            .ignoreFailedDrops(true)
                                            // .addScript("schema.sql")
                                            // .addScripts("user_data.sql", "country_data.sql")
                                      .build();
        // @formatter:on
    }
    */
    

}
