package com.learn.jdbc;


import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class SelectSysdatePureJDBC {
    protected static final Logger LOG = Logger.getLogger(SelectSysdatePureJDBC.class);

    @Test
    public void testMongo()
        throws UnknownHostException {

        MongoClient mongo = new MongoClient("localhost", 27017);

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        DB db = mongo.getDB("testdb");

        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        DBCollection table = db.getCollection("user");

        /**** Insert ****/
        // create a document to store key and value
        BasicDBObject document = new BasicDBObject();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());
        table.insert(document);

        /**** Find and display ****/
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            LOG.info(dbo);
        }

        /**** Update ****/
        // search document where name="mkyong" and update it with new values
        BasicDBObject query = new BasicDBObject();
        query.put("name", "mkyong");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        table.update(query, updateObj);

        /**** Find and display ****/
        BasicDBObject searchQuery2 = new BasicDBObject().append("name", "mkyong-updated");

        DBCursor cursor2 = table.find(searchQuery2);

        while (cursor2.hasNext()) {
            LOG.info(cursor2.next());
        }

        /**** Done ****/
        LOG.info("Done");
    }

    @Test
    public void testRead()
        throws UnknownHostException {

        MongoClient mongo = new MongoClient("localhost", 27017);

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        DB db = mongo.getDB("testdb");

        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        DBCollection table = db.getCollection("user");

        DBCursor cursor2 = table.find();

        while (cursor2.hasNext()) {
            LOG.info(cursor2.next());
        }

        LOG.info("Done");
    }
}
