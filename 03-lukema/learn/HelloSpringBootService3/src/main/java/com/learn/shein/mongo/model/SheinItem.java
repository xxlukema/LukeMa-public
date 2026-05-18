package com.learn.shein.mongo.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SheinItem")
public class SheinItem {

    @Transient
    public static final String SEQ_NAME = "SheinItem_seq";

    @Id
    private Long id;

    private String title;
    private String category;
    private String condition;
    private String brand;
    private String description;

    private Map<String, Object> optionalAttributes = new HashMap<>();

    private List<String> imageFileNames = new ArrayList<>();

    private Float price;
    private Float discount;
    private Integer availableUnitQuantity;
    private Integer soldUnitQuantity;
    private String sellerUsername;
    private Date dateUpdated;
    private String status;

}
