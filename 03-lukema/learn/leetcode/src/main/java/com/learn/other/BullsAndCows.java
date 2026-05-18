package com.learn.other;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 299 - Bulls and Cows
 *
 * Medium
 *
 * You are playing the Bulls and Cows game with your friend.
 *
 * You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess,
 * you provide a hint with the following info:
 *
 *     The number of "bulls", which are digits in the guess that are in the correct position.
 *     The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position.
 *         Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
 *
 * Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.
 *
 * The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret
 * and guess may contain duplicate digits.
 *
 * Example 1:
 * Input: secret = "1807", guess = "7810"
 * Output: "1A3B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1807"
 *   |
 * "7810"
 * "_ __"
 *
 * Example 2:
 * Input: secret = "1123", guess = "0111"
 * Output: "1A1B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1123"        "1123"
 *   |      or     |
 * "0111"        "0111"
 * "  _ "        "   _"
 *
 * Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged to allow one 1 to be a bull.
 *
 * Constraints:
 *     1 <= secret.length, guess.length <= 1000
 *     secret.length == guess.length
 *     secret and guess consist of digits only.
 */
@Log4j2
public class BullsAndCows {

    public static void main(String[] args) {

        /**
         * Expected: 1A3B
         */
        final String secret = "1807", guess = "7810";

        BullsAndCows bullsAndCows = new BullsAndCows();
        var ret = bullsAndCows.getHint(secret, guess);
        log.debug("Bulls and Cows: {}", () -> ret);
        log.debug("Bulls and Cows {} OK", () -> "ret");

    }

    /**
     * Luke - Two Pass
     *
     * Runtime: 3 ms Beats 97.32%
     * Memory: 40.6 MB Beats 99.71%
     *
     * Time: O(N)
     * Space: O(1)
     */
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;

        final int LEN = secret.length();
        final Map<Character, Integer> map = new HashMap<>();
        final boolean[] matched = new boolean[LEN];

        for (int i = 0; i < LEN; i++) {
            char chSec = secret.charAt(i);
            char chGus = guess.charAt(i);

            if (chSec == chGus) {
                bulls++;
                matched[i] = true;
            } else {
                map.put(chSec, map.getOrDefault(chSec, 0) + 1);
            }
        }

        for (int i = 0; i < LEN; i++) {
            if (matched[i]) {
                continue;
            }

            char chGus = guess.charAt(i);
            if (map.getOrDefault(chGus, 0) > 0) {
                cows++;
                map.put(chGus, map.getOrDefault(chGus, 0) - 1);
            }
        }

        StringBuilder sb = new StringBuilder();
        return sb.append(bulls).append("A").append(cows).append("B").toString();
    }
}
