package com.learn.other;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 151 - Reverse Wrods In String
 *
 * Medium
 *
 * Given an input string s, reverse the order of the words.
 * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
 * Return a string of the words in reverse order concatenated by a single space.
 *
 * Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single
 * space separating the words. Do not include any extra spaces.
 *
 * Example 1:
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 *
 * Example 2:
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 *
 * Example 3:
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 *
 * Constraints:
 *     1 <= s.length <= 104
 *     s contains English letters (upper-case and lower-case), digits, and spaces ' '.
 *     There is at least one word in s.
 *
 * Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?
 */
@Log4j2
public class ReverseWordsInString {

    public static void main(String[] args) {

        final String s = "  hello      world     ";

        ReverseWordsInString reverseWordsInString = new ReverseWordsInString();

        String reverseWordsLukeIterative = reverseWordsInString.reverseWordsLukeIterative(s);
        log.debug("Reverse words in string:{}:", () -> reverseWordsLukeIterative);
        log.debug("Reverse words in string {} OK", () -> "reverseWordsLukeIterative");

        String reverseWordsLcBuiltIn = reverseWordsInString.reverseWordsLcBuiltIn(s);
        Assertions.assertEquals(reverseWordsLukeIterative, reverseWordsLcBuiltIn);
        log.debug("Reverse words in string {} OK", () -> "reverseWordsLcBuiltIn");
    }

    /**
     * Luke - Iterative
     * 
     * (1) "Character.isWhitespace(ch)" vs "Character.isSpaceChar(ch)"
     * (2) This does not remove leading spaces:
     *     int idx = 0;
     *     while (idx < sb.length() && Character.isWhitespace(sb.charAt(idx))) {
     *         sb.deleteCharAt(idx++);
     *     }
     * (3) This does remove leading spaces:
     *     while (Character.isWhitespace(sb.charAt(0))) {
     *         sb.deleteCharAt(0);
     *     }
     * 
     * Runtime: 21 ms, faster than 29.59% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 48.5 MB, less than 33.87% of Java online submissions for Reverse Words in a String.
     * 
     * Time: O(N)
     * Space: O(1) <--- To make space complexity O(1), s must be mutable. Converting s to sb to make it mutable. That mutable sb is the input. 
     *                  Therefore, as "challenged", working with that sb space complexity is O(1).
     */
    public String reverseWordsLukeIterative(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s);

        /**
         * 1. Remove leading spaces.
         */
        while (Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }

        /**
         * 2. Reverse.
         */
        int left = 0;
        int right = sb.length() - 1;
        while (left <= right) {
            char chTmp = sb.charAt(left);
            sb.replace(left, left + 1, String.valueOf(sb.charAt(right)));
            sb.replace(right, right + 1, String.valueOf(chTmp));
            left++;
            right--;
        }

        /**
         * 3. Remove leading spaces.
         */
        while (Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }

        /**
         * 4. Normalize.
         */
        int idx = 0;
        boolean isPreSpace = false;
        while (idx < sb.length()) {
            if (Character.isWhitespace(sb.charAt(idx))) {
                if (isPreSpace) {
                    sb.deleteCharAt(idx);
                } else {
                    isPreSpace = true;
                    idx++;
                }
            } else {
                isPreSpace = false;
                idx++;
            }
        }

        /**
         * 5. Reverse each word
         */
        left = 0;
        while (true) {
            right = left + 1;
            while (right < sb.length() && !Character.isWhitespace(sb.charAt(right))) {
                right++;
            }

            if (right < sb.length()) {
                int curr = right + 1;
                right = right - 1;
                while (left <= right) {
                    char tmp = sb.charAt(left);
                    sb.replace(left, left + 1, String.valueOf(sb.charAt(right)));
                    sb.replace(right, right + 1, String.valueOf(tmp));
                    left++;
                    right--;
                }
                left = curr;
            } else {
                right--;

                while (left <= right) {
                    char tmp = sb.charAt(left);
                    sb.replace(left, left + 1, String.valueOf(sb.charAt(right)));
                    sb.replace(right, right + 1, String.valueOf(tmp));
                    left++;
                    right--;
                }
                break;
            }
        }

        return sb.toString();
    }

    /**
     * LC - Built-In Utils
     * 
     * (1) "split by multiple spaces": Use 's.split("\\s+")'. Do not use 's.split(" ")'.
     * (2) Use 'Arrays.asList(s.split("\\s+"))'. Do not use 'List.of(s.split(" "))' because this list is immutable.
     * (3) Learn to use 'String.join(" ", list)', as well as 'list.stream().collect(Collectors.joining(" "))'
     * 
     * With 'String.join(" ", list)':
     * Runtime: 11 ms, faster than 57.43% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 43.4 MB, less than 70.69% of Java online submissions for Reverse Words in a String.
     * 
     * With 'list.stream().collect(Collectors.joining(" "))':
     * Runtime: 10 ms, faster than 62.23% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 43.6 MB, less than 64.51% of Java online submissions for Reverse Words in a String.
     * 
     * Time: O(N)
     * Space: O(N)
     */
    public String reverseWordsLcBuiltIn(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        s = s.trim();

        /**
         * split by multiple spaces
         */
        // List<String> list = List.of(s.split(" ")); /** not this */
        // List<String> list = Arrays.asList(s.split(" +")); /** not this */
        List<String> list = Arrays.asList(s.split("\\s+"));
        Collections.reverse(list);
        // return list.stream().collect(Collectors.joining(" "));
        return String.join(" ", list);
    }
}
