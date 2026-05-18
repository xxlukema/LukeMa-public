package com.learn.mapstruct;


import java.math.BigDecimal;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import lombok.Data;


@Data
public class CarDto {
    private String make;
    private int seatCount;
    private String type;
    private CarDtoEnum carDtoEnum;
    private List<String> strList;
    private List<MyPojo> myPojoList;
    private MyPojo[] myPojoArray;

    private BigDecimal carWeight;
    private MyPojo name;
    private XMLGregorianCalendar date;
}
