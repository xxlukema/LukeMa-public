package com.learn.pojo;

import lombok.Data;

@Data
public class NestedPojo {
    private String field1;
    private int field2;
    private ChildPojo childPojo;
}
