package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class AddOne {

    public static void main(String[] args) {

        // int[] digits = { 4, 3, 2, 1 };
        int[] digits = { 9 };

        AddOne addOne = new AddOne();

        var ret = addOne.plusOne(digits);

        log.debug("Add 1 Luke: {}", () -> ret);

    }

    /**
     * Runtime: 1 ms, faster than 28.79% of Java online submissions for Plus One.
     * Memory Usage: 42.4 MB, less than 53.26% of Java online submissions for Plus One.
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public int[] plusOne(int[] digits) {

        int carr = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = carr + digits[i];
            digits[i] = sum % 10;
            carr = sum / 10;
            if (carr == 0) {
                return digits;
            }
        }

        if (carr == 0) {
            return digits;
        } else {
            int[] ret = new int[digits.length + 1];
            ret[0] = carr;
            for (int i = 0; i < digits.length; i++) {
                ret[i + 1] = digits[i];
            }
            return ret;
        }
    }
}
