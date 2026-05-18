package com.learn.classpath;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ClasspathModifierTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testClassLoaderGetResource()
        throws MalformedURLException {
        logger.info("Start Test.");

        /**
         * TODO
         */
        File dirToAdd = new File("/D:/opt/att_billing");
        String file = "/test.properties";
        URL url = null;
        try {
            url = dirToAdd.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> sysclass = URLClassLoader.class;

        try {
            @SuppressWarnings("unchecked")
            Class<URL>[] parameters = new Class[] { URL.class };
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] { url });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        logger.info("Loading property file...");
        InputStream inputStream = ClasspathModifierTest.class.getResourceAsStream(file);
        if (inputStream == null) {
            logger.info("File not found.");
        } else {
            try {
                Properties prop = new Properties();
                prop.load(inputStream);
                logger.info("Test: " + prop.getProperty("test"));
            } catch (SecurityException | IOException e1) {
                e1.printStackTrace();
            }
            logger.info("Loaded property file.");
        }

        String path = System.getProperty("user.dir");
        logger.info("user.dir = " + path);

        /**
         * TODO
         */

        logger.info("End Test.");
    }

}
