package com.learn.bean;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Getter
@Component
public class PropertyBean {

    /**
     * From application.properties
     */
    @Value("${my.property.age}")
    private String age;

}
