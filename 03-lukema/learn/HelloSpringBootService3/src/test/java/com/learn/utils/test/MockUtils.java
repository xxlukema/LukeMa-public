package com.learn.utils.test;


import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MockUtils {

    public static MockMultipartFile readFromMockMultipartFile(String filename) {
        // @formatter:off
        try (InputStream is = TestRestTemplate.class.getResourceAsStream(filename);
             FileOutputStream fos = new FileOutputStream("target/invoice-sample-input.pdf")) {
             // @formatter:on
            byte [] bytes = is.readAllBytes() ;
            if (is != null) {
                fos.write(bytes);
            }
            
            /**
             * MockMultipartFile("file", ....) constructor - The first parameter must be "file".
             */
            return new MockMultipartFile("file", filename, MediaType.APPLICATION_PDF_VALUE, bytes);
        } catch (Exception e) {
            log.error("Unable to open file to be uploaded", e);
            return null;
        }
    }

}
