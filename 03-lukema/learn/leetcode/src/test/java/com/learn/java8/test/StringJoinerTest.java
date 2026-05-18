package com.learn.java8.test;


import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StringJoinerTest {

    @Test
    public void testStringJoiner1() {

        StringJoiner sj = new StringJoiner(",", "[", "]"); // passing comma(,) and square-brackets as delimiter

        // Adding values to StringJoiner
        sj.add("Rahul");
        sj.add("Raju");

        // Creating StringJoiner with :(colon) delimiter
        StringJoiner sj2 = new StringJoiner(":", "[", "]"); // passing colon(:) and square-brackets as delimiter

        // Adding values to StringJoiner
        sj2.add("Peter");
        sj2.add("Raheem");

        // Merging two StringJoiner
        StringJoiner merge = sj.merge(sj2);
        log.debug(() -> merge);

    }

    @Test
    public void testStringJoiner2() {
        StringJoiner sj = new StringJoiner(","); // passing comma(,) as delimiter

        // Prints nothing because it is empty
        log.debug(() -> sj);

        // We can set default empty value.
        sj.setEmptyValue("It is empty");
        log.debug(() -> sj);

        // Adding values to StringJoiner
        sj.add("Rahul");
        sj.add("Raju");
        log.debug(() -> sj);

        // Returns length of StringJoiner
        int length = sj.length();
        log.debug(() -> "Length: " + length);

        // Returns StringJoiner as String type
        String str = sj.toString();
        log.debug(() -> str);

        // Now, we can apply String methods on it
        char ch = str.charAt(3);
        log.debug(() -> "Character at index 3: " + ch);

        // Adding one more element
        sj.add("Sorabh");
        log.debug(() -> sj);

        // Returns length
        int newLength = sj.length();
        log.debug(() -> "New Length: " + newLength);
    }
}
