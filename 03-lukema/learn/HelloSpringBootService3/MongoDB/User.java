package com.learn.data;


import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document
public class User
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigInteger id;

    @JsonProperty("name")
    private String name;

    private Integer age;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
