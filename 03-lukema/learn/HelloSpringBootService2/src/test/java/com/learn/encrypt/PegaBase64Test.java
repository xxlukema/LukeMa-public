package com.learn.encrypt;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class PegaBase64Test {

    private static final Logger LOG = LogManager.getLogger();

    // private static final String encrypted = "d2VidGVhbTpydWxlcw==";
    private static final String encrypted = "d2VidGVhbV90ZW1wOnJ1bGVz";

    @Test
    public void testBase64Decode() {
        /**
         * Base64.getUrlDecoder() and Base64.getDecoder() got same result.
         */
        // byte[] base64decodedBytes = Base64.getUrlDecoder().decode(encrypted);
        byte[] base64decodedBytes = Base64.getDecoder().decode(encrypted);

        String decrypted = new String(base64decodedBytes, StandardCharsets.UTF_8);

        LOG.info("Decrypted input '{}' to '{}': ", () -> encrypted, () -> decrypted);
    }

}
