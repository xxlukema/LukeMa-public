package com.learn.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyPojoObject {

    private String title;
    private String body;
    private Long userId;
}
