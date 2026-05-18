package com.learn.shein.mongo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.shein.mongo.model.CategoryConditions;
import com.learn.shein.mongo.repository.CategoryConditionsRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CategoryConditionsService {

    private final CategoryConditionsRepository categoryConditionsRepository;

    public CategoryConditions findByCategory(String category) {
        List<CategoryConditions> cats = this.categoryConditionsRepository.findByCategory(category);
        if (cats == null || cats.size() == 0) {
            return null;
        } else {
            return cats.get(0);
        }

    }

    public List<CategoryConditions> findAllCategories() {
        return this.categoryConditionsRepository.findAll();
    }

}
