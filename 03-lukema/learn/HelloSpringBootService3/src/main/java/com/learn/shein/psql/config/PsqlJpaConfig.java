package com.learn.shein.psql.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableAutoConfiguration
@Configuration
/**
 * Entity vs Repository:
 *   + Entity lives in between java object (java class) and relational table. It is about ORM mapping.
 *   + Repository helps to perform CRUD related operations with Entity.
 */
@EnableJpaRepositories(basePackages = { "com.learn.shein.psql.repository" })
@EntityScan(basePackages = { "com.learn.shein.psql.entity" })
public class PsqlJpaConfig {

}
