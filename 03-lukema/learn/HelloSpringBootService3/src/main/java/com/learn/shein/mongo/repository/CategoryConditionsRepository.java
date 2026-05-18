package com.learn.shein.mongo.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.learn.shein.mongo.model.CategoryConditions;


public interface CategoryConditionsRepository
    extends MongoRepository<CategoryConditions, ObjectId> {

    List<CategoryConditions> findByCategory(String category);
}
