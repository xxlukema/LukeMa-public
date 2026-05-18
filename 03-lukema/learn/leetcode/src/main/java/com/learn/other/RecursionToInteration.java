package com.learn.other;


import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class RecursionToInteration {

    public static void main(String[] args) {

        log.debug("--- args: {}", () -> args);

        final String str = "Hello World!";

        RecursionToInteration recursionToInteration = new RecursionToInteration();

        /*
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.useDelimiter(System.getProperty("line.separator"));

            // List<Integer> list = new ArrayList<>();
            recursionToInteration.readOddInputIntoList(scanner, list);
            // recursionToInteration.readOddInputIntoListIterative(list);
        }

        log.debug("Odd numbers: {}", () -> list);
        */

        String reverseStringRecursive = recursionToInteration.reverseStringRecursive(str);
        log.debug("New str: {}", reverseStringRecursive);

        String reverseStringIterative = recursionToInteration.reverseStringIterative(str);
        Assertions.assertEquals(reverseStringRecursive, reverseStringIterative);

    }

    public String reverseStringRecursive(String str) {

        if ((str == null) || (str.length() <= 1)) {
            return str;
        } else {
            return str.charAt(str.length() - 1) + reverseStringRecursive(str.substring(0, str.length() - 1));
        }
    }

    public String reverseStringIterative(String str) {

        /**
         * edge condition
         */
        if (str == null || str.length() <= 1) {
            return str;
        }

        Stack<String> stack = new Stack<>();
        stack.push(str);

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            String s = stack.pop();

            if (str.isEmpty()) {
                break;
            }

            sb.append(s.charAt(s.length() - 1));

            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
                stack.push(s);
            }
        }

        return sb.toString();
    }

    public void readOddInputIntoListIterative(final List<Integer> list) {
        System.out.print("Please enter an odd number: ");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.useDelimiter(System.getProperty("line.separator"));

            if (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                try {
                    int in = Integer.valueOf(str);
                    if (in % 2 == 1) {
                        list.add(in);
                    }
                    if (in == -1) {
                        return;
                    }
                } catch (Exception e) {
                    log.error("Not a number: {}", e.getMessage());
                }
            } else {
                log.debug("No more input line");
            }

            scanner.close();

            readOddInputIntoListIterative(list);
        }
    }

    public void readOddInputIntoList(final Scanner scanner, final List<Integer> list) {
        System.out.print("Please enter an odd number: ");

        if (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            try {
                int in = Integer.valueOf(str);

                while (in % 2 == 1) {
                    list.add(in);
                }
                if (in == -1) {
                    return;
                }
            } catch (Exception e) {
                log.error("Not a number: {}", e.getMessage());
            }
        }

        readOddInputIntoList(scanner, list);
    }

    /*
    String str = System.console().readLine();
    try {
    int in = Integer.valueOf(str);

    while (in % 2 == 1) {
        list.add(in);
    }
    if (in == -1) {
        return;
    }
    } catch (Exception e) {
    log.error("Not a number: {}", e.getMessage());
    }
    */

    /*
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
    try {
        String str = reader.readLine();
        int in = Integer.valueOf(str);

        while (in % 2 == 1) {
            list.add(in);
        }
        if (in == -1) {
            return;
        }
    } catch (Exception e) {
        log.error("Not a number: {}", e.getMessage());
    }
    } catch (Exception ee) {
    log.error("Not a number: {}", ee.getMessage());
    }
    */

}
