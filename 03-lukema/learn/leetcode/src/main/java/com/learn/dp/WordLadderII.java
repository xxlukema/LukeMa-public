package com.learn.dp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;


/**
 * LC - 126 - Word Ladder II
 * <p>
 * Hard
 * <p>
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence
 * of words beginWord -> s1 -> s2 -> ... -> sk such that:
 * <p>
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 * sk == endWord
 * <p>
 * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation
 * sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be
 * returned as a list of the words [beginWord, s1, s2, ..., sk].
 * <p>
 * Example 1:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * Explanation: There are 2 shortest transformation sequences:
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 * <p>
 * Example 2:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: []
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 * <p>
 * Constraints:
 * <p>
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English letters.
 * beginWord != endWord
 * All the words in wordList are unique.
 */
@Log4j2
public class WordLadderII {

    public static void main(String[] args) {

        /*
        String beginWord = "hit";
        String endWord = "cog";
        String[] dict = { "hot", "dot", "xxx", "yyy", "zzz", "dog", "lot", "log", "cog" };
        */

        /*
        String beginWord = "hot";
        String endWord = "dog";
        String[] dict = { "hah", "hot", "dog", "dot" };
        */

        /*
        String beginWord = "a";
        String endWord = "c";
        String[] dict = { "a", "b", "c" };
        */

        String beginWord = "qa";
        String endWord = "sq";
        String[] dict = {
                "si", "go", "se", "cm", "so", "ph", "mt", "db", "mb", "sb", "kr", "ln", "tm", "le", "av", "sm", "ar", "ci", "ca", "br",
                "ti", "ba", "to", "ra", "fa", "yo", "ow", "sn", "ya", "cr", "po", "fe", "ho", "ma", "re", "or", "rn", "au", "ur", "rh", "sr",
                "tc", "lt", "lo", "as", "fr", "nb", "yb", "if", "pb", "ge", "th", "pm", "rb",
                "sh", // <--------------------------"sh" is causing trouble
                "co", "ga", "li", "ha", "hz", "no", "bi", "di", "hi",
                "qa", "pi", "os", "uh", "wm", "an", "me", "mo", "na", "la", "st", "er", "sc", "ne", "mn", "mi", "am", "ex", "pt",
                "io", "be", "fm", "ta", "tb", "ni", "mr", "pa", "he", "lr", "sq",
                "ye" };

        // List<String> wordList = List.of(dict);
        List<String> wordList = Arrays.asList(dict);

        WordLadderII wordLadderII = new WordLadderII();

        var retLc = wordLadderII.findLadders(beginWord, endWord, wordList);
        log.debug("Word Ladder II Lc: {}", () -> retLc);

        var retLuke = wordLadderII.findLaddersLukeDp(beginWord, endWord, wordList);
        Assertions.assertEquals(retLc.size(), retLuke.size());

        log.debug(() -> "Word Ladder II Luke DP OK");
    }

    /**
     * Luke - DP
     * <p>
     * Time Limit Exceeded
     * <p>
     * Time: O(N ^ 2)
     * Space: O(N ^ 2)
     */
    public List<List<String>> findLaddersLukeDp(String beginWord, String endWord, List<String> wordList) {
        final List<List<String>> result = new ArrayList<>();

        if (!wordList.contains(endWord)) {
            return result;
        }

        /**
         * Make wordList mutibale
         */
        List<String> list = new ArrayList<>();
        list.addAll(wordList);
        wordList = list;

        /**
         * Remove beginWord from wordList
         */
        wordList.remove(beginWord);

        /**
         * Remove endWord and place it to the end of wordList
         */
        wordList.remove(endWord);
        wordList.add(endWord);

        final LinkedList<String> llist = new LinkedList<>();
        llist.add(beginWord);

        final int N = wordList.size();

        /**
         * Dynamic Programming Array:
         *   (1) row: index of current word.
         *   (2) col: index of a matching word.
         */
        final boolean[][] dp = new boolean[N][N];

        for (int row = 0; row < N; row++) {
            String word = wordList.get(row);
            for (int col = 0; col < N; col++) {
                String curr = wordList.get(col);
                if (isMatch(word, curr)) {
                    if (!curr.equals(beginWord)) {
                        dp[row][col] = true;
                    }
                }
            }
        }

        // DpUtils.print(dp);
        // wordList.forEach(System.out::println);

        /**
         * Kick off backtrack
         */
        backtrack(beginWord, endWord, wordList, N, -1, result, dp, llist);

        if (result.size() < 2) {
            return result;
        }

        /*
        result.forEach(e -> {
            log.debug("llist: {}", () -> e);
        });
        */

        /**
         * return only the shorted lists
         */
        // int len = result.stream().sorted((a, b) -> a.size() - b.size()).toList().get(0).size();
        int len = result.stream().map(e -> e.size()).sorted().limit(1).toList().get(0);

        return result.stream().filter(a -> a.size() == len).collect(Collectors.toList());
    }

    private int minLen = 0;

    private void backtrack(
            final String beginWord,
            final String endWord,
            final List<String> wordList,
            final int N,
            final int row,
            final List<List<String>> result,
            final boolean[][] dp,
            final LinkedList<String> llist) {

        /**
         * Only get the shorted lists.
         *
         * However, this "return" caused extra call to "llist.removeLast();"
         */
        if (minLen > 0 && llist.size() > minLen) {
            // This "return" will cause extra call to "llist.removeLast();"

            llist.add("tail");

            return;
        }

        /**
         * Kick off backtrack
         */
        if (row == -1) {
            /**
             * Find matching words for beginWord, and start backtrack().
             */
            int idx = wordList.indexOf(beginWord);
            if (idx == -1) {
                for (int col = 0; col < N; col++) {
                    if (isMatch(beginWord, wordList.get(col))) {
                        backtrack(beginWord, endWord, wordList, N, col, result, dp, llist);
                        llist.removeLast();
                        // log.debug(() -> " ----111----=================================--------- After init kickoff");
                    }
                }
            } else {
                for (int col = 0; col < N; col++) {
                    if (dp[idx][col]) {
                        backtrack(beginWord, endWord, wordList, N, col, result, dp, llist);
                        llist.removeLast();
                        // log.debug(() -> " ----222----=================================--------- After init kickoff");
                    }
                }
            }
        } else {
            String word = wordList.get(row);
            llist.add(word);

            // log.debug("row: {}, word: {}, llist: {}", row, word, llist);

            if (word.equals(endWord)) {
                if (minLen == 0) {
                    minLen = llist.size();
                } else {
                    minLen = Math.min(minLen, llist.size());
                }

                /**
                 * Add list to result and return.
                 */
                result.add(List.copyOf(llist));

                // log.debug("-------------- minLen: {}, built a list: {}", () -> minLen, () -> llist);
            } else {
                for (int col = 0; col < N; col++) {
                    if (dp[row][col] && !llist.contains(wordList.get(col))) {
                        backtrack(beginWord, endWord, wordList, N, col, result, dp, llist);
                        llist.removeLast();
                    }

                    // backtrack(beginWord, endWord, wordList, N, col, result, dp, llist);
                    // llist.removeLast();

                }
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

    /**
     * LC
     */

    Map<String, List<String>> adjList = new HashMap<>();
    List<String> currPath = new ArrayList<>();
    List<List<String>> shortestPaths = new ArrayList<>();

    private List<String> findNeighbors(String word, Set<String> wordSet) {
        List<String> neighbors = new ArrayList<>();
        char charList[] = word.toCharArray();

        for (int i = 0, n = word.length(); i < n; i++) {
            char oldChar = charList[i];

            // replace the i-th character with all letters from a to z except the original character
            for (char c = 'a'; c <= 'z'; c++) {
                charList[i] = c;

                // skip if the character is same as original or if the word is not present in the wordList
                if (c == oldChar || !wordSet.contains(String.valueOf(charList))) {
                    continue;
                }
                neighbors.add(String.valueOf(charList));
            }
            charList[i] = oldChar;
        }
        return neighbors;
    }

    private void backtrack(String source, String destination) {
        // store the path if we reached the endWord
        if (source.equals(destination)) {
            List<String> tempPath = new ArrayList<>(currPath);
            shortestPaths.add(tempPath);
        }

        if (!adjList.containsKey(source)) {
            return;
        }

        for (int i = 0; i < adjList.get(source).size(); i++) {
            currPath.add(adjList.get(source).get(i));
            backtrack(adjList.get(source).get(i), destination);
            currPath.remove(currPath.size() - 1);
        }
    }

    private void bfs(String beginWord, Set<String> wordList) {
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);

        // remove the root word which is the first layer in the BFS
        if (wordList.contains(beginWord)) {
            wordList.remove(beginWord);
        }

        Map<String, Integer> isEnqueued = new HashMap<>();
        isEnqueued.put(beginWord, 1);

        while (!q.isEmpty()) {
            // visited will store the words of current layer
            List<String> visited = new ArrayList<>();

            for (int i = q.size() - 1; i >= 0; i--) {
                String currWord = q.remove();

                // findNeighbors will have the adjacent words of the currWord
                List<String> neighbors = findNeighbors(currWord, wordList);
                for (String word : neighbors) {
                    visited.add(word);

                    if (!adjList.containsKey(currWord)) {
                        adjList.put(currWord, new ArrayList<>());
                    }

                    // add the edge from currWord to word in the list
                    adjList.get(currWord).add(word);
                    if (!isEnqueued.containsKey(word)) {
                        q.add(word);
                        isEnqueued.put(word, 1);
                    }
                }
            }
            // removing the words of the previous layer
            for (int i = 0; i < visited.size(); i++) {
                if (wordList.contains(visited.get(i))) {
                    wordList.remove(visited.get(i));
                }
            }
        }
    }

    /**
     * LC - Main
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // copying the words into the set for efficient deletion in BFS
        Set<String> copiedWordList = new HashSet<>(wordList);
        // build the DAG using BFS
        bfs(beginWord, copiedWordList);

        // every path will start from the beginWord
        currPath.add(beginWord);
        // traverse the DAG to find all the paths between beginWord and endWord
        backtrack(beginWord, endWord);

        return shortestPaths;
    }
}
