package com.learn.shein.mongo.model;


import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CategoryConditions")
public class CategoryConditions {

    @Id
    private ObjectId id;

    private String category;
    private List<String> conditions = new ArrayList<>();
}
