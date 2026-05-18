package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC-258 Add Digits
 *
 * Easy
 *
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 *
 * Example 1:
 * Input: num = 38
 * Output: 2
 * Explanation: The process is
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * Since 2 has only one digit, return it.
 *
 * Example 2:
 * Input: num = 0
 * Output: 0
 *
 * Constraints:
 *     0 <= num <= 2 ^ 31 - 1
 *
 * Follow up: Could you do it without any loop/recursion in O(1) runtime?
 */
@Log4j2
public class AddDigits {

    public static void main(String[] args) {

        final int num = 38;

        AddDigits addDigits = new AddDigits();

        var ret = addDigits.addDigitsBrute(num);
        log.debug("Add Digits: {}", () -> ret);
        log.debug("Add Digits {} OK", () -> "ret");

    }

    /**
     * Luke - Iteration + recursion
     *
     * Runtime: 2 ms Beats 79.33%
     * Memory: 41.9 MB Beats 14.98%
     *
     * Time: O(length of num) + O(length of sum) + ... = O((length of num) ^ 2) = O(length of num). Worst case: 999999
     * Space: O(length of sum) = O(length of num)
     */
    public int addDigitsBrute(int num) {
        if (num >= 10) {
            int sum = 0;
            while (num != 0) {
                sum += num % 10;
                num = num / 10;
            }
            return addDigitsBrute(sum);
        } else {
            return num;
        }
    }

    public int addDigitsLc1(int num) {
        if (num == 0) {
            return 0;
        }
        if (num % 9 == 0) {
            return 9;
        }
        return num % 9;
    }

    public int addDigitsLc2(int num) {
        return num == 0 ? 0 : 1 + (num - 1) % 9;
    }
}
