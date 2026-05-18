package com.learn.dp;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 126 - Word Ladder II
 *
 * Hard
 *
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence
 * of words beginWord -> s1 -> s2 -> ... -> sk such that:
 *
 *     Every adjacent pair of words differs by a single letter.
 *     Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 *     sk == endWord
 *
 * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation
 * sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be
 * returned as a list of the words [beginWord, s1, s2, ..., sk].
 *
 * Example 1:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * Explanation: There are 2 shortest transformation sequences:
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 *
 * Example 2:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: []
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 * Constraints:
 *
 *     1 <= beginWord.length <= 5
 *     endWord.length == beginWord.length
 *     1 <= wordList.length <= 500
 *     wordList[i].length == beginWord.length
 *     beginWord, endWord, and wordList[i] consist of lowercase English letters.
 *     beginWord != endWord
 *     All the words in wordList are unique.
 */
@Log4j2
public class WordLadderIIMisunderstood {

    public static void main(String[] args) {

        String beginWord = "hit";
        String endWord = "cog";
        String[] dict = { "hot", "dot", "xxx", "yyy", "zzz", "dog", "lot", "log", "cog" };

        /*
        String beginWord = "hot";
        String endWord = "dog";
        String[] dict = { "hah", "hot", "dog", "dot" };
        */

        List<String> wordList = List.of(dict);

        WordLadderIIMisunderstood wordLadderII = new WordLadderIIMisunderstood();

        var ret = wordLadderII.findLaddersLukeDp(beginWord, endWord, wordList);
        log.debug("Word Ladder II: {}", () -> ret);
    }

    /**
     * Luke - DP
     *
     *
     *
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    public List<List<String>> findLaddersLukeDp(String beginWord, String endWord, List<String> wordList) {
        final List<List<String>> result = new ArrayList<>();
        final int NbrWords = wordList.indexOf(endWord);
        if (NbrWords == -1) {
            return result;
        }

        /**
         * ROWS starts with beginWord, ends before endWord.
         */
        final List<String> ROWS = wordList.stream().limit(NbrWords + 1).collect(Collectors.toList());

        int beginIdx = ROWS.indexOf(beginWord);

        if (beginIdx == -1) {
            ROWS.add(0, beginWord);
        } else {
            while (ROWS.indexOf(beginWord) != -1) {
                ROWS.remove(0);
            }
        }

        log.debug("words: {}", wordList);
        log.debug("ROWS: {}", ROWS);

        /**
         * Dynamic Programming Array:
         *   row: index of current word.
         *   col: index of a matching word.
         *   Only use Top-Right part of the matrix.
         */
        final boolean[][] dp = new boolean[ROWS.size() + 1][NbrWords + 1];

        for (int row = 0; row < ROWS.size(); row++) {
            String r = ROWS.get(row);
            for (int col = row; col <= NbrWords; col++) {
                String c = wordList.get(col);
                if (isMatch(r, c)) {
                    dp[row][col] = true;
                }
            }
        }

        if (!dp[ROWS.size() - 1][NbrWords]) {
            return result;
        }

        final LinkedList<String> llist = new LinkedList<>();
        llist.add(beginWord);

        backtrackLukeDp(wordList, ROWS, NbrWords, dp, 0, 0, llist, result);

        // DpUtils.print(dp);
        // ROWS.forEach(e -> System.out.println(e));

        result.sort((a, b) -> a.size() - b.size());

        int minLen = result.get(0).size();

        return result.stream().filter(e -> e.size() == minLen).toList();
    }

    private void backtrackLukeDp(
            final List<String> wordList,
            final List<String> ROWS,
            final int NbrWords,
            final boolean[][] dp,
            final int row,
            final int col,
            final LinkedList<String> llist,
            final List<List<String>> result) {

        /**
         * endWord reached:
         *
         * endWord is in wordList. If endWord is not included in ROWS,
         * then "row == ROWS.size() - 1". Otherwise, "row == -1".
         */
        if (row == -1 || row == ROWS.size() - 1) {
            result.add(List.copyOf(llist));
            return;
        }

        for (int c = col; c <= NbrWords; c++) {
            if (dp[row][c]) {
                llist.add(wordList.get(c));

                /**
                 * If endWord is not included in ROWS,
                 * then "row == ROWS.size() - 1". Otherwise, "row == -1".
                 *
                 * Let the "return block" handles the exit situations.
                 */
                int nextRow = ROWS.indexOf(wordList.get(c));
                backtrackLukeDp(wordList, ROWS, NbrWords, dp, nextRow, c, llist, result);
                llist.removeLast();
            }
        }
    }

    private boolean isMatch(String s1, String s2) {
        int count = 0;
        for (int i = 0, n = s1.length(); i < n; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }
        return count == 1;
    }
}
