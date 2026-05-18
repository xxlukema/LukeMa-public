package com.learn.java8;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class Base64Test {

    private static final Logger LOG = LogManager.getLogger();

    private static final String text = "TutorialsPoint?java8";

    private static final String encrypted = "d2VidGVhbTpydWxlcw==";

    @Test
    public void testBase64Decode() {
        try {
            /**
             * Base64.getUrlDecoder() and Base64.getDecoder() got same result.
             */
            // byte[] base64decodedBytes = Base64.getUrlDecoder().decode(encrypted);
            byte[] base64decodedBytes = Base64.getDecoder().decode(encrypted);

            String decrypted = new String(base64decodedBytes, "utf-8");

            LOG.info("Decrypted input '{}' to '{}': ", () -> encrypted, () -> decrypted);
        } catch (UnsupportedEncodingException e) {
            LOG.error("Error :" + e.getMessage(), e);
        }
    }

    @Disabled
    @Test
    public void testBase64() {
        try {

            // Encode using basic encoder
            String base64encodedString = Base64.getEncoder().encodeToString(text.getBytes("utf-8"));
            LOG.info("Base64 Encoded String (Basic) :" + base64encodedString);

            // Decode
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);

            LOG.info("Original String: " + new String(base64decodedBytes, "utf-8"));

            base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
            LOG.info("Base64 Encoded String (URL) :" + base64encodedString);

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 10; ++i) {
                stringBuilder.append(UUID.randomUUID().toString());
            }

            byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
            String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
            LOG.info("Base64 Encoded String (MIME) :\n" + mimeEncodedString);

        } catch (UnsupportedEncodingException e) {
            LOG.error("Error :" + e.getMessage(), e);
        }
    }
}
