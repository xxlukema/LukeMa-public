package com.learn;


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.Person;
import com.learn.service.MyService;
import com.learn.util.SpringApplicationContext;


public class AppTest {

    protected static final Logger LOG = Logger.getLogger(AppTest.class);

    private MyService myService;

    @Before
    public void before()
        throws Exception {
        LOG.info("before().");
        myService = SpringApplicationContext.getBean("myService");
    }

    @After
    public void after()
        throws Exception {
        LOG.info("after().");
    }

    @Test
    public void testApp()
        throws Exception {
        LOG.info("Test begin.");

        addRecords();
        addRecordsRollBack();
        queryRecords();

        LOG.info("Test complete.");
    }

    public void addRecords() {
        Person person = new Person();
        person.setName("Luke Ma");

        Widget widget = new Widget();
        widget.setName("My Widget");

        try {
            myService.saveObjects(person, widget);
        } catch (Exception e) {
            LOG.info("Got Exception: " + e.getMessage());
        }
    }

    public void addRecordsRollBack()
        throws Exception {
        Person person = new Person();
        person.setName("Luke Ma - Rollback");

        Widget widget = new Widget();
        widget.setName("My Widget - Rollback");

        try {
            myService.saveObjectsRollback(person, widget);
        } catch (Exception e) {
            LOG.info("Got Exception: " + e.getMessage());
        }
    }

    public void queryRecords()
        throws Exception {
        myService.list();
    }

}
