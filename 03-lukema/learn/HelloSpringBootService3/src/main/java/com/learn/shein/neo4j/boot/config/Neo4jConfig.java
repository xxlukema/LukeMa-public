package com.learn.shein.neo4j.boot.config;


import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@ComponentScan(basePackages = { "com.learn.shein.neo4j" })
@EnableAutoConfiguration
@Configuration
/**
 * Entity vs Repository:
 *   + Entity lives in between java object (java class) and relational table. It is about ORM mapping.
 *   + Repository helps to perform CRUD related operations with Entity.
 */
@EnableNeo4jRepositories(basePackages = { "com.learn.shein.neo4j.repository" })
@EntityScan(basePackages = { "com.learn.shein.neo4j.entity" })
public class Neo4jConfig {

  @Bean
  org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
    return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
  }

}
