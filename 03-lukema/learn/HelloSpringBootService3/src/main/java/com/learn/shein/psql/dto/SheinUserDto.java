package com.learn.shein.psql.dto;


import lombok.Data;


@Data
public class SheinUserDto {

    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String businessname;
    private String countryCode;
    private Boolean isBuyOnly;

}
