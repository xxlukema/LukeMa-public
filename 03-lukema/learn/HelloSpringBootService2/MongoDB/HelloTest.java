package com.learn.mongo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.learn.util.MyAppConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;


public class HelloTest {
    private static final Logger LOG = LogManager.getLogger();

    private MongoClient mongoClient = null;
    private MongoCollection<Document> collection = null;

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        ServerAddress serverAddress = new ServerAddress(MyAppConstants.DB_HOST, MyAppConstants.DB_PORT);
        MongoCredential mongoCredential = MongoCredential.createCredential(MyAppConstants.DB_USER, MyAppConstants.DB_DATABASE, MyAppConstants.DB_PASSWORD.toCharArray());

        List<MongoCredential> mongoCredentials = new ArrayList<>();
        mongoCredentials.add(mongoCredential);

        mongoClient = new MongoClient(serverAddress, mongoCredentials);
        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        // db = mongoClient.getDatabase("lukedb");

        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        collection = db.getCollection("user");

    }

    @After
    public void after()
        throws Exception {
        LOG.info("after(). For each test.");

        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Test
    public void testListDatabaseNames()
        throws Exception {
        LOG.info("Begin Test");

        MongoIterable<String> dbs = mongoClient.listDatabaseNames();
        for (String db : dbs) {
            LOG.info(db);
        }

        LOG.info("End Test.");
    }

    @Test
    public void testGetCollection()
        throws Exception {
        LOG.info("Begin Test");

        LOG.info("User count: " + collection.count());

        FindIterable<Document> allUsers = collection.find();

        for (Document user : allUsers) {
            LOG.info(user.toJson());
        }

        LOG.info("End Test.");
    }

    @Test
    public void testInsert()
        throws Exception {
        LOG.info("Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        LOG.info("User count: " + collection.count(searchQuery));

        /**** Insert ****/
        // create a document to store key and value
        Document document = new Document();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());
        collection.insertOne(document);

        FindIterable<Document> allUsers = collection.find();

        allUsers.forEach((Consumer<Document>) d -> {
            LOG.info("User: " + d.toJson());
        });

        LOG.info("User count: " + collection.count(searchQuery));

        LOG.info("End Test.");
    }

    @Test
    public void testFind()
        throws Exception {
        LOG.info("Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        long count = collection.count(searchQuery);
        LOG.info("User count: " + count);

        FindIterable<Document> result = collection.find(searchQuery);

        result.forEach((Consumer<Document>) d -> {
            LOG.info("User: " + d.toJson());
        });

        LOG.info("End Test.");
    }

    @Test
    public void testUpdate()
        throws Exception {
        LOG.info("Begin Test");

        BasicDBObject query = new BasicDBObject();
        query.put("name", "mkyong");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        collection.updateMany(query, updateObj);

        BasicDBObject searchQuery2 = new BasicDBObject().append("name", "mkyong-updated");

        MongoCursor<Document> cursor = collection.find(searchQuery2).iterator();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            LOG.info("User: " + document.toJson());
        }

        LOG.info("End Test.");
    }

    @Test
    public void testDelete()
        throws Exception {
        LOG.info("Begin Test");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        BasicDBObject searchQuery2 = new BasicDBObject().append("name", "mkyong-updated");

        long count = collection.count(searchQuery2);
        LOG.info("User count: " + count);

        FindIterable<Document> result = collection.find(searchQuery2);

        result.forEach((Consumer<Document>) d -> {
            LOG.info("User: " + d.toJson());
            collection.deleteMany(d);
        });

        count = collection.count(searchQuery2);
        LOG.info("User count: " + count);

        LOG.info("End Test.");
    }

}
