package com.learn.backtrack;


import java.util.LinkedList;

import lombok.extern.log4j.Log4j2;


/**
 * LC-356 Additive Number
 *
 * Medium
 *
 * An additive number is a string whose digits can form an additive sequence.
 *
 * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in
 * the sequence must be the sum of the preceding two.
 *
 * Given a string containing only digits, return true if it is an additive number or false otherwise.
 *
 * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
 *
 * Example 1:
 * Input: "112358"
 * Output: true
 * Explanation:
 * The digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
 * 1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 *
 * Example 2:
 * Input: "199100199"
 * Output: true
 * Explanation:
 * The additive sequence is: 1, 99, 100, 199.
 * 1 + 99 = 100, 99 + 100 = 199
 *
 * Constraints:
 *     1 <= num.length <= 35
 *     num consists only of digits.
 *
 * Follow up: How would you handle overflow for very large input integers?
 */
@Log4j2
public class AdditiveNumber {

    public static void main(String[] args) {

        // final String num = "199100199";
        // final String num = "112358";
        // final String num = "1023";
        // final String num = "101";
        // final String num = "101";
        // final String num = "000";
        // final String num = "199111992";
        // final String num = "198019823962";
        /**
         * Integer.MAX_VALUE exceded: 2147483641 + 1 = 2147483648
         */
        // final String num = "121474836472147483648";
        final String num = "11111111111011111111111";

        AdditiveNumber additiveNumber = new AdditiveNumber();

        var ret = additiveNumber.isAdditiveNumber(num);
        log.debug("Additive Number: {}", () -> ret);
        log.debug("Additive Number: {}", () -> "ret");
    }

    final int MAX = Integer.MAX_VALUE;
    final int LEN_MAX_INTEGER = String.valueOf(MAX).length() + 5;

    /**
     * Luke - Problem: Test cases exceded Integer.MAX_VALUE.
     *
     * Runtime: 1 ms Beats 92.33%
     * Memory: 40.2 MB Beats 82.61%
     *
     * Time: O(N ^ 2) for first two numbers + O(N) for the rest of the digits
     * Space: O(N) to hold the List and recursion depth
     */
    public boolean isAdditiveNumber(String num) {

        if (num == null) {
            return false;
        }

        final LinkedList<Integer> llist = new LinkedList<>();

        return backtrack(num, 0, 0, 0, llist);
    }

    private boolean backtrack(String num, int curIdx, int firstEndIdx, int secondEndIdx, final LinkedList<Integer> llist) {
        if (llist.isEmpty()) {
            curIdx = 0;
            firstEndIdx = curIdx + 1;
            while (firstEndIdx < num.length() && firstEndIdx - curIdx <= LEN_MAX_INTEGER) {
                String firstStr = num.substring(curIdx, firstEndIdx);
                if (firstStr.startsWith("0") && firstStr.length() > 1) {
                    firstEndIdx++;
                    continue;
                }
                long first = Long.valueOf(firstStr);
                if (first >= MAX / 2 + 1) {
                    return false;
                }
                secondEndIdx = firstEndIdx + 1;
                while (secondEndIdx < num.length() && secondEndIdx - firstEndIdx <= LEN_MAX_INTEGER) {
                    String secondStr = num.substring(firstEndIdx, secondEndIdx);
                    if (secondStr.startsWith("0") && secondStr.length() > 1) {
                        break;
                    }
                    long second = Long.valueOf(secondStr);
                    /*
                    if (second > MAX) {
                        break;
                    }
                    */

                    long sum = first + second;
                    /*
                    if (sum > MAX) {
                        break;
                    }
                    */

                    String sumStr = String.valueOf(sum);

                    if (num.indexOf(sumStr, secondEndIdx) != secondEndIdx) {
                        secondEndIdx++;
                        continue;
                    }

                    if (secondEndIdx + sumStr.length() == num.length()) {
                        return true;
                    }

                    llist.add((int) first);
                    llist.add((int) second);
                    llist.add((int) sum);

                    // log.debug("========== found one {} =================", llist);

                    boolean isValid = backtrack(num, firstEndIdx, secondEndIdx, secondEndIdx + sumStr.length(), llist);

                    if (isValid) {
                        return true;
                    } else {
                        llist.removeLast();
                        llist.removeLast();
                        llist.removeLast();
                        secondEndIdx++;
                    }
                }

                firstEndIdx++;
            }

            return false;
        } else {

            // log.debug("============  again {}, num: {} ===============", llist, num);

            long sum = llist.get(llist.size() - 2) + llist.get(llist.size() - 1);
            String sumStr = String.valueOf(sum);

            if (num.indexOf(sumStr, secondEndIdx) != secondEndIdx) {
                return false;
            }

            if (secondEndIdx + sumStr.length() == num.length()) {
                // log.debug("============  finish {}, num: {} ===============", llist, num);

                return true;
            }

            int thirdEndIdx = secondEndIdx + sumStr.length();

            llist.add((int) sum);

            return backtrack(num, firstEndIdx, secondEndIdx, thirdEndIdx, llist);
        }
    }

}
