package com.learn.mapstruct;


import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;
}
