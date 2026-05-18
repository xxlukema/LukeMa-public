package com.learn.mockito;


import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.learn.mockito.util.TestUtils;


public class MyDictionaryPrivateFieldTest {

    private static final Logger LOG = LogManager.getLogger();

    @Mock
    HashMap<String, String> mockWordMap;

    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        MyDictionary myDictionary = new MyDictionary();

        // FieldSetter.setField(myDictionary, myDictionary.getClass().getDeclaredField("wordMap"), mockWordMap);
        TestUtils.setField(myDictionary, "wordMap", mockWordMap);

        String putCalled = "Put called.";

        Mockito.when(mockWordMap.put(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(putCalled);

        String ret = myDictionary.add("Luke", "Ma");
        LOG.info("ret = " + ret);
        Assert.assertTrue(putCalled.equals(ret));

        // Mockito.verify(mockWordMap).put("Luke", "Ma").equals("Put called.");

        Mockito.when(mockWordMap.get(ArgumentMatchers.anyString())).thenReturn("Gotcha!");
        String result = myDictionary.getMeaning("Luke");

        LOG.info("result = " + result);

        LOG.info("End Test.");

    }

}
