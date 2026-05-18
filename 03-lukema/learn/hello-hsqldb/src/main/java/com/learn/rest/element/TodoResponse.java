package com.learn.rest.element;


import javax.xml.bind.annotation.XmlRootElement;

import jakarta.transaction.Status;
import lombok.Data;


@Data
@XmlRootElement
public class TodoResponse {

    private Status status;
    private String description;
    private BeanValidationErrors beanValidationErrors;

}
