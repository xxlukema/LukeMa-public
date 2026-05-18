package com.learn.mongo;


import java.util.Date;
import java.util.function.Consumer;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.learn.util.MyAppConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import lombok.extern.log4j.Log4j2;


@Log4j2
@TestMethodOrder(MethodOrderer.MethodName.class)
public class HelloTest {

    private static MongoClient mongoClient = null;

    @BeforeAll
    public static void beforeAll()
        throws Exception {
        log.debug(() -> "before(). For each test.");

        ServerAddress serverAddress = new ServerAddress(MyAppConstants.DB_HOST, MyAppConstants.DB_PORT);
        MongoCredential mongoCredential = MongoCredential.createCredential(MyAppConstants.DB_USER, MyAppConstants.DB_DATABASE,
                MyAppConstants.DB_PASSWORD.toCharArray());

        mongoClient = new MongoClient(serverAddress, mongoCredential, new MongoClientOptions.Builder().build());
    }

    @AfterAll
    public static void afterAll()
        throws Exception {
        log.debug(() -> "after(). For each test.");

        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Test
    public void test0ListDatabaseNames()
        throws Exception {
        log.debug(() -> "Begin Test");

        MongoIterable<String> dbs = mongoClient.listDatabaseNames();

        for (String db : dbs) {
            log.info(db);
        }

        log.debug(() -> "End Test.");
    }

    @Test
    public void testaCountUsers()
        throws Exception {
        log.debug(() -> "Begin Test");

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        // db = mongoClient.getDatabase("lukedb");

        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");

        log.debug("Before insert - User count: {}", () -> collection.countDocuments());

        FindIterable<Document> allUsers = collection.find();

        for (Document user : allUsers) {
            log.info(user.toJson());
        }

        log.debug(() -> "End Test.");
    }

    @Test
    public void testbGetCollection()
        throws Exception {
        log.debug(() -> "Begin Test");

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");
        log.debug("User count: {}", () -> collection.countDocuments());

        FindIterable<Document> allUsers = collection.find();

        for (Document user : allUsers) {
            log.info(user.toJson());
        }

        log.debug(() -> "End Test.");
    }

    /**
     * mongosh lukedb -u luke -p luke
     *
     */
    @Test
    public void testcInsert()
        throws Exception {
        log.debug(() -> "Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");
        log.debug("Before insert - User count: {}", () -> collection.countDocuments(searchQuery));

        // create a document to store key and value
        Document document = new Document();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());

        /**
         * insert
         */
        collection.insertOne(document);

        FindIterable<Document> allUsers = collection.find();

        allUsers.forEach((Consumer<Document>) d -> {
            log.debug("After insert - User: {}", () -> d.toJson());
        });

        log.debug("After insert - User count: {}", () -> collection.countDocuments(searchQuery));

        log.debug(() -> "End Test.");
    }

    @Test
    public void testdFind()
        throws Exception {
        log.debug(() -> "Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");
        long count = collection.countDocuments(searchQuery);
        log.debug("After insert - User count: {}", () -> count);

        /**
         * find
         */
        FindIterable<Document> result = collection.find(searchQuery);

        result.forEach((Consumer<Document>) d -> {
            log.debug("After insert - User: {}", () -> d.toJson());
        });

        log.debug(() -> "End Test.");
    }

    @Test
    public void testeUpdate()
        throws Exception {
        log.debug(() -> "Begin Test");

        BasicDBObject query = new BasicDBObject();
        query.put("name", "mkyong");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");

        /**
         * updateMany
         */
        collection.updateMany(query, updateObj);

        BasicDBObject searchQuery = new BasicDBObject().append("name", "mkyong-updated");

        MongoCursor<Document> cursor = collection.find(searchQuery).iterator();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            log.debug("After update - User: {}", () -> document.toJson());
        }

        log.debug(() -> "End Test.");
    }

    @Test
    public void testfDelete()
        throws Exception {
        log.debug(() -> "Begin Test");

        BasicDBObject searchQuery = new BasicDBObject().append("name", "mkyong-updated");

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);
        MongoCollection<Document> collection = db.getCollection("mkyong");
        long count = collection.countDocuments(searchQuery);
        log.debug("Before delete - User count: {}", () -> count);

        FindIterable<Document> result = collection.find(searchQuery);

        result.forEach((Consumer<Document>) d -> {
            log.debug("Before delete - User: {}", () -> d.toJson());

            /**
             * deleteMany
             */
            collection.deleteMany(d);
        });

        long count2 = collection.countDocuments(searchQuery);
        log.debug("After delete - User count: {}", () -> count2);

        log.debug(() -> "End Test.");
    }

}
