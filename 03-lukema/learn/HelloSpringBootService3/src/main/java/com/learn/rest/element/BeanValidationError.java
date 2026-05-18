package com.learn.rest.element;


import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement(name = "validationError")
public class BeanValidationError {

    private String invalidValue;
    private String message;
    private String messageTemplate;
    private String path;

}
