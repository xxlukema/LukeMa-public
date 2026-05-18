package com.learn.encrypt;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Provider;
import java.security.Security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import lombok.extern.log4j.Log4j2;


@TestInstance(Lifecycle.PER_CLASS)
@Log4j2
class JasyptEncDecTest {

    private final StandardPBEStringEncryptor pbeEncryptor = new StandardPBEStringEncryptor();

    @BeforeAll
    protected void before()
        throws Exception {
        log.debug(() -> "before(). For each test.");

        String encryptionPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        String encryptionAlgorithm = System.getenv("JASYPT_ENCRYPTOR_ALGORITHM");

        log.debug("encryptionPassword = {} encryptionAlgorithm = {}", () -> encryptionPassword, () -> encryptionAlgorithm);

        pbeEncryptor.setAlgorithm(encryptionAlgorithm);
        pbeEncryptor.setPassword(encryptionPassword);
    }

    @SuppressWarnings("unused")
    @Test
    void encrypt() {

        final String mySecret = "luke";
        String encrypted;
        String decrypted;

        given: {
            encrypted = pbeEncryptor.encrypt(mySecret);
            log.info("{}", encrypted);

            decrypted = pbeEncryptor.decrypt(encrypted);
            log.info(decrypted);
        }
        when: {
            assertEquals(decrypted, mySecret);
        }
        then: {
            log.debug(() -> "Asserted.");
        }
    }

    @Disabled("Do not know why.")
    @Test
    void listProviders() {
        for (Provider provider : Security.getProviders()) {
            log.debug(() -> "Provider: " + provider.getName());
            for (Provider.Service service : provider.getServices()) {
                log.debug(() -> "  Algorithm: " + service.getAlgorithm());
            }
        }
    }

}
