package com.learn.other;


import java.util.Scanner;

import lombok.extern.log4j.Log4j2;


/**
 * BufferedReader when we want to read the input into lines
 * Scanner to read the input into tokens
 *
 * scanner.skip(".e.lo");
 * String result = scanner.findInLine("wo..d");
 * scanner.useDelimiter("o");
 *
 */
@Log4j2
public class MyScanner {

    public static void main(String[] args) {

        log.debug("Enter an integer > ");

        /**
         * AutoClose
         */
        try (Scanner scanner = new Scanner(System.in);) {

            // scanner.useDelimiter("o");

            // int i = scanner.nextInt();
            // String line = scanner.nextInt();

            /**
             * while (scanner.hasNextLine()) {
             */
            while (scanner.hasNext()) {
                /**
                 * String str = scanner.nextLine();
                 */
                String str = scanner.next();
                log.debug("You entered: ", () -> str);
                log.debug("You entered: " + str);
                // System.out.println("You entered: " + scanner.next());
                if (str.equals("-1")) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Scanner Error: ", e);
        }

    }
}
