package com.learn.test.other;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.baeldung.com/java-scanner
 */
@Log4j2
public class ScannerTest {

    @Test
    public void testScanner() {

        log.debug("Enter an integer > ");

        String input = "Hello world 123";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        /**
         * AutoClose
         */
        try (Scanner scanner = new Scanner(System.in);) {
            // int i = scanner.nextInt();
            // String line = scanner.nextInt();

            // scanner.useDelimiter("o");

            while (scanner.hasNext()) {
                /**
                 * scanner.next() - is for reading string
                 */
                String str = scanner.next();
                log.debug("You entered: ", () -> str);
                log.debug("You entered: " + str);
                // System.out.println("You entered: " + scanner.next());
                if (str.equals("-1")) {
                    break;
                }
            }

            log.debug(() -> "Test stdIn complete.");

            /**
             * call this before AutoClose get triggered.
             */
            System.setIn(stdin);

        } catch (Exception e) {
            log.error("Scanner Error: ", e);
        }

    }

}
