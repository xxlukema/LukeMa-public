package com.learn.boot.config;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


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
@EnableJpaRepositories(basePackages = { "com.learn.dao", "com.learn.repository" })
@EntityScan(basePackages = { "com.learn.entity" })
@EnableTransactionManagement
// @ImportXml("classpath:com/company/data-access-config.xml") // XML with DataSource bean
public class BootJpaConfig {

    @Autowired
    private DataSource dataSource;

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
