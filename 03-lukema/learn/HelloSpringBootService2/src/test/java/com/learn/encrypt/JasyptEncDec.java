package com.learn.encrypt;


import java.security.Provider;
import java.security.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


public class JasyptEncDec {

    private static final Logger LOG = LogManager.getLogger();

    private static StandardPBEStringEncryptor PbeEncryptor = null;

    @BeforeClass
    public static void beforeClass()
        throws Exception {
        LOG.info("beforeClass(). Once for the class.");

        PbeEncryptor = new StandardPBEStringEncryptor();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        String encryptionPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        String encryptionAlgorithm = System.getenv("JASYPT_ENCRYPTOR_ALGORITHM");

        LOG.info("encryptionPassword = " + encryptionPassword + " encryptionAlgorithm = " + encryptionAlgorithm);

        PbeEncryptor.setAlgorithm(encryptionAlgorithm);
        PbeEncryptor.setPassword(encryptionPassword);
    }

    @SuppressWarnings("unused")
    @Test
    public void encrypt() {

        final String mySecret = "luke";
        String encrypted = null;
        String decrypted = null;

        given: {
            encrypted = PbeEncryptor.encrypt(mySecret);
            LOG.info(encrypted);

            decrypted = PbeEncryptor.decrypt(encrypted);
            LOG.info(decrypted);
        }
        when: {
            Assert.assertTrue("They are equal", mySecret.equals(decrypted));
        }
        then: {
            LOG.info("Asserted.");
        }
    }

    @Ignore
    @Test
    public void listProviders() {
        for (Provider provider : Security.getProviders()) {
            LOG.info("Provider: " + provider.getName());
            for (Provider.Service service : provider.getServices()) {
                LOG.info("  Algorithm: " + service.getAlgorithm());
            }
        }
    }

}
