package com.learn.mongo;


import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learn.util.MyAppConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;


public class HelloTest {
    private static final Logger log = LogManager.getLogger();

    private MongoClient mongoClient = null;
    private MongoCollection<Document> collection = null;

    @BeforeEach
    public void before()
        throws Exception {
        log.info(() -> "before(). For each test.");

        ServerAddress serverAddress = new ServerAddress(MyAppConstants.DB_HOST, MyAppConstants.DB_PORT);
        MongoCredential mongoCredential = MongoCredential.createCredential(MyAppConstants.DB_USER, MyAppConstants.DB_DATABASE,
                MyAppConstants.DB_PASSWORD.toCharArray());

        mongoClient = MongoClients.create(
            com.mongodb.MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                    builder.hosts(List.of(serverAddress)))
                .credential(mongoCredential)
                .build());

        MongoDatabase db = mongoClient.getDatabase(MyAppConstants.DB_DATABASE);

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        // db = mongoClient.getDatabase("lukedb");

        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        collection = db.getCollection("user");

    }

    @AfterEach
    public void after()
        throws Exception {
        log.info(() -> "after(). For each test.");

        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Test
    public void testListDatabaseNames()
        throws Exception {
        log.info(() -> "Begin Test");

        MongoIterable<String> dbs = mongoClient.listDatabaseNames();
        for (String db : dbs) {
            log.info(() -> db);
        }

        log.info(() -> "End Test.");
    }

    @Test
    public void testGetCollection()
        throws Exception {
        log.info(() -> "Begin Test");

        log.info("User count: {}", () -> collection.countDocuments());

        FindIterable<Document> allUsers = collection.find();

        for (Document user : allUsers) {
            log.info(() -> user.toJson());
        }

        log.info(() -> "End Test.");
    }

    @Test
    public void testInsert()
        throws Exception {
        log.info(() -> "Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        log.info("User count: {}", () -> collection.countDocuments(searchQuery));

        /**** Insert ****/
        // create a document to store key and value
        Document document = new Document();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());
        collection.insertOne(document);

        FindIterable<Document> allUsers = collection.find();

        allUsers.forEach((Consumer<Document>) d -> {
            log.info(() -> "User: " + d.toJson());
        });

        log.info("User count: {}", collection.countDocuments(searchQuery));

        log.info(() -> "End Test.");
    }

    @Test
    public void testFind()
        throws Exception {
        log.info(() -> "Begin Test");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        long count = collection.countDocuments(searchQuery);
        log.info("User count: {}", () -> count);

        FindIterable<Document> result = collection.find(searchQuery);

        result.forEach((Consumer<Document>) d -> {
            log.info("User: {}", d.toJson());
        });

        log.info(() -> "End Test.");
    }

    @Test
    public void testUpdate()
        throws Exception {
        log.info(() -> "Begin Test");

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
            log.info("User: {}", () -> document.toJson());
        }

        log.info(() -> "End Test.");
    }

    @Test
    public void testDelete()
        throws Exception {
        log.info(() -> "Begin Test");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        long count = collection.countDocuments(newDocument);
        log.info("User count: {}", count);

        BasicDBObject searchQuery2 = new BasicDBObject().append("name", "mkyong-updated");

        count = collection.countDocuments(searchQuery2);
        log.info("User count: {}", count);

        FindIterable<Document> result = collection.find(searchQuery2);

        result.forEach((Consumer<Document>) d -> {
            log.info("User: {}", () -> d.toJson());
            collection.deleteMany(d);
        });

        count = collection.countDocuments(searchQuery2);
        log.info("User count: {}", count);

        log.info(() -> "End Test.");
    }

}
