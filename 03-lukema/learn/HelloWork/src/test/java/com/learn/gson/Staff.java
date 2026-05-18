package com.learn.gson;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class Staff {
    private String name;
    private int age;
    private String[] position; // array
    private List<String> skills; // list
    private Map<String, BigDecimal> salary; // map
    private boolean active; // boolean
}
