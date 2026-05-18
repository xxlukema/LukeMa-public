package com.learn.bbb;


import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class PropertiesBoundleTest {
    
    private static final Logger LOG = LogManager.getLogger();

    private void displayValue(Locale currentLocale, String key) {

        ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", currentLocale);
        String value = labels.getString(key);
        LOG.debug("Locale = " + currentLocale.toString() + ", " + "key = " + key + ", " + "value = " + value);

    } // displayValue

    private void iterateKeys(Locale currentLocale) {

        ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", currentLocale);

        Enumeration<String> bundleKeys = labels.getKeys();

        while (bundleKeys.hasMoreElements()) {
            String key = (String) bundleKeys.nextElement();
            String value = labels.getString(key);
            LOG.debug("key = " + key + ", " + "value = " + value);
        }

    } // iterateKeys

    @Test
    public void testBoundle() {

        Locale[] supportedLocales = { Locale.FRENCH, Locale.GERMAN, Locale.ENGLISH };

        for (int i = 0; i < supportedLocales.length; i++) {
            displayValue(supportedLocales[i], "s2");
        }

        LOG.debug("");

        iterateKeys(supportedLocales[2]);

    } // main

} // class
