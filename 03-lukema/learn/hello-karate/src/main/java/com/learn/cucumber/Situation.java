package com.learn.cucumber;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Situation {
    private Integer value;
    private String status;
}
