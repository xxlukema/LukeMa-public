package com.learn.bbb;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloFileTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test()
        throws Exception {
        LOG.info("Begin Test.");

        String file = "file:D:/01-AppServers/glassfish-4.1/glassfish/domains/api/conf/rsb/accounts.xml";

        URL url = HelloFileTest.class.getResource(file);

        if (url == null) {
            LOG.info("url is null.");

            return;
        }

        File accountFile = new File(url.getPath());

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(accountFile))) {

            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }

        } catch (Exception e) {
            LOG.error(e);
        }

        LOG.info(sb.toString());

        LOG.info("End Test.");

    }
}
