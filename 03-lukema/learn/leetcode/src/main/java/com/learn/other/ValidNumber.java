package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class ValidNumber {

    public static void main(String[] args) {

        /**
         * "e", "0e"
         */
        String str = "e";

        ValidNumber validNumber = new ValidNumber();

        var ret = validNumber.isNumberLuke(str);

        log.info("isNumber luke: {}", () -> ret);

    }

    /**
     * Runtime: 7 ms, faster than 21.67% of Java online submissions for Valid Number.
     * Memory Usage: 43.3 MB, less than 38.39% of Java online submissions for Valid Number.
     *
     * Time: O(n)
     * Space: O(n)
     *
     */
    public boolean isNumberLuke(String str) {

        str = str.toLowerCase();
        String[] fields = str.split("e");

        if (fields.length > 2 || fields.length == 1) {
            return false;
        }

        if (str.endsWith("e")) {
            return false;
        }

        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            if (!Character.isDigit(ch) && !(ch == '-' || ch == '+' || ch == 'e' || ch == '.')) {
                return false;
            }
        }

        if (fields.length == 2) {
            if (fields[1].lastIndexOf('-') > 0 || fields[1].lastIndexOf('+') > 0 || fields[1].indexOf('.') >= 0) {
                return false;
            }

            if (!hasNumber(fields[1])) {
                return false;
            }
        }

        if (fields[0].lastIndexOf('-') > 0 || fields[0].lastIndexOf('+') > 0 || (fields[0].indexOf('.') != fields[0].lastIndexOf('.'))) {
            return false;
        }

        return hasNumber(fields[0]);
    }

    boolean hasNumber(String str) {
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                return true;
            }
        }

        return false;
    }
}
