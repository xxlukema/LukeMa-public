package com.learn.rest.element;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "validationError")
public class BeanValidationError {

    private String invalidValue;
    private String message;
    private String messageTemplate;
    private String path;

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
