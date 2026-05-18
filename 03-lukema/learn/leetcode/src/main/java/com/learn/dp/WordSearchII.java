package com.learn.dp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 212 - Word Search II
 *
 * Hard
 *
 * Given an m x n board of characters and a list of strings words, return all words on the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 *
 * Example 1:
 * Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
 * Output: ["eat","oath"]
 *
 * Example 2:
 * Input: board = [["a","b"],["c","d"]], words = ["abcb"]
 * Output: []
 *
 * Constraints:
 *     m == board.length
 *     n == board[i].length
 *     1 <= m, n <= 12
 *     board[i][j] is a lowercase English letter.
 *     1 <= words.length <= 3 * 104
 *     1 <= words[i].length <= 10
 *     words[i] consists of lowercase English letters.
 *     All the strings of words are unique.
 */
@Log4j2
public class WordSearchII {

    public static void main(String[] args) {

        /**
         * Outout: {"eat", "oath"}
         */
        /*
        final char[][] board = {
                { 'o', 'a', 'a', 'n' },
                { 'e', 't', 'a', 'e' },
                { 'i', 'h', 'k', 'r' },
                { 'i', 'f', 'l', 'v' } };
        final String[] words = { "oath", "pea", "eat", "rain" };
        */

        /**
         * Output: {"abcdefg", "befa", "eaabcdgfa", "gfedcbaaa"}
         */
        /*
        final char[][] board = {
                { 'a', 'b', 'c' },
                { 'a', 'e', 'd' },
                { 'a', 'f', 'g' } };
        final String[] words = { "abcdefg", "gfedcbaaa", "eaabcdgfa", "befa", "dgc", "ade" };
        */

        /**
         * Output: {"abcdefg", "befa", "eaabcdgfa", "gfedcbaaa"}
         */
        final char[][] board = {
                { 'o', 'a', 'b', 'n' },
                { 'o', 't', 'a', 'e' },
                { 'a', 'h', 'k', 'r' },
                { 'a', 'f', 'l', 'v' } };
        final String[] words = { "oa", "oaa" };

        WordSearchII wordSearchII = new WordSearchII();

        var findWordsLukeBrute = wordSearchII.findWordsLukeBrute(board, words);
        log.debug("Word Search II: {}", () -> findWordsLukeBrute);
        log.debug("Word Search II {} OK", () -> "findWordsLukeBrute");

        var findWordsLukeTrie = wordSearchII.findWordsLukeTrie(board, words);
        log.debug("Word Search II: {}", () -> findWordsLukeTrie);
        log.debug("Word Search II {} OK", () -> "findWordsLukeTrie");

        var findWordsLukeTrieMap = wordSearchII.findWordsLukeTrieMap(board, words);
        log.debug("Word Search II: {}", () -> findWordsLukeTrieMap);
        log.debug("Word Search II {} OK", () -> "findWordsLukeTrieMap");

    }

    /**
     * Luke - TrieMap
     *
     * Time Limit Exeeded
     *
     * Time: O(M * (4 * 3 ^ (L−1))),
     * Space: O(N)
     */

    private TrieMap trieMap;

    public List<String> findWordsLukeTrieMap(final char[][] board, final String[] words) {

        this.trieMap = new TrieMap();

        final List<String> list = new ArrayList<>();

        final int ROWS = board.length;
        final int COLS = board[0].length;

        if (ROWS == 0 || COLS == 0) {
            return list;
        }

        /**
         * Add words to Trie root
         */
        for (String word : words) {
            char[] chars = word.toCharArray();
            TrieMap curr = trieMap;
            for (char ch : chars) {
                curr = curr.add(ch);
            }
            curr.setEnd();
            curr.setWord(word);
        }

        /**
         * Space: O(ROWS * COLS)
         */
        final boolean[][] visited = new boolean[ROWS][COLS];

        /**
         * Search board
         *
         * Time: O(ROWS * COLS * backtrack-time-comlexity)
         */
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                char ch = board[row][col];
                if (trieMap.contains(ch)) {
                    backtrackMatchWordLukeTrieMap(board, row, col, trieMap.get(ch), ROWS, COLS, list, visited);
                }
            }
        }

        return list.stream().distinct().toList();
    }

    private void backtrackMatchWordLukeTrieMap(
            final char[][] board,
            final int row,
            final int col,
            final TrieMap curr,
            final int ROWS,
            final int COLS,
            final List<String> list,
            final boolean[][] visited) {

        /** backtrack start */
        if (curr.isEnd()) {
            list.add(curr.getWord());
        }

        /*
        if (row < 0 || col < 0 || row >= ROWS || col >= COLS) {
            return;
        }
        */

        visited[row][col] = true;

        // log.debug("----- char: {}", board[row][col]);
        // DpUtils.print(visited);

        // curr = curr.map.get(board[row][col]);

        for (char ch : curr.map.keySet()) {

            int[] nextRows = { row - 1, row, row, row + 1 };
            int[] nextCols = { col, col - 1, col + 1, col };

            for (int k = 0; k < 4; k++) {
                if (nextRows[k] >= 0 && nextCols[k] >= 0 && nextRows[k] < ROWS && nextCols[k] < COLS && !visited[nextRows[k]][nextCols[k]]) {
                    if (board[nextRows[k]][nextCols[k]] == ch) {
                        backtrackMatchWordLukeTrieMap(board, nextRows[k], nextCols[k], curr.get(ch), ROWS, COLS, list, visited);
                    }
                }
            }
        }

        visited[row][col] = false;
    }

    /////////////////
    /////////////////
    /////////////////
    /////////////////

    /**
     * Luke - Trie - Using char[26]
     */

    private Trie root;

    public List<String> findWordsLukeTrie(final char[][] board, final String[] words) {

        /**
         * Space: O(26 * words.length())
         */
        this.root = new Trie();

        final List<String> list = new ArrayList<>();

        final int ROWS = board.length;
        final int COLS = board[0].length;

        if (ROWS == 0 || COLS == 0) {
            return list;
        }

        /**
         * Add words to Trie root
         */
        for (String word : words) {
            char[] chars = word.toCharArray();
            Trie curr = root;
            for (char ch : chars) {
                curr = curr.add(ch);
            }
            curr.setEnd();
            curr.setWord(word);
        }

        /**
         * Space: O(ROWS * COLS)
         */
        final boolean[][] visited = new boolean[ROWS][COLS];

        /**
         * Search board
         *
         * Time: O(ROWS * COLS * backtrack-time-comlexity)
         */
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                char ch = board[row][col];
                if (root.contains(ch)) {
                    backtrackMatchWordLukeTrie(board, row, col, root.get(ch), ROWS, COLS, list, visited);
                }
            }
        }

        return list.stream().distinct().toList();
    }

    private void backtrackMatchWordLukeTrie(
            final char[][] board,
            final int row,
            final int col,
            final Trie curr,
            final int ROWS,
            final int COLS,
            final List<String> list,
            final boolean[][] visited) {

        /** backtrack start */
        if (curr.isEnd()) {
            list.add(curr.getWord());
        }

        /*
        if (row < 0 || col < 0 || row >= ROWS || col >= COLS) {
            return;
        }
        */

        visited[row][col] = true;

        // log.debug("----- char: {}", board[row][col]);
        // DpUtils.print(visited);

        Trie[] tries = curr.nodes;

        for (int i = 0; i < Trie.N; i++) {
            if (tries[i] != null) {
                char ch = (char) ('a' + i);

                int[] nextRows = { row - 1, row, row, row + 1 };
                int[] nextCols = { col, col - 1, col + 1, col };

                for (int k = 0; k < 4; k++) {
                    if (nextRows[k] >= 0 && nextCols[k] >= 0 && nextRows[k] < ROWS && nextCols[k] < COLS && !visited[nextRows[k]][nextCols[k]]) {
                        if (board[nextRows[k]][nextCols[k]] == ch) {
                            backtrackMatchWordLukeTrie(board, nextRows[k], nextCols[k], tries[i], ROWS, COLS, list, visited);
                        }
                    }
                }
            }
        }

        visited[row][col] = false;
    }

    /**
     * Luke - Brute
     *
     * Time Limit Exceeded
     *
     * Time: O(ROWS * COLS * work.length)
     * Space: O(ROWS * COLS) for visited + O(word.length) recursion stack size.
     */
    public List<String> findWordsLukeBrute(final char[][] board, final String[] words) {
        final List<String> list = new ArrayList<>();

        final int ROWS = board.length;
        final int COLS = board[0].length;

        if (ROWS == 0 || COLS == 0) {
            return list;
        }

        final boolean[][] visited = new boolean[ROWS][COLS];

        for (String word : words) {
            /**
             * Reset visited
             */
            for (int i = 0; i < ROWS; i++) {
                Arrays.fill(visited[i], false);
            }

            if (word.length() <= ROWS * COLS) {
                if (findWordsLukeBrute(board, word, ROWS, COLS, visited)) {
                    list.add(word);
                }
            }
        }

        return list;
    }

    public boolean findWordsLukeBrute(final char[][] board, final String word, final int ROWS, final int COLS, final boolean[][] visited) {
        /**
         * Time: O(ROWS * COLS * work.length)
         * Space: O(ROWS * COLS) for visited + O(word.length) recursion stack size.
         */
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean isMatch = backtrackMatchWordLukeBrute(board, word, 0, r, c, ROWS, COLS, visited);

                if (isMatch) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Time: O(word.length * 3)
     * Space: O(word.length) resursion stack size.
     */
    public boolean backtrackMatchWordLukeBrute(
            final char[][] board,
            final String word,
            int idx,
            int row,
            int col,
            final int ROWS,
            final int COLS,
            final boolean[][] visited) {

        /** End of recursion */
        /* Already covered inside "if" block.
        if (idx == word.length()) {
            return true;
        }
        */

        if (row < 0 || col < 0 || row >= ROWS || col >= COLS || visited[row][col]) {
            return false;
        }

        char ch = word.charAt(idx);

        if (ch == board[row][col]) {
            /**
             * mark the cell visited to prevent re-visit
             *
             * Trick 1: Mark the cell "visited" ONLY if the char matches.
             */
            visited[row][col] = true;

            /**
             * Last char of the word is matched. "return true;" to prevent further call of backtrack.
             */
            if (idx == word.length() - 1) {
                return true;
            }

            int nextIdx = idx + 1;
            boolean isMatch = backtrackMatchWordLukeBrute(board, word, nextIdx, row + 1, col, ROWS, COLS, visited) ||
                    backtrackMatchWordLukeBrute(board, word, nextIdx, row, col + 1, ROWS, COLS, visited) ||
                    backtrackMatchWordLukeBrute(board, word, nextIdx, row - 1, col, ROWS, COLS, visited) ||
                    backtrackMatchWordLukeBrute(board, word, nextIdx, row, col - 1, ROWS, COLS, visited);

            /**
             * Trick 2: Mark the cell "unvisited" if it does not macth for this char, so that the cell can be matched for other chars.
             */
            if (!isMatch) {
                visited[row][col] = false;
            }

            return isMatch;
        } else {
            return false;
        }
    }

    /**
     * LC - Trie
     *
     * Runtime: 156 ms, faster than 76.03% of Java online submissions for Word Search II.
     * Memory Usage: 71.9 MB, less than 30.01% of Java online submissions for Word Search II.
     *
     * Time: O(M * (4 * 3 ^ (L − 1)))
     * Space: O(N)
     */

    class TrieNode {

        /**
         * Use "Map" instead of "new int[26]"" to save space and performance if both Uppercase and Lowercase letters are used.
         */
        final HashMap<Character, TrieNode> children = new HashMap<>();

        String word = null;

        public TrieNode() {
        }
    }

    public List<String> findWordsLcTrie(final char[][] board, final String[] words) {

        final ArrayList<String> result = new ArrayList<>();

        // Step 1). Construct the Trie
        final TrieNode root1 = new TrieNode();

        for (String word : words) {
            TrieNode node = root1;

            for (Character letter : word.toCharArray()) {
                if (node.children.containsKey(letter)) {
                    node = node.children.get(letter);
                } else {
                    TrieNode newNode = new TrieNode();
                    node.children.put(letter, newNode);
                    node = newNode;
                }
            }

            node.word = word; // store words in Trie
        }

        // Step 2). Backtracking starting for each cell in the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (root1.children.containsKey(board[row][col])) {
                    backtrackingLcTrie(row, col, root1, board, result);
                }
            }
        }

        return result;
    }

    private void backtrackingLcTrie(int row, int col, TrieNode root, final char[][] board, final ArrayList<String> result) {
        Character letter = board[row][col];
        TrieNode currNode = root.children.get(letter);

        // check if there is any match
        if (currNode.word != null) {
            result.add(currNode.word);
            currNode.word = null;
        }

        // mark the current letter before the EXPLORATION
        board[row][col] = '#';

        // explore neighbor cells in around-clock directions: up, right, down, left
        int[] rowOffset = { -1, 0, 1, 0 };
        int[] colOffset = { 0, 1, 0, -1 };
        for (int i = 0; i < 4; ++i) {
            int newRow = row + rowOffset[i];
            int newCol = col + colOffset[i];
            if (newRow < 0 || newRow >= board.length || newCol < 0 || newCol >= board[0].length) {
                continue;
            }
            if (currNode.children.containsKey(board[newRow][newCol])) {
                backtrackingLcTrie(newRow, newCol, currNode, board, result);
            }
        }

        // End of EXPLORATION, restore the original letter in the board.
        board[row][col] = letter;

        // Optimization: incrementally remove the leaf nodes
        if (currNode.children.isEmpty()) {
            root.children.remove(letter);
        }
    }

}


class Trie {

    public static final int N = 26;

    final Trie[] nodes;

    private boolean isEnd;

    private String word;

    public Trie() {
        this.nodes = new Trie[N];
    }

    public Trie add(char ch) {
        int idx = ch - 'a';
        if (nodes[idx] == null) {
            nodes[idx] = new Trie();
        }

        return nodes[idx];
    }

    public Trie get(char ch) {
        return nodes[ch - 'a'];
    }

    public boolean contains(char ch) {
        return nodes[ch - 'a'] != null;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}


class TrieMap {

    final Map<Character, TrieMap> map;

    private boolean isEnd;

    private String word;

    public TrieMap() {
        this.map = new HashMap<>();
    }

    public TrieMap add(char ch) {
        if (!map.containsKey(ch)) {
            map.put(ch, new TrieMap());
        }

        return map.get(ch);
    }

    public TrieMap get(char ch) {
        return map.get(ch);
    }

    public boolean contains(char ch) {
        return map.containsKey(ch);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
