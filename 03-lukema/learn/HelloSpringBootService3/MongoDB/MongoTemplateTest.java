package com.learn.mongo;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.data.User;
import com.learn.util.JsonConvertor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/SpringMVC-servlet.xml" })
public class MongoTemplateTest {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String TEMPLATE_USER_COLLECTION_NAME = "template_user";

    @Test
    public void testListDatabaseNames()
        throws Exception {
        log.debug(() -> "Begin Test");

        DB db = mongoTemplate.getDb();

        log.debug(() -> db.getName());

        log.debug(() -> "End Test.");
    }

    @Test
    public void testGetCollection()
        throws Exception {
        log.debug(() -> "Begin Test");

        DBCollection dc = mongoTemplate.getCollection(TEMPLATE_USER_COLLECTION_NAME);

        log.debug("collection name = {}", () -> dc.getFullName());

        log.debug(() -> "End Test.");
    }

    @Test
    public void testGetCollectionNames()
        throws Exception {
        log.debug(() -> "Begin Test");

        Set<String> collectionNames = mongoTemplate.getCollectionNames();

        collectionNames.forEach((item) -> {
            log.debug("collection name = {}", () -> item);
        });

        log.debug(() -> "End Test.");
    }

    @Test
    public void testInsert()
        throws Exception {
        log.debug(() -> "Begin Test");

        User user = new User();
        user.setName("Jon");
        mongoTemplate.insert(user, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("Added user: {}", () -> user);

        log.debug(() -> "End Test.");
    }

    @Test
    public void testSave()
        throws Exception {
        log.debug(() -> "Begin Test");

        User user = new User();
        user.setName("Albert");
        mongoTemplate.save(user, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("Saved user: {}", () -> user);

        log.debug(() -> "End Test.");
    }

    @Test
    public void testSaveNoCollectionName()
        throws Exception {
        log.debug(() -> "Begin Test");

        User user = new User();
        user.setName("Luke");
        mongoTemplate.save(user);

        log.debug("Saved user: {}", () -> user);

        log.debug(() -> "End Test.");
    }

    @Test
    public void testFindNoCollectionName()
        throws Exception {
        log.debug(() -> "Begin Test");

        List<User> listUsers = mongoTemplate.findAll(User.class);

        log.debug("listUser.size() = {}", () -> listUsers.size());

        listUsers.forEach((item) -> {
            log.debug("Found user: {}", () -> item);
        });

        log.debug(() -> "End Test.");
    }

    @Test
    public void testFind()
        throws Exception {
        log.debug(() -> "Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jon"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("listUser.size() = {}", () -> listUsers.size());

        listUsers.forEach(item -> {
            log.debug("Found user: {}", () -> item);
        });

        log.debug(() -> "End Test.");
    }

    @Test
    public void testUpdate()
        throws Exception {
        log.debug(() -> "Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jon"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("listUser.size() = {}", () -> listUsers.size());

        listUsers.forEach(item -> {
            log.debug("Found user: {}", () -> item);
            item.setName("Jim");
            item.setAge(10);
            mongoTemplate.save(item, TEMPLATE_USER_COLLECTION_NAME);
        });

        query = new Query();
        query.addCriteria(Criteria.where("name").is("Jim"));
        User user = mongoTemplate.findOne(query, User.class, TEMPLATE_USER_COLLECTION_NAME);
        log.debug("Found user: {}", () -> user);

        log.debug(() -> "End Test.");
    }

    @Test
    public void testUpdateJsonConvertor()
        throws Exception {
        log.debug(() -> "Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jim"));

        Update update = new Update();
        update.set("name", "Tim");

        WriteResult writeResult = mongoTemplate.updateMulti(query, update, TEMPLATE_USER_COLLECTION_NAME);
        log.debug("writeResult.toString() = {}", () -> writeResult.toString());

        query = new Query();
        query.addCriteria(Criteria.where("name").is("Tim"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("listUser.size() = {}", () -> listUsers.size());

        listUsers.forEach(item -> {
            try {
                String jsonString = JsonConvertor.toString(item);
                log.debug("User json: {}", () -> jsonString);

                User user = JsonConvertor.toObject(jsonString, User.class);
                log.debug("User object: {}", () -> user);
            } catch (IOException e) {
                log.error("IOException", e);
            }
        });

        log.debug(() -> "End Test.");
    }

    @Test
    public void testDelete()
        throws Exception {
        log.debug(() -> "Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*im"));

        User user = mongoTemplate.findOne(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        log.debug(() -> "user = " + user);

        mongoTemplate.remove(user, TEMPLATE_USER_COLLECTION_NAME);

        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        log.debug("listUser.size() = {}", () -> listUsers.size());

        listUsers.forEach(item -> {
            try {
                String jsonString = JsonConvertor.toString(item);
                log.debug("User json: {}", () -> jsonString);
            } catch (IOException e) {
                log.error("IOException", e);
            }
        });

        log.debug(() -> "End Test.");
    }

}
