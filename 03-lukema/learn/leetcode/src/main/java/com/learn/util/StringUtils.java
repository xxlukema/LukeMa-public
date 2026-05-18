package com.learn.util;


public class StringUtils {

    /**
     * There is `Character.isDigit(c)` to check an individual char. There is no built in JDK utils to check that.
     *
     * Assertions.assertTrue(StringUtils.isNumeric("22331"));
     * Assertions.assertFalse(StringUtils.isNumeric("-22331"));
     * Assertions.assertFalse(StringUtils.isNumeric("222www331"));
     */
    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    /**
     * There is `Character.isDigit(c)` to check an individual char. There is no built in JDK utils to check that.
     *
     * Add this function for reminiscense only.
     */
    public static boolean isNumeric(final char ch) {
        return Character.isDigit(ch);
    }

    /**
     * There is `Character.isDigit(c)` to check an individual char. There is no built in JDK utils to check that.
     *
     * Add this function for reminiscense only.
     */
    public static boolean isNumeric(final int ch) {
        return Character.isDigit(ch);
    }

}
