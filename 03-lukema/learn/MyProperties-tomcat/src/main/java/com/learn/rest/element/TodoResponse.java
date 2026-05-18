package com.learn.rest.element;


import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TodoResponse {

    private Status status;
    private String description;
    private BeanValidationErrors beanValidationErrors;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BeanValidationErrors getBeanValidationErrors() {
        return beanValidationErrors;
    }

    public void setBeanValidationErrors(BeanValidationErrors beanValidationErrors) {
        this.beanValidationErrors = beanValidationErrors;
    }

}
