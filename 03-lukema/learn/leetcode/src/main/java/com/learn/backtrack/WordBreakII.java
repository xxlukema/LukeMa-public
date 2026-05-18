package com.learn.backtrack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC -140 - Word Break II
 *
 * Hard
 *
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * Return all such possible sentences in any order.
 *
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 *
 * Example 1:
 * Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * Output: ["cats and dog","cat sand dog"]
 *
 * Example 2:
 * Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: []
 *
 * Constraints:
 *     1 <= s.length <= 20
 *     1 <= wordDict.length <= 1000
 *     1 <= wordDict[i].length <= 10
 *     s and wordDict[i] consist of only lowercase English letters.
 *     All the strings of wordDict are unique.
 */
@Log4j2
public class WordBreakII {

    public static void main(String[] args) {

        /*
        final String s = "catsanddog";
        final String[] dict = { "cat", "cats", "and", "sand", "dog" };
        */

        /*
        final String s = "catsandog";
        final String[] dict = { "cats", "dog", "sand", "and", "cat" };
        */

        /*
        final String s = "abcd";
        final String[] dict = { "a", "abc", "b", "cd" };
        */

        final String s = "aaaaaaaa";
        final String[] dict = { "aaaa", "aaa", "aa" };

        /*
        final String s = "bb";
        final String[] dict = { "a", "b", "bbb", "bbbb" };
        */

        /*
        final String s = "aaaaaaa";
        final String[] dict = { "aaaa", "aaa" };
        */

        /*
        final String s = "catskicatcats";
        final String[] dict = { "cats", "cat", "dog", "ski" };
        */

        /*
        final String s = "applepenapple";
        final String[] dict = { "apple", "pen" };
        */

        final List<String> wordDict = List.of(dict);

        WordBreakII wordBreakII = new WordBreakII();

        List<String> wordBreakLukeBacktrackBrute = wordBreakII.wordBreakLukeBacktrackBrute(s, wordDict);
        log.debug("Word Break II: {}", () -> wordBreakLukeBacktrackBrute);
        log.debug("Work Break II {} OK", () -> "wordBreakLukeBacktrackBrute");

        List<String> wordBreakLukeBacktrackMemo = wordBreakII.wordBreakLukeBacktrackMemo(s, wordDict);
        Assertions.assertEquals(wordBreakLukeBacktrackBrute, wordBreakLukeBacktrackMemo);
        log.debug("Work Break II {} OK", () -> "wordBreakLukeBacktrackMemo");
    }

    /**
     * Luke - memo - DP Top-Down
     *
     * Runtime: 8 ms, faster than 33.21% of Java online submissions for Word Break II.
     * Memory Usage: 40.8 MB, less than 90.34% of Java online submissions for Word Break II.
     *
     * Time: O(ROWS) * O(average word.length()) * O(word count i s), where recursion will be executed O(word count in s) * O(ROWS)
     * Space: O(word count in s) + COLS * memo[i].list size(), where recursion stack size: O(word count in s)
     *
     */
    public List<String> wordBreakLukeBacktrackMemo(String s, List<String> wordDict) {
        if (wordDict == null || wordDict.size() == 0) {
            return new ArrayList<>();
        }

        final int COLS = s.length();
        int ROWS = wordDict.size();

        /**
         * Time: O(COLS * ROWS)
         * Space: O(word.length() * ROWS)
         */
        wordDict = wordDict.stream().filter(e -> s.contains(e)).toList();

        ROWS = wordDict.size();

        /**
         * Space: O(COLS)
         */
        final IdxList[] memo = new IdxList[COLS];

        /**
         * Time: O()
         * Space: O()
         */
        IdxList idxList = backtrackMemo(s, wordDict, 0, memo, COLS, ROWS);

        // log.debug("idxList: {}", idxList);
        // log.debug("memo: {}", () -> memo);

        if (idxList.isMatch) {
            return idxList.list;
        } else {
            return new ArrayList<>();
        }
    }

    record IdxList(int idx, boolean isMatch, List<String> list) {
        public IdxList(int idx, boolean isMatch, List<String> list) {
            this.idx = idx;
            this.isMatch = isMatch;
            if (list == null) {
                this.list = new ArrayList<>();
            } else {
                this.list = list;
            }
        }
    }

    /**
     * Time: O(ROWS) * O(average word.length()) * O(word count i s), where recursion will be executed O(word count in s) * O(ROWS)
     * Space: O(word count in s) + COLS * memo[i].list size(), where recursion stack size: O(word count in s)
     */
    private IdxList backtrackMemo(
            final String s,
            final List<String> wordLists,
            final int idx,
            final IdxList[] memo,
            final int COLS,
            final int ROWS) {

        // Boundary conditions
        if (idx >= COLS) {
            return new IdxList(idx, true, new ArrayList<>());
        }

        if (memo[idx] != null) {

            // log.debug("------- Re-use memo. idx: {}", idx);

            return memo[idx];
        } else {
            boolean foundMatch = false;

            /**
             * Time: O(ROWS) * O(average word.length()) * O(word count i s), where recursion will be executed O(word count in s) * O(ROWS)
             * Space: O(word count in s) + COLS * memo[i].list size(), where recursion stack size: O(word count in s)
             */
            for (int row = 0; row < ROWS; row++) {
                /**
                 * Time: O(average word.length())
                 */
                if (startsWith(s, idx, wordLists.get(row))) {
                    foundMatch = true;
                    String word = wordLists.get(row);

                    /**
                     * O(word count in s)
                     */
                    IdxList subIdxList = backtrackMemo(s, wordLists, idx + word.length(), memo, COLS, ROWS);

                    // log.debug("------- Found. idx: {}, word: {}, subIdxList: {}", idx, word, subIdxList);

                    if (subIdxList.isMatch) {
                        if (subIdxList.list.isEmpty()) {
                            if (memo[idx] == null) {
                                memo[idx] = new IdxList(idx, true, new ArrayList<>());
                            }
                            memo[idx].list.add(word);
                        } else {
                            if (memo[idx] == null) {
                                memo[idx] = new IdxList(idx, true, new ArrayList<>());
                            }
                            subIdxList.list.forEach(e -> {
                                memo[idx].list.add(word + " " + e);
                            });
                        }
                        if (!memo[idx].isMatch) {
                            memo[idx] = new IdxList(idx, true, memo[idx].list);
                        }
                    } else {
                        if (memo[idx] == null) {
                            memo[idx] = new IdxList(idx, false, null);
                        }
                    }
                }
            }
            if (!foundMatch) {
                if (memo[idx] == null) {
                    memo[idx] = new IdxList(idx, false, null);
                }
            }

            return memo[idx];
        }
    }

    /**
     * Luke - Brute
     *
     * Runtime: 6 ms, faster than 51.26% of Java online submissions for Word Break II.
     * Memory Usage: 42.6 MB, less than 39.69% of Java online submissions for Word Break II.
     *
     * Time: O(ROWS) * O(average word.length()) * O(word count i s), where recursion will be executed O(word count in s) * O(ROWS)
     * Space: Space: O(COLS)
     */
    public List<String> wordBreakLukeBacktrackBrute(String s, List<String> wordDict) {
        final List<String> sentences = new ArrayList<>();

        if (wordDict == null || wordDict.size() == 0) {
            return sentences;
        }

        final int COLS = s.length();
        int ROWS = wordDict.size();

        /**
         * Time: O(COLS * ROWS)
         * Space: O(word.length() * ROWS)
         */
        wordDict = wordDict.stream().filter(e -> s.contains(e)).toList();

        ROWS = wordDict.size();

        final LinkedList<String> list = new LinkedList<>();

        backtrackBrute(s, wordDict, 0, COLS, ROWS, sentences, list);

        return sentences;
    }

    /**
     * Time: O(ROWS) * O(average word.length()) * O(word count i s), where recursion will be executed O(word count in s) * O(ROWS)
     * Space: Space: O(COLS)
     */
    private void backtrackBrute(
            final String s,
            final List<String> wordLists,
            int idx,
            final int COLS,
            final int ROWS,
            final List<String> sents,
            final LinkedList<String> list) {

        // Boundary conditions
        if (idx >= COLS) {
            sents.add(list.stream().collect(Collectors.joining(" ")));
            return;
        }

        /**
         * Time: O(ROWS) * O(2 ^ (word count in s))
         */
        for (int row = 0; row < ROWS; row++) {
            /**
             * Time: O(word.length())
             * Space: O(1)
             */
            if (startsWith(s, idx, wordLists.get(row))) {

                /**
                 * Space: O(COLS)
                 */
                list.add(wordLists.get(row));

                /**
                 * Time: O(word count in s)
                 */
                backtrackBrute(s, wordLists, idx + wordLists.get(row).length(), COLS, ROWS, sents, list);
                list.removeLast();
            }
        }
    }

    /**
     * Time: O(word.length())
     * Space: O(1)
     */
    boolean startsWith(String s, int idx, String word) {
        if (idx + word.length() > s.length()) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (s.charAt(idx + i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * LC - DP Top-Down - (1) Use Map, and use word as key of map.
     *                    (2) Use word Set, and use set.contains() to match word.
     *
     *
     * Time: O(N ^ 2 + 2 ^ N + W)
     * Space: O(2 ^ N * N + W)
     */

    /**
     * Do NOT initialize the members to make the class stateless!!!
     */
    protected Set<String> wordSet;
    protected HashMap<String, List<List<String>>> memo;

    public List<String> wordBreakLcTopDown(String s, List<String> wordDict) {
        wordSet = new HashSet<>();
        for (String word : wordDict) {
            wordSet.add(word);
        }
        memo = new HashMap<>();

        backtrackLcTopDown(s);

        // chain up words together
        List<String> ret = new ArrayList<>();
        for (List<String> words : memo.get(s)) {
            StringBuffer sentence = new StringBuffer();
            for (String word : words) {
                sentence.insert(0, word);
                sentence.insert(0, " ");
            }
            ret.add(sentence.toString().strip());
        }

        return ret;
    }

    protected List<List<String>> backtrackLcTopDown(String s) {
        if (s.equals("")) {
            List<List<String>> solutions = new ArrayList<>();
            solutions.add(new ArrayList<>());
            return solutions;
        }

        if (memo.containsKey(s)) {
            return memo.get(s);
        } else {
            List<List<String>> solutions = new ArrayList<>();
            memo.put(s, solutions);
        }

        for (int end = 1; end <= s.length(); ++end) {
            String word = s.substring(0, end);
            if (wordSet.contains(word)) {
                List<List<String>> subsentences = backtrackLcTopDown(s.substring(end));
                for (List<String> subsentence : subsentences) {
                    List<String> newSentence = new ArrayList<>(subsentence);
                    newSentence.add(word);
                    memo.get(s).add(newSentence);
                }
            }
        }
        return memo.get(s);
    }

    /**
     * LC - DP - Bottom-Up - Iterative
     *
     *
     */
    private void updateCharSet(String s, HashSet<Character> charSet) {
        for (int i = 0; i < s.length(); ++i)
            charSet.add(s.charAt(i));
    }

    public List<String> wordBreakLcBottomUp(String s, List<String> wordDict) {
        HashSet<Character> stringCharSet = new HashSet<>();
        updateCharSet(s, stringCharSet);

        HashSet<Character> wordCharSet = new HashSet<>();
        wordSet = new HashSet<>();
        for (String word : wordDict) {
            wordSet.add(word);
            updateCharSet(word, wordCharSet);
        }

        // quick check on the sets of characters
        if (!wordCharSet.containsAll(stringCharSet)) {
            return new ArrayList<>();
        }

        final ArrayList<ArrayList<String>> dp = new ArrayList<>(s.length() + 1);

        for (int i = 0; i < s.length() + 1; ++i) {
            ArrayList<String> emptyList = new ArrayList<>();
            dp.add(emptyList);
        }
        dp.get(0).add("");

        for (int endIndex = 1; endIndex < s.length() + 1; ++endIndex) {
            ArrayList<String> sublist = new ArrayList<>();

            // fill up the values in the dp array.
            for (int startIndex = 0; startIndex < endIndex; ++startIndex) {
                String word = s.substring(startIndex, endIndex);
                if (wordSet.contains(word)) {
                    for (String subsentence : dp.get(startIndex)) {
                        sublist.add((subsentence + " " + word).strip());
                    }
                }
            }
            dp.set(endIndex, sublist);
        }

        return dp.get(s.length());
    }

    /**
     * LC - Recursive Encoding
     */
    protected ArrayList<ArrayList<ArrayList<Integer>>> dp;

    protected String inputString;
    protected ArrayList<String> result;

    public List<String> wordBreakLcRecursiveEncoding(String s, List<String> wordDict) {
        HashSet<Character> stringCharSet = new HashSet<Character>();
        updateCharSet(s, stringCharSet);

        HashSet<Character> wordCharSet = new HashSet<Character>();
        wordSet = new HashSet<>();
        for (String word : wordDict) {
            wordSet.add(word);
            updateCharSet(word, wordCharSet);
        }

        // quick check on the sets of characters
        if (!wordCharSet.containsAll(stringCharSet))
            return new ArrayList<>();

        inputString = s;
        dp = new ArrayList<ArrayList<ArrayList<Integer>>>(s.length() + 1);
        for (int i = 0; i < s.length() + 1; ++i) {
            ArrayList<ArrayList<Integer>> emptyList = new ArrayList<>();
            dp.add(emptyList);
        }
        ArrayList<Integer> start = new ArrayList<>();
        start.add(0);
        dp.get(0).add(start);

        for (int endIndex = 1; endIndex < s.length() + 1; ++endIndex) {
            ArrayList<ArrayList<Integer>> stops = new ArrayList<>();

            // fill up the values in the dp array.
            for (int startIndex = 0; startIndex < endIndex; ++startIndex) {
                String word = s.substring(startIndex, endIndex);
                if (wordSet.contains(word)) {
                    ArrayList<Integer> newStop = new ArrayList<>();
                    newStop.add(startIndex);
                    newStop.add(endIndex);
                    stops.add(newStop);
                }
            }
            dp.set(endIndex, stops);
        }

        this.result = new ArrayList<String>();
        wordDFS("", s.length());
        return this.result;
    }

    protected void wordDFS(String sentence, Integer dpIndex) {
        if (dpIndex == 0) {
            result.add(sentence.strip());
            return;
        }

        for (List<Integer> wordIndex : dp.get(dpIndex)) {
            Integer start = wordIndex.get(0), end = wordIndex.get(1);
            String newSentence = inputString.substring(start, end) + " " + sentence;
            wordDFS(newSentence, start);
        }
    }
}
