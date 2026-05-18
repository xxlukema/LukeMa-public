package com.learn.spring.rowmapper;


import java.io.Serializable;

import lombok.Data;


@Data
public class ParamsRow
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private int age;

}
