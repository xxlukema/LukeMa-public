package com.learn.shein.mongo.model;


import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;


@Data
@Document(collection = SheinItem.SEQ_NAME)
public class SheinItemSeq {

    /**
     * KEEP! Do not delete.
     * This is the id for this document.
     */
    @Id
    private String id;

    /**
     * KEEP! Do not delete.
     * This is the sequence number for `SheinItem` collection
     */
    private long seq;

}
