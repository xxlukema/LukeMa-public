package com.learn.validate;


import lombok.Data;


@Data
public class ValidatedPhone {

    @ContactNumberConstraint
    private String phone;

}
