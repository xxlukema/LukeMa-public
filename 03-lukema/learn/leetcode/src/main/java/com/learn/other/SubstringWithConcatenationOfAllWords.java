package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 30 - Substring With Concatenation Of All Words
 *
 * Hard
 *
 * You are given a string s and an array of strings words. All the strings of words are of the same length.
 *
 * A concatenated substring in s is a substring that contains all the strings of any permutation of words concatenated.
 *
 *     For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated
 *     strings. "acdbef" is not a concatenated substring because it is not the concatenation of any permutation of words.
 *
 * Return the starting indices of all the concatenated substrings in s. You can return the answer in any order.
 *
 * Example 1:
 * Input: s = "barfoothefoobarman", words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Since words.length == 2 and words[i].length == 3, the concatenated substring has to be of length 6.
 * The substring starting at 0 is "barfoo". It is the concatenation of ["bar","foo"] which is a permutation of words.
 * The substring starting at 9 is "foobar". It is the concatenation of ["foo","bar"] which is a permutation of words.
 * The output order does not matter. Returning [9,0] is fine too.
 *
 * Example 2:
 * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * Output: []
 * Explanation: Since words.length == 4 and words[i].length == 4, the concatenated substring has to be of length 16.
 * There is no substring of length 16 is s that is equal to the concatenation of any permutation of words.
 * We return an empty array.
 *
 * Example 3:
 * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * Output: [6,9,12]
 * Explanation: Since words.length == 3 and words[i].length == 3, the concatenated substring has to be of length 9.
 * The substring starting at 6 is "foobarthe". It is the concatenation of ["foo","bar","the"] which is a permutation of words.
 * The substring starting at 9 is "barthefoo". It is the concatenation of ["bar","the","foo"] which is a permutation of words.
 * The substring starting at 12 is "thefoobar". It is the concatenation of ["the","foo","bar"] which is a permutation of words.
 *
 * Constraints:
 *     1 <= s.length <= 104
 *     1 <= words.length <= 5000
 *     1 <= words[i].length <= 30
 *     s and words[i] consist of lowercase English letters.
 */
@Log4j2
public class SubstringWithConcatenationOfAllWords {

    public static void main(String[] args) {

        /**
         * Expected: [6,9,12]
         */
        // final String s = "barfoofoobarthefoobarman";
        // final String[] words = { "bar", "foo", "the" };

        /**
         * Expected: [8]
         */
        final String s = "wordgoodgoodgoodbestword";
        final String[] words = { "word", "good", "best", "good" };

        SubstringWithConcatenationOfAllWords substringWithConcatenationOfAllWords = new SubstringWithConcatenationOfAllWords();

        var ret = substringWithConcatenationOfAllWords.findSubstring(s, words);
        log.debug("Substring With Concatenation Of All Words: {}", () -> ret);
        log.debug("Substring With Concatenation Of All Words {} OK", () -> "ret");
    }

    /**
     * Luke - HashMap
     *
     * Runtime: 603 ms Beats 7.33%
     * Memory: 117.7 MB Beats 17.87%
     *
     * Time: O(s.length() - LEN * words.length) * O(words.length)
     * Space: O(words.length * word.length())
     */
    public List<Integer> findSubstring(String s, String[] words) {
        final List<Integer> list = new ArrayList<>();

        final int LEN = words[0].length();

        if (s == null || s.length() < words.length * LEN) {
            return list;
        }

        for (int i = 0, n = s.length() - LEN * words.length; i <= n; i++) {
            int idx = findMatch(s, i, words, LEN);
            if (idx == -1) {
                continue;
            } else {
                list.add(idx);
            }
        }

        return list;
    }

    /**
     *
     * @param s
     * @param start index
     * @param words All words
     * @return -1 if not found.
     */
    int findMatch(final String s, final int start, final String[] words, final int LEN) {
        final Map<String, Integer> map = new HashMap<>();

        for (String str : words) {
            if (map.containsKey(str)) {
                int count = map.get(str);
                map.put(str, count + 1);
            } else {
                map.put(str, 1);
            }
        }

        int pos = start;
        /**
         * Time: O(words.length)
         */
        while (!map.isEmpty()) {
            String str = s.substring(pos, pos + LEN);

            if (map.containsKey(str)) {
                int count = map.get(str);
                if (count > 1) {
                    /**
                     * Time: O(1)
                     */
                    map.put(str, count - 1);
                } else {
                    /**
                     * Time: O(1)
                     */
                    map.remove(str);
                }
                pos += LEN;
            } else {
                return -1;
            }
        }

        return start;
    }
}
