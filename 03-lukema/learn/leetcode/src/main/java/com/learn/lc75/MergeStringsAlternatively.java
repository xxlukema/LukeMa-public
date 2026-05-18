package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 1768 Merge Strings Alternately
 *
 * Easy
 *
 * You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1. If a string is longer than the other, append the additional letters onto the end of the merged string.

Return the merged string.



Example 1:

Input: word1 = "abc", word2 = "pqr"
Output: "apbqcr"
Explanation: The merged string will be merged as so:
word1:  a   b   c
word2:    p   q   r
merged: a p b q c r

Example 2:

Input: word1 = "ab", word2 = "pqrs"
Output: "apbqrs"
Explanation: Notice that as word2 is longer, "rs" is appended to the end.
word1:  a   b
word2:    p   q   r   s
merged: a p b q   r   s

Example 3:

Input: word1 = "abcd", word2 = "pq"
Output: "apbqcd"
Explanation: Notice that as word1 is longer, "cd" is appended to the end.
word1:  a   b   c   d
word2:    p   q
merged: a p b q c   d



Constraints:

    1 <= word1.length, word2.length <= 100
    word1 and word2 consist of lowercase English letters.

 */

 @Log4j2
public class MergeStringsAlternatively {

    public static void main(String[] args) {

        MergeStringsAlternatively mergeStringsAlternatively = new MergeStringsAlternatively();

        String word1 = "wwee";
        String word2 = "abdc";

        var ret = mergeStringsAlternatively.mergeAlternately(word1, word2);
        Assertions.assertEquals("wawbedec", ret);

        log.debug("Merge Strings Alternately {} OK", () -> "mergeAlternately");

    }

    /**
     * Time: O(len1 + len2)
     * Space: O(1)
     *
     * Runtime: 1 ms Beats 77.86%
     * Memory: 40.7 MB Beats 81.99%
     */
    public String mergeAlternately(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (i < len1 || i < len2) {
            if (i < len1) {
                sb.append(word1.charAt(i));
            }

            if (i < len2) {
                sb.append(word2.charAt(i));
            }

            i++;
        }

        return sb.toString();
    }

}
