package com.learn.jks;


import java.io.InputStream;
import java.security.KeyStore;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class HelloJksTest {
    private static final Logger LOG = LogManager.getLogger();

    private static final String KEYSTORE_TYPE = "jks";
    private static final String KEYSTORE_FILE = "/META-INF/ContractApprovalKeystore.jks";
    //private static final String KEYSTORE_FILE = "/META-INF/My_Key.jks";
    //private static final String KEYSTORE_FILE = "/META-INF/lukekeystore.jks";
    private static final String KEYSTORE_PASS = "changeit";
    //private static final String PRIVATEKEY_PASS = "changeit";
    //private static final String PRIVATEKEY_ALIAS = "XXXX";
    //private static final String CERTIFICATE_ALIAS = "dashboard";

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        char[] password = KEYSTORE_PASS.toCharArray();
        try (InputStream is = HelloJksTest.class.getResourceAsStream(KEYSTORE_FILE);) {
            keyStore.load(is, password);
        } catch (Throwable e) {
            LOG.error("Exception", e);
            throw e;
        }

        LOG.info("End Test.");

    }
}
