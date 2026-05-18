package com.learn.rest.element;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement(name = "createCustomerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateCustomerResponse {

    private Long status;

    private String customerId;

}
