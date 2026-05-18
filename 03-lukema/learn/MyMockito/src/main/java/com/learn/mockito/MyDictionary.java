package com.learn.mockito;


import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyDictionary {

    private static final Logger LOG = LogManager.getLogger();

    private Map<String, String> wordMap;

    public MyDictionary() {
        // wordMap = new HashMap<String, String>();
        LOG.info("Constructor called.");
    }

    public String greet(String name) {
        LOG.info("greet() called.");
        return "Hello " + name;
    }

    public List<String> getList() {
        LOG.info("getList() called.");
        return null;
    }

    public String add(final String word, final String meaning) {
        LOG.info("add() called. meaning = " + meaning);
        return wordMap.put(word, meaning);
    }

    public String getMeaning(final String word) {
        LOG.info("getMeaning() called.");
        return wordMap.get(word);
    }
}
