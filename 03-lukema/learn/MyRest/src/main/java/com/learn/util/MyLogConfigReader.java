package com.learn.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;


public class MyLogConfigReader {

    public static final String LogConfigFileName = "/logging.properties";

    public static void readConfig() {

        System.out.println("++++++ Loading Logger Configuration...");
        InputStream inputStream = MyLogConfigReader.class.getResourceAsStream(LogConfigFileName);
        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("Loaded Logger Configuration.");
    }

}
