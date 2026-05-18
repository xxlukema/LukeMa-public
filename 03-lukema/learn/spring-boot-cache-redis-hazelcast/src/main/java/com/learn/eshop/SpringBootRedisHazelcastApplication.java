package com.learn.eshop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootApplication(exclude = HazelcastAutoConfiguration.class)
@EnableCaching
public class SpringBootRedisHazelcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisHazelcastApplication.class, args);
        log.info("============ {} Started ============", () -> SpringBootRedisHazelcastApplication.class.getSimpleName());
    }
}
