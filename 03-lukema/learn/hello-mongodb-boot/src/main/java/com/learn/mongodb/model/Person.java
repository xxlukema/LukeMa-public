package com.learn.mongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Document(collection = "Person")
public class Person {

    @Transient
    public static final String SEQ_NAME = "person_seq";

    @Id
    private Long id;
    private String name;

    /*
    public Person(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
    */

}
