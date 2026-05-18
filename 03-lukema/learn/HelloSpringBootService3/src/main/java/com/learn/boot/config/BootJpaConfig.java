package com.learn.boot.config;


import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;


@Log4j2
@ServletComponentScan(basePackages = { "com.learn" })
@ComponentScan(basePackages = { "com.learn" })
@EnableAutoConfiguration
@Configuration
/**
 * Entity vs Repository:
 *   + Entity lives in between java object (java class) and relational table. It is about ORM mapping.
 *   + Repository helps to perform CRUD related operations with Entity.
 */
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = { "com.learn.repository" })
@EntityScan(basePackages = { "com.learn.entity" })
@EnableTransactionManagement
// @ImportXml("classpath:com/company/data-access-config.xml") // XML with DataSource bean
public class BootJpaConfig {

    // @Value("${jasypt.encryptor.algorithm:none}")
    private final String jasyptEncryptAlgorithm;

    private final DataSource dataSource;

    /**
     * static inject is not supported. Therefore, a work around have to be used:
     * Trick 1. (Preferred) Implicit constructor injection + assign to static memeber
     * Trick 2. protected setter injection + assign to static memeber
     */
    private static String name;

    /**
     * Trick 1. (Preferred) Implicit constructor injection + assign to static memeber
     */
    protected BootJpaConfig(
            @Value("${my.property.name:none}") String name,
            @Value("${jasypt.encryptor.algorithm:none}") String jasyptEncryptAlgorithm,
            @Autowired DataSource dataSource) {
        BootJpaConfig.name = name;
        this.jasyptEncryptAlgorithm = jasyptEncryptAlgorithm;
        this.dataSource = dataSource;

    }

    private

    /**
     * Trick 2. protected setter injection + assign to static memeber
     */
    /*
    @Value("${my.property.name:none}") void setName(String name) {
        BootJpaConfig.name = name;
    }
    */

    @Autowired void initBootJpaConfig(ApplicationContext applicationContext) {
        log.info(() -> "Init BootJpaConfig");

        log.info("jasyptEncryptAlgorithm: {}", () -> jasyptEncryptAlgorithm);

        Objects.requireNonNull(name, "static injection is not supported. Please use a work around.");

        log.info("static NAME: {}", () -> name);

        var env = applicationContext.getEnvironment();
        log.debug("env: {}", () -> env);
        log.debug("env: my.property.name={}", () -> env.getProperty("my.property.name"));
    }

    @Bean
    @Scope("singleton")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /*
    @Bean
    public SessionFactory getSessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
    */

    @Bean
    public EntityManager entityManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

}
