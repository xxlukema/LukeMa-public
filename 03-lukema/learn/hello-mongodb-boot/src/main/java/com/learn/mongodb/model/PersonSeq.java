package com.learn.mongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = Person.SEQ_NAME)
public class PersonSeq {

    /**
     * KEEP! Do not delete.
     * This is the id for this document.
     */
    @Id
    private String id;

    /**
     * KEEP! Do not delete.
     * This is the sequence number for `Person` collection
     */
    private long seq;

}
