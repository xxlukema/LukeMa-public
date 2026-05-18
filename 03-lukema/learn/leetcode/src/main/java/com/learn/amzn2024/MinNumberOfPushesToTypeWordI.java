package com.learn.amzn2024;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC-3014 Mumimum Number of Pushes to Type Word I
 *
 * Easy
 *
 * You are given a string word containing distinct lowercase English letters.

Telephone keypads have keys mapped with distinct collections of lowercase English letters, which can be used to form words by pushing them. For example, the key 2 is mapped with ["a","b","c"], we need to push the key one time to type "a", two times to type "b", and three times to type "c" .

It is allowed to remap the keys numbered 2 to 9 to distinct collections of letters. The keys can be remapped to any amount of letters, but each letter must be mapped to exactly one key. You need to find the minimum number of times the keys will be pushed to type the string word.

Return the minimum number of pushes needed to type word after remapping the keys.

An example mapping of letters to keys on a telephone keypad is given below. Note that 1, *, #, and 0 do not map to any letters.



Example 1:

Input: word = "abcde"
Output: 5
Explanation: The remapped keypad given in the image provides the minimum cost.
"a" -> one push on key 2
"b" -> one push on key 3
"c" -> one push on key 4
"d" -> one push on key 5
"e" -> one push on key 6
Total cost is 1 + 1 + 1 + 1 + 1 = 5.
It can be shown that no other mapping can provide a lower cost.

Example 2:

Input: word = "xycdefghij"
Output: 12
Explanation: The remapped keypad given in the image provides the minimum cost.
"x" -> one push on key 2
"y" -> two pushes on key 2
"c" -> one push on key 3
"d" -> two pushes on key 3
"e" -> one push on key 4
"f" -> one push on key 5
"g" -> one push on key 6
"h" -> one push on key 7
"i" -> one push on key 8
"j" -> one push on key 9
Total cost is 1 + 2 + 1 + 2 + 1 + 1 + 1 + 1 + 1 + 1 = 12.
It can be shown that no other mapping can provide a lower cost.



Constraints:

    1 <= word.length <= 26
    word consists of lowercase English letters.
    All letters in word are distinct.

 *
 */

@Log4j2
public class MinNumberOfPushesToTypeWordI {

    /**
     * Runtime: beats 100%
     * Memory: beats 54%
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int minimumPushes(String word) {
        /**
         * 2 - 9 (8 buckets)
         * evenly distribute
         * distinct letters in word. no need to prioritize letters
         */

        int cycles = word.length() / 8;
        int rem = word.length() % 8;

        switch (cycles) {
            case 0:
                return rem;
            case 1:
                return 8 + rem * 2;
            case 2:
                return 8 + 8 * 2 + rem * 3;
            case 3:
                return 8 + 8 * 2 + 8 * 3 + rem * 4;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        MinNumberOfPushesToTypeWordI minNumberOfPushesToTypeWordI = new MinNumberOfPushesToTypeWordI();

        // int[] nums = { 1, 2, 3, 4, 5 };
        int[] nums = { 10, 100 };

        var ret = minNumberOfPushesToTypeWordI.maximumStrongPairXor(nums);

        log.debug("var = {}", () -> ret);
    }

    public int maximumStrongPairXor(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int k = i; k < nums.length; k++) {
                if (isStrongPair(nums[i], nums[k])) {
                    max = Math.max(max, nums[i] ^ nums[k]);
                }
            }
        }

        return max;
    }

    boolean isStrongPair(int a, int b) {
        return Math.abs(a - b) <= Math.min(a, b);
    }

    public String shortestBeautifulSubstring(String s, int k) {

        // 1. find all beautiful strings[]
        // two pointers
        List<String> list = new ArrayList<>();

        int count = 0;
        char[] chs = s.toCharArray();
        int pos = 0;

        while (count < k && pos < s.length()) {
            if (chs[pos++] == '1') {
                count++;
            }
        }

        if (pos == s.length() && count < k) {
            return "";
        }

        list.add(s.substring(0, pos));

        int i = 0;

        while (i < s.length() - k && pos < s.length()) {
            if (chs[++i] == '0') {
                continue;
            } else {
                while (pos < s.length()) {
                    if (chs[pos++] == '0') {
                        continue;
                    } else {
                        list.add(s.substring(i, pos + 1));
                    }
                }
            }
        }

        // 2. find the smallest one
        return list.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList()).get(0);
    }
}
