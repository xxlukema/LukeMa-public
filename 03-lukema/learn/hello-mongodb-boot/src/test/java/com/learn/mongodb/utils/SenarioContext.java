package com.learn.mongodb.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SenarioContext {

    private static final Map<String, Object> map = new ConcurrentHashMap<>();

    private SenarioContext() {
    }

    public static void setContext(String key, Object value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getContext(String key) {
        return (T) map.get(key);
    }

}
