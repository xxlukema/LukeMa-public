package com.learn.kafka.consumer.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.learn.kafka.consumer" })
public class MySpringBootKafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootKafkaConsumerApplication.class, args);
    }
}
