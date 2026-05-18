package com.learn.rest.element;


import jakarta.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement
public class TodoResponse {

    private Status status;
    private String description;
    private BeanValidationErrors beanValidationErrors;

}
