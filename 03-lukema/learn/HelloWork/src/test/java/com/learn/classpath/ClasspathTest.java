package com.learn.classpath;


import java.net.URL;
import java.net.URLClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ClasspathTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testClassLoaderGetResource() {
        logger.info("Start Test.");

        String file = "log4j.dtd";

        URL url = ClasspathTest.class.getClassLoader().getResource(file);

        logger.info("Path: " + url.getPath());

        ClassLoader classLoader = ClasspathTest.class.getClassLoader();

        URL[] urls = ((URLClassLoader) classLoader).getURLs();

        for (URL u : urls) {
            String path = u.getFile();

            if (!path.endsWith(".jar") && path.contains("target")) {
                logger.info(path);
            }
        }

        logger.info("End Test.");
    }
    
    @Test
    public void testClassGetResource() {
        logger.info("Start Test.");

        String file = "/log4j.dtd";

        URL url = ClasspathTest.class.getResource(file);

        logger.info("Path: " + url.getPath());

        ClassLoader classLoader = ClasspathTest.class.getClassLoader();

        URL[] urls = ((URLClassLoader) classLoader).getURLs();

        for (URL u : urls) {
            String path = u.getFile();

            if (!path.endsWith(".jar") && path.contains("target")) {
                logger.info(path);
            }
        }

        logger.info("End Test.");
    }
}
