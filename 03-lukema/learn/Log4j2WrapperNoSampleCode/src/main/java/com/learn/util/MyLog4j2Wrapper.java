package com.learn.util;


/**
 * No working sample code found until today Monday, July 23, 2018. 
 */
public class MyLog4j2Wrapper {

    static {
        System.setProperty("log4j.configurationFile", MyLog4j2Wrapper.class.getClassLoader().getResource("my-log4j2.xml").getPath());
    }

    public static void getLogger() {
    }

}
