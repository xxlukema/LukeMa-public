package com.learn.kafka.producer.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.learn.kafka.producer" })
public class MySpringBootKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootKafkaProducerApplication.class, args);
    }
}
