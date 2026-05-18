package com.learn.mongodb.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@ComponentScan(basePackages = { "com.learn.mongodb" })
@EnableAutoConfiguration
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.learn.mongodb.repository")
@EntityScan(basePackages = { "com.learn.mongodb.model" })
public class MongoConfig {

}
