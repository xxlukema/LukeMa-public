package com.learn.rest.resource.junit;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Getter
@Setter
@Component
public class GreetingComponent {

    /**
     * 1. application.properties:
     * my.property.age=25
     * 
     * 2. application-default.properties:
     * my.property.age=18
     * 
     * 3. application-test.properties:
     * my.property.age = 40
     * 
     */
    @Value("${my.property.age:20}")
    private String age;

    /**
     * application.properties:
     * my.property.name=Luke
     */
    @Value("${my.property.name:Hello}")
    private String name;

    public String print() {
        log.info("GreetingComponent ::: name: {}, age: {}", () -> name, () -> age);
        
        return "Message printed.";
    }
}
