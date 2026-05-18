package com.learn.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Employee {

    private Integer id;
    
    @JsonProperty("employee_name")
    private String employeeName;
    
    @JsonProperty("employee_salary")
    private Float employeeSalary;
    
    @JsonProperty("employee_age")
    private Integer employeeAge;
    
    @JsonProperty("profile_image")
    private String profileImage;

}
