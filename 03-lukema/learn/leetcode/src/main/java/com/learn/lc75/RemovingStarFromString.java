package com.learn.lc75;


import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 2390. Removing Stars From a String
 *
 * Medium
 *
 * You are given a string s, which contains stars *.

In one operation, you can:

    Choose a star in s.
    Remove the closest non-star character to its left, as well as remove the star itself.

Return the string after all stars have been removed.

Note:

    The input will be generated such that the operation is always possible.
    It can be shown that the resulting string will always be unique.

Example 1:

Input: s = "leet**cod*e"
Output: "lecoe"
Explanation: Performing the removals from left to right:
- The closest character to the 1st star is 't' in "leet**cod*e". s becomes "lee*cod*e".
- The closest character to the 2nd star is 'e' in "lee*cod*e". s becomes "lecod*e".
- The closest character to the 3rd star is 'd' in "lecod*e". s becomes "lecoe".
There are no more stars, so we return "lecoe".

Example 2:

Input: s = "erase*****"
Output: ""
Explanation: The entire string is removed, so we return an empty string.

Constraints:

    1 <= s.length <= 105
    s consists of lowercase English letters and stars *.
    The operation above can be performed on s.
 */

@Log4j2
public class RemovingStarFromString {

    public static void main(String[] args) {

        RemovingStarFromString removingStarFromString = new RemovingStarFromString();

        String s = "leet**cod*e";
        String expected = "lecoe";

        var ret = removingStarFromString.removeStars(s);

        log.debug("Removing Stars From a String: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Removing Stars From a String {} OK", () -> "removeStars");

        var ret2 = removingStarFromString.removeStarsLc(s);

        log.debug("Removing Stars From a String: {}", () -> ret2);
        Assertions.assertEquals(expected, ret2);
        log.debug("Removing Stars From a String {} OK", () -> "removeStarsLc");

    }

    /**
     * Luke - Counter
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 146 ms Beats 66.11%
     * Memory: 44.6 MB Beats 89.6%
     */
    public String removeStars(String s) {

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        for (int len = s.length(), i = len - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '*') {
                counter++;
            } else {
                if (counter > 0) {
                    counter--;
                } else {
                    sb.insert(0, ch);
                }
            }
        }

        return sb.toString();
    }

    /**
     * LC - Stack
     *
     * Time: O(n)
     * Space: O(n)
     *
     * Runtime: 898 ms Beats 23.51%
     * Memory: 44.8 MB Beats 77.63%
     */
    public String removeStarsLc(String s) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '*') {
                stack.pop();
            } else {
                stack.push(ch);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            // sb.append(stack.remove(0));
            sb.insert(0, stack.pop());
        }

        return sb.toString();
    }
}
