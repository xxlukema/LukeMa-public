package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC-273-IntegerToEnglishWords
 *
 * Hard
 *
 * Convert a non-negative integer num to its English words representation.
 *
 * Example 1:
 * Input: num = 123
 * Output: "One Hundred Twenty Three"
 *
 * Example 2:
 * Input: num = 12,345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 *
 * Example 3:
 * Input: num = 1,234,567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 *
 * Constraints:
 *     0 <= num <= 2 ^ 31 - 1
 */
@Log4j2
public class IntegerToEnglishWords {

    public static void main(String[] args) {

        // final int num = 323;
        // final int num = 12_345;
        // final int num = 1_234_567;
        final int num = 100;

        IntegerToEnglishWords integerToEnglishWords = new IntegerToEnglishWords();

        var ret = integerToEnglishWords.numberToWords(num);
        log.debug("Integer to English Words: {}", () -> ret);
        log.debug("Integer to English Words {} OK", () -> "ret");

    }

    /**
     * Luke - Brute
     *
     * Runtime: 8 ms Beats 74.92%
     * Memory: 42.7 MB Beats 56.69%
     *
     * Time: O(# of digits)
     * Space: O(1)
     */
    public String numberToWords(int num) {
        /**
         * 0. edge cases
         */
        if (num == 0) {
            return "Zero";
        }

        /**
         * 1. build dictionary
         */
        final String[] groups = { "Billion", "Million", "Thousand" };

        final Map<Integer, String> words = new HashMap<>();
        words.put(0, "zero");
        words.put(1, "one");
        words.put(2, "two");
        words.put(3, "three");
        words.put(4, "four");
        words.put(5, "five");
        words.put(6, "six");
        words.put(7, "seven");
        words.put(8, "eight");
        words.put(9, "nine");
        words.put(10, "ten");
        words.put(11, "eleven");
        words.put(12, "twelve");
        words.put(13, "thirteen");
        words.put(14, "fourteen");
        words.put(15, "fifteen");
        words.put(16, "sixteen");
        words.put(17, "seventeen");
        words.put(18, "eighteen");
        words.put(19, "nineteen");
        words.put(20, "twenty");
        words.put(30, "thirty");
        words.put(40, "forty");
        words.put(50, "fifty");
        words.put(60, "sixty");
        words.put(70, "seventy");
        words.put(80, "eighty");
        words.put(90, "ninety");

        words.keySet().forEach(key -> {
            String value = words.get(key);
            value = value.substring(0, 1).toUpperCase() + value.substring(1);
            words.put(key, value);
        });

        /**
         * 2. group the integer
         */
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < groups.length; i++) {
            int dividend = (int) Math.pow(1_000, 3 - i);

            int quotiant = num / dividend;
            int rem = num % dividend;

            num = rem;

            if (quotiant > 0) {
                String str = groupToWords(quotiant, words);
                if (sb.length() > 0) {
                    preFillSpace(sb);
                }

                sb.append(str).append(" ").append(groups[i]);
            }
        }

        if (num > 0) {
            String str = groupToWords(num, words);
            if (str.length() > 0) {
                preFillSpace(sb);
                sb.append(str);
            }
        }

        return sb.toString();
    }

    String groupToWords(final int n, final Map<Integer, String> words) {
        final StringBuilder sb = new StringBuilder();
        int quotiant = n / 100;
        int rem = n % 100;

        if (quotiant > 0) {
            sb.append(words.get(quotiant)).append(" Hundred");
        }

        if (rem <= 20) {
            if (rem != 0) {
                preFillSpace(sb);
                sb.append(words.get(rem));
            }
        } else {
            quotiant = rem / 10 * 10;
            rem = rem % 10;

            if (quotiant > 0) {
                preFillSpace(sb);
                sb.append(words.get(quotiant));
            }

            if (rem > 0) {
                preFillSpace(sb);
                sb.append(words.get(rem));
            }

        }

        return sb.toString();
    }

    void preFillSpace(final StringBuilder sb) {
        if (sb.length() > 0) {
            sb.append(" ");
        }
    }
}
