package com.learn.boot.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;


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
@EnableJpaRepositories(basePackages = { "com.learn.repository", "com.learn.querydsl.repository" })
@EntityScan(basePackages = { "com.learn.entity" })
@EnableTransactionManagement
@DependsOn("hsqldbInitConfig")
@Log4j2
// @ImportXml("classpath:com/company/data-access-config.xml") // XML with DataSource bean
public class BootJpaConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void initBootJpaConfig() {
        log.info(() -> "Init BootJpaConfig");
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
