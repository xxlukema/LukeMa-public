package com.learn.mockito;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;


public class MyDictionaryTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        MyDictionary myDictionary = new MyDictionary();

        // MyDictionary myDictionary = Mockito.mock(MyDictionary.class);

        MyDictionary mySpyDictionary = Mockito.spy(myDictionary);
        Mockito.when(mySpyDictionary.greet((String) null)).thenReturn("Mocked answer!");

        LOG.info("Calling spied method 1: ");
        /**
         * Call mock. Not real invoke
         */
        String result = mySpyDictionary.greet(null);

        LOG.info("result = " + result);

        LOG.info("Calling real method: ");
        /**
         * Real invoke
         */
        result = mySpyDictionary.greet("Luke Ma");
        LOG.info("result = " + result);

        Mockito.when(mySpyDictionary.greet(Mockito.anyString())).thenReturn("Got you Mocker, too!");

        LOG.info("Calling spied method 2: ");
        result = mySpyDictionary.greet("Luke Ma");
        LOG.info("result = " + result);

        LOG.info("End Test.");

    }

    @Ignore
    @Test
    public void runListTest()
        throws Exception {
        LOG.info("Begin Test");

        MyDictionary myDictionary = new MyDictionary();

        // MyDictionary myDictionary = Mockito.mock(MyDictionary.class);

        List<String> list = new ArrayList<String>();
        list.add("One");
        list.add("Two");

        MyDictionary mySpyDictionary = Mockito.spy(myDictionary);
        Mockito.when(mySpyDictionary.getList()).thenReturn(list);

        List<String> result = mySpyDictionary.getList();

        LOG.info("result.size() = " + result.size());
        LOG.info("result = " + result);

        LOG.info("End Test.");

    }

}
