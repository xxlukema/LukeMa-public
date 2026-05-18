package com.learn.rest.element;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BeanValidationErrors {

    @XmlElement(name = "validationErrors")
    private final List<BeanValidationError> validationErrors = new ArrayList<>();

}
