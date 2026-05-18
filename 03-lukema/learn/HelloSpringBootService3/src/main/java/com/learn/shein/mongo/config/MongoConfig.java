package com.learn.shein.mongo.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@ComponentScan(basePackages = { "com.learn.shein.mongo" })
@EnableAutoConfiguration
@Configuration
/**
 * Entity vs Repository:
 *   + Entity lives in between java object (java class) and relational table. It is about ORM mapping.
 *   + Repository helps to perform CRUD related operations with Entity.
 */
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.learn.shein.mongo.repository")
@EntityScan(basePackages = { "com.learn.shein.mongo.model" })
public class MongoConfig {

}
