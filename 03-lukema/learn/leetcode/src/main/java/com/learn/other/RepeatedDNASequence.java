package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 187 - Repeated DNA Sequence
 *
 * Medium
 *
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
 *     For example, "ACGAATTCCG" is a DNA sequence.
 * When studying DNA, it is useful to identify repeated sequences within the DNA.
 * Given a string s that represents a DNA sequence, return all the "10-letter-long" sequences (substrings) that occur more than once
 * in a DNA molecule. You may return the answer in any order.
 *
 * Example 1:
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 *
 * Example 2:
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 *
 * Constraints:
 *     1 <= s.length <= 105
 *     s[i] is either 'A', 'C', 'G', or 'T'.
 */
@Log4j2
public class RepeatedDNASequence {

    public static void main(String[] args) {

        // final String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        // final String s = "AAAAAAAAAAAAA";
        final String s = "AAAAAAAAAAA";

        RepeatedDNASequence repeatedDNASequence = new RepeatedDNASequence();

        var findRepeatedDnaSequencesLukeBrute = repeatedDNASequence.findRepeatedDnaSequencesLukeBrute(s);
        log.debug("Repeated DNA Sequence: {}", () -> findRepeatedDnaSequencesLukeBrute);
        log.debug("Repeated DNA Sequence {} OK", () -> "findRepeatedDnaSequencesLukeBrute");

        var findRepeatedDnaSequencesRabinKarp = repeatedDNASequence.findRepeatedDnaSequencesLcRabinKarp(s);
        Assertions.assertEquals(findRepeatedDnaSequencesLukeBrute, findRepeatedDnaSequencesRabinKarp);
        log.debug("Repeated DNA Sequence {} OK", () -> "findRepeatedDnaSequencesRabinKarp");

        var findRepeatedDnaSequencesLcBitShift = repeatedDNASequence.findRepeatedDnaSequencesLcBitShift(s);
        Assertions.assertEquals(findRepeatedDnaSequencesLukeBrute, findRepeatedDnaSequencesLcBitShift);
        log.debug("Repeated DNA Sequence {} OK", () -> "findRepeatedDnaSequencesLcBitShift");

        var findRepeatedDnaSequencesLukeNeetCode = repeatedDNASequence.findRepeatedDnaSequencesLukeNeetCode(s);
        Assertions.assertEquals(findRepeatedDnaSequencesLukeBrute, findRepeatedDnaSequencesLukeNeetCode);
        log.debug("Repeated DNA Sequence {} OK", () -> "findRepeatedDnaSequencesLukeNeetCode");

        var findRepeatedDnaSequencesLukeNeetCodeImproved = repeatedDNASequence.findRepeatedDnaSequencesLukeNeetCodeImproved(s);
        Assertions.assertEquals(findRepeatedDnaSequencesLukeBrute, findRepeatedDnaSequencesLukeNeetCodeImproved);
        log.debug("Repeated DNA Sequence {} OK", () -> "findRepeatedDnaSequencesLukeNeetCodeImproved");

    }

    /**
     * Luke - s.indexOf(sub, i + 1);
     *
     * Time Limit Exceeded
     *
     * Time: O((N - L) * N)
     * Space: O(L)
     */
    public List<String> findRepeatedDnaSequencesLukeBrute(final String s) {
        final List<String> list = new ArrayList<>();

        /**
         * Time: O(N - L) * O(N)
         */
        for (int i = 0, n = s.length(); i < n - 9; i++) {
            String sub = s.substring(i, i + 10);
            if (!list.contains(sub)) {
                /**
                 * Time: O(N)
                 */
                if (s.indexOf(sub, i + 1) > -1) {
                    list.add(sub);
                }
            }
        }

        return list;
    }

    /**
     * Luke - Based on NeetCode
     *
     * Runtime: 32 ms, faster than 73.50% of Java online submissions for Repeated DNA Sequences.
     * Memory Usage: 64.8 MB, less than 7.85% of Java online submissions for Repeated DNA Sequences.
     *
     * Time: O(N - L)
     * Space: O(N)
     */
    public List<String> findRepeatedDnaSequencesLukeNeetCode(final String s) {
        final Set<String> result = new HashSet<>();
        final Set<String> seen = new HashSet<>();

        for (int i = 0, n = s.length(); i < n - 9; i++) {
            String sub = s.substring(i, i + 10);
            if (seen.contains(sub)) {
                result.add(sub);
            } else {
                seen.add(sub);
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * Luke - Based on NeetCode - Improved by converting string to int[]
     *
     * Runtime: 43 ms, faster than 46.12% of Java online submissions for Repeated DNA Sequences.
     * Memory Usage: 52.4 MB, less than 81.04% of Java online submissions for Repeated DNA Sequences.
     *
     * Time: O(N - L)
     * Space: O(N)
     */
    public List<String> findRepeatedDnaSequencesLukeNeetCodeImproved(final String s) {
        final Set<String> result = new HashSet<>();
        final Set<Integer> seen = new HashSet<>();

        final Map<Character, Integer> map = new HashMap<>() {
            {
                put('A', 0);
                put('C', 1);
                put('G', 2);
                put('T', 3);
            }
        };

        final int[] nums = new int[s.length()];
        for (int i = 0, n = s.length(); i < n; i++) {
            nums[i] = map.get(s.charAt(i));
        }

        for (int i = 0; i < nums.length - 9; i++) {
            int hash = 0;
            for (int k = i; k < i + 10; k++) {
                hash += hash * 10 + nums[k];
            }

            if (seen.contains(hash)) {
                result.add(s.substring(i, i + 10));
            } else {
                seen.add(hash);
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * LC - Rabin-Karp
     *
     * Time: O(N - L)
     * Space: O(N - L)
     */
    public List<String> findRepeatedDnaSequencesLcRabinKarp(String s) {
        final int L = 10, n = s.length();
        if (n <= L) {
            return new ArrayList<>();
        }

        // rolling hash parameters: base a
        final int a = 4;
        final int aL = (int) Math.pow(a, L);

        // convert string to array of integers
        Map<Character, Integer> toInt = new HashMap<>() {
            {
                put('A', 0);
                put('C', 1);
                put('G', 2);
                put('T', 3);
            }
        };

        final int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = toInt.get(s.charAt(i));
        }

        int h = 0;
        Set<Integer> seen = new HashSet<>();
        Set<String> output = new HashSet<>();
        // iterate over all sequences of length L
        for (int start = 0; start < n - L + 1; ++start) {
            // compute hash of the current sequence in O(1) time
            if (start != 0) {
                h = h * a - nums[start - 1] * aL + nums[start + L - 1];
            } else {
                // compute hash of the first sequence in O(L) time
                for (int i = 0; i < L; ++i) {
                    h = h * a + nums[i];
                }
            }
            // update output and hashset of seen sequences
            if (seen.contains(h)) {
                output.add(s.substring(start, start + L));
            } else {
                seen.add(h);
            }
        }
        return new ArrayList<>(output);
    }

    public List<String> findRepeatedDnaSequencesLcBitShift(String s) {
        final int L = 10, n = s.length();
        if (n <= L) {
            return new ArrayList<>();
        }

        // convert string to array of integers
        Map<Character, Integer> toInt = new HashMap<>() {
            {
                put('A', 0);
                put('C', 1);
                put('G', 2);
                put('T', 3);
            }
        };
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = toInt.get(s.charAt(i));
        }

        int bitmask = 0;
        Set<Integer> seen = new HashSet<>();
        Set<String> output = new HashSet<>();
        // iterate over all sequences of length L
        for (int start = 0; start < n - L + 1; ++start) {
            // compute bitmask of the current sequence in O(1) time
            if (start != 0) {
                // left shift to free the last 2 bit
                bitmask <<= 2;
                // add a new 2-bits number in the last two bits
                bitmask |= nums[start + L - 1];
                // unset first two bits: 2L-bit and (2L + 1)-bit
                bitmask &= ~(3 << 2 * L);
            } else {
                // compute hash of the first sequence in O(L) time
                for (int i = 0; i < L; ++i) {
                    bitmask <<= 2;
                    bitmask |= nums[i];
                }
            }
            // update output and hashset of seen sequences
            if (seen.contains(bitmask)) {
                output.add(s.substring(start, start + L));
            } else {
                seen.add(bitmask);
            }
        }
        return new ArrayList<>(output);
    }
}
