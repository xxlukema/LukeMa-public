package com.learn.dao;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class MongoDao {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    MongoTemplate mongoTemplate;

    public <T> void save(T t) {
        log.debug(() -> "Begin save");

        try {
            mongoTemplate.save(t);
            log.debug(() -> "Saved: " + t);
        } finally {
            log.debug(() -> "End save.");
        }
    }

    public <T> void insert(T t) {
        log.debug(() -> "Begin insert");

        try {
            mongoTemplate.insert(t);
            log.debug(() -> "Inserted: " + t);
        } finally {
            log.debug(() -> "End insert.");
        }
    }

    public <T> List<T> findAll(Class<T> type) {
        log.debug(() -> "Begin findAll");

        try {
            List<T> listTs = mongoTemplate.findAll(type);
            return listTs;
        } finally {
            log.debug(() -> "End findAll.");
        }
    }

    public <T> List<T> find(Query query, Class<T> type) {
        log.debug(() -> "Begin find");

        try {
            List<T> listTs = mongoTemplate.find(query, type);
            return listTs;
        } finally {
            log.debug(() -> "End find.");
        }
    }

    public <T> T findOne(Query query, Class<T> type) {
        log.debug(() -> "Begin findOne");

        try {
            return mongoTemplate.findOne(query, type);
        } finally {
            log.debug(() -> "End findOne.");
        }
    }

    public <T> void updateMulti(Query query, Update update, Class<T> type) {
        log.debug(() -> "Begin updateMulti");

        try {
            mongoTemplate.updateMulti(query, update, type);
        } finally {
            log.debug(() -> "End updateMulti.");
        }
    }

    public <T> void remove(Query query, Class<T> type) {
        log.debug(() -> "Begin remove");

        try {
            mongoTemplate.remove(query, type);
        } finally {
            log.debug(() -> "End remove.");
        }
    }

    public <T> void remove(T t) {
        log.debug(() -> "Begin remove");

        try {
            mongoTemplate.remove(t);
        } finally {
            log.debug(() -> "End remove.");
        }
    }
}
