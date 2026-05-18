package com.learn.mapstruct;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;
    private CarEnum carEnum;
    private List<String> strList;
    private List<MyPojo> myPojoList;
    private MyPojo[] myPojoArray;

    private String carWeight;
    private String name;
    private String date;
}
