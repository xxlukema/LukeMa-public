
package com.learn.boot.jms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = { "com.learn" })
@SpringBootApplication
public class BootJmsApplication {

    public static void main(String[] args) {
        // Launch the application

        SpringApplication.run(BootJmsApplication.class, args);

        // ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        // Send a message with a POJO - the template reuse the message converter
        // System.out.println("Sending an email message.");
        // jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
    }

}
