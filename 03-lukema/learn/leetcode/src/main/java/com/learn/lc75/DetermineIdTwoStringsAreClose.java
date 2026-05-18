package com.learn.lc75;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1657. Determine if Two Strings Are Close
 *
 * Medium
 *
 * Two strings are considered close if you can attain one from the other using the following operations:

    Operation 1: Swap any two existing characters.
        For example, abcde -> aecdb
    Operation 2: Transform every occurrence of one existing character into another existing character, and do the same with the other character.
        For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)

You can use the operations on either string as many times as necessary.

Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.

Example 1:

Input: word1 = "abc", word2 = "bca"
Output: true
Explanation: You can attain word2 from word1 in 2 operations.
Apply Operation 1: "abc" -> "acb"
Apply Operation 1: "acb" -> "bca"

Example 2:

Input: word1 = "a", word2 = "aa"
Output: false
Explanation: It is impossible to attain word2 from word1, or vice versa, in any number of operations.

Example 3:

Input: word1 = "cabbba", word2 = "abbccc"
Output: true
Explanation: You can attain word2 from word1 in 3 operations.
Apply Operation 1: "cabbba" -> "caabbb"
Apply Operation 2: "caabbb" -> "baaccc"
Apply Operation 2: "baaccc" -> "abbccc"

Constraints:

    1 <= word1.length, word2.length <= 10 ^ 5
    word1 and word2 contain only lowercase English letters.
 */

@Log4j2
public class DetermineIdTwoStringsAreClose {

    public static void main(String[] args) {

        DetermineIdTwoStringsAreClose determineIdTwoStringsAreClose = new DetermineIdTwoStringsAreClose();

        // String word1 = "abc", word2 = "bca";
        // boolean expected = true;

        // String word1 = "cabbba", word2 = "abbccc";
        // boolean expected = true;

        String word1 = "abbzzca", word2 = "babzzcz";
        boolean expected = false;

        var ret = determineIdTwoStringsAreClose.closeStrings(word1, word2);
        log.debug("Determine if Two Strings Are Close: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Determine if Two Strings Are Close {} OK", () -> "closeStrings");

    }

    /**
     * Time: O(n * log(n))
     * Space: O(n)
     *
     * Runtime: 15 ms Beats 78.68%
     * Memory: 44.5 MB Beats 75.82%
     */
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int[] arr1 = new int[26];

        for (int i = 0, len = word1.length(); i < len; i++) {
            arr1[word1.charAt(i) - 'a']++;
        }

        int[] arr2 = new int[26];

        for (int i = 0, len = word2.length(); i < len; i++) {
            arr2[word2.charAt(i) - 'a']++;
        }

        /**
         * Ensure both have same set of chars
         */
        for (int i = 0; i < 26; i++) {
            if ((arr1[i] == 0) != (arr2[i] == 0)) {
                return false;
            }
        }

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);

        /*
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            if (arr1[i] > 0) {
                list1.add(arr1[i]);
            }

            if (arr2[i] > 0) {
                list2.add(arr2[i]);
            }
        }

        list1.sort((a, b) -> a - b);
        list2.sort((a, b) -> a - b);

        String str1 = list1.stream().map(String::valueOf).collect(Collectors.joining("-"));
        String str2 = list2.stream().map(String::valueOf).collect(Collectors.joining("-"));

        return str1.equals(str2);
        */
    }
}
