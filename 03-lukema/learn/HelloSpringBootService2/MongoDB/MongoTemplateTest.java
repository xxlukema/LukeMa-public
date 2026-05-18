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
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String TEMPLATE_USER_COLLECTION_NAME = "template_user";

    @Test
    public void testListDatabaseNames()
        throws Exception {
        LOG.info("Begin Test");

        DB db = mongoTemplate.getDb();

        LOG.info(db.getName());

        LOG.info("End Test.");
    }

    @Test
    public void testGetCollection()
        throws Exception {
        LOG.info("Begin Test");

        DBCollection dc = mongoTemplate.getCollection(TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("collection name=" + dc.getFullName());

        LOG.info("End Test.");
    }

    @Test
    public void testGetCollectionNames()
        throws Exception {
        LOG.info("Begin Test");

        Set<String> collectionNames = mongoTemplate.getCollectionNames();

        collectionNames.forEach((item) -> {
            LOG.info("collection name= " + item);
        });

        LOG.info("End Test.");
    }

    @Test
    public void testInsert()
        throws Exception {
        LOG.info("Begin Test");

        User user = new User();
        user.setName("Jon");
        mongoTemplate.insert(user, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("Added user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testSave()
        throws Exception {
        LOG.info("Begin Test");

        User user = new User();
        user.setName("Albert");
        mongoTemplate.save(user, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("Saved user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testSaveNoCollectionName()
        throws Exception {
        LOG.info("Begin Test");

        User user = new User();
        user.setName("Luke");
        mongoTemplate.save(user);

        LOG.info("Saved user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testFindNoCollectionName()
        throws Exception {
        LOG.info("Begin Test");

        List<User> listUsers = mongoTemplate.findAll(User.class);

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach((item) -> {
            LOG.info("Found user: " + item);
        });

        LOG.info("End Test.");
    }

    @Test
    public void testFind()
        throws Exception {
        LOG.info("Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jon"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach(item -> {
            LOG.info("Found user: " + item);
        });

        LOG.info("End Test.");
    }

    @Test
    public void testUpdate()
        throws Exception {
        LOG.info("Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jon"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach(item -> {
            LOG.info("Found user: " + item);
            item.setName("Jim");
            item.setAge(10);
            mongoTemplate.save(item, TEMPLATE_USER_COLLECTION_NAME);
        });

        query = new Query();
        query.addCriteria(Criteria.where("name").is("Jim"));
        User user = mongoTemplate.findOne(query, User.class, TEMPLATE_USER_COLLECTION_NAME);
        LOG.info("Found user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testUpdateJsonConvertor()
        throws Exception {
        LOG.info("Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Jim"));

        Update update = new Update();
        update.set("name", "Tim");

        WriteResult writeResult = mongoTemplate.updateMulti(query, update, TEMPLATE_USER_COLLECTION_NAME);
        LOG.info("writeResult.toString() = " + writeResult.toString());

        query = new Query();
        query.addCriteria(Criteria.where("name").is("Tim"));
        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach(item -> {
            try {
                String jsonString = JsonConvertor.toString(item);
                LOG.info("User json: " + jsonString);

                User user = JsonConvertor.toObject(jsonString, User.class);
                LOG.info("User object: " + user);
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        });

        LOG.info("End Test.");
    }

    @Test
    public void testDelete()
        throws Exception {
        LOG.info("Begin Test");

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*im"));

        User user = mongoTemplate.findOne(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("user = " + user);

        mongoTemplate.remove(user, TEMPLATE_USER_COLLECTION_NAME);

        List<User> listUsers = mongoTemplate.find(query, User.class, TEMPLATE_USER_COLLECTION_NAME);

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach(item -> {
            try {
                String jsonString = JsonConvertor.toString(item);
                LOG.info("User json: " + jsonString);
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        });

        LOG.info("End Test.");
    }

}
