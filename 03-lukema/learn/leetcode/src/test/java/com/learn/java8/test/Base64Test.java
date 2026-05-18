package com.learn.java8.test;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * https://base64.guru/learn/base64-characters
 */
@Log4j2
public class Base64Test {

    @Test
    public void testBase64EncodeDecode() {
        try {
            String original = "TutorialsPoint?java8";

            // Encode using basic encoder
            String base64encodedString = Base64.getEncoder().encodeToString(original.getBytes("utf-8"));

            log.debug("Base64 Encoded String (Basic) :" + base64encodedString);

            // Decode
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);

            log.debug("Original String: " + new String(base64decodedBytes, "utf-8"));

            base64encodedString = Base64.getUrlEncoder().encodeToString(original.getBytes("utf-8"));

            log.debug("Base64 Encoded String (URL) :" + base64encodedString);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 10; ++i) {
                sb.append(UUID.randomUUID().toString());
            }
            sb.append("hi");

            byte[] mimeBytes = sb.toString().getBytes("utf-8");
            String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
            log.debug("Base64 Encoded String (MIME) :\n" + mimeEncodedString);

        } catch (UnsupportedEncodingException e) {
            log.debug("Error :" + e.getMessage());
        }
    }
}
