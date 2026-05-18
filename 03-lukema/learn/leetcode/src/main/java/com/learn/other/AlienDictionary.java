package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;


/**
 * LC-269 Alien Dictionary
 *
 * Hard
 *
 * There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.
 *
 * You are given a list of strings words from the alien language's dictionary, where the strings in words are
 * sorted lexicographically by the rules of this new language.
 *
 * Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the
 * new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.
 *
 * Example 1:
 * Input: words = ["wrt","wrf","er","ett","rftt"]
 * Output: "wertf"
 *
 * Example 2:
 * Input: words = ["z","x"]
 * Output: "zx"
 *
 * Example 3:
 * Input: words = ["z","x","z"]
 * Output: ""
 * Explanation: The order is invalid, so return "".
 *
 * Constraints:
 *     1 <= words.length <= 100
 *     1 <= words[i].length <= 100
 *     words[i] consists of only lowercase English letters.
 */
@Log4j2
public class AlienDictionary {

    public static void main(String[] args) {

        /**
         * Expected: wertf
         */
        // final String[] words = { "wrt", "wrf", "er", "ett", "rftt" };

        /**
         * Expected: ""
         */
        // final String[] words = { "z", "x", "z" };

        /**
         * Expected: ""
         */
        final String[] words = { "z", "z" };

        AlienDictionary alienDictionary = new AlienDictionary();

        var alienOrderLuke = alienDictionary.alienOrderLuke(words);
        log.debug("Alien Dictionary: {}", () -> alienOrderLuke);
        log.debug("Alien Dictionary {} OK", () -> "alienOrderLuke");

        var alienOrderLc = alienDictionary.alienOrderLc(words);
        Assertions.assertEquals(alienOrderLuke, alienOrderLc);
        log.debug("Alien Dictionary {} OK", () -> "alienOrderLc");

    }

    /**
     * LC - Trick 1: Use White/Grey/Black to detect cycle.
     *    - Trick 2: Post Order DFS in combination with White/Grey/Black cyclic graph detection
     *    - Trick 3: Put all chars into adjMap, even if they are edge nodes
     *
     * Time: O(Number of words)
     * Space: O(1) or O(U+min⁡(U ^ 2, N))
     */
    public String alienOrderLc(String[] words) {

        final Map<Character, List<Character>> reverseAdjList = new HashMap<>();

        // Step 0: Put all unique letters into reverseAdjList as keys.
        for (String word : words) {
            for (char c : word.toCharArray()) {
                reverseAdjList.putIfAbsent(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges and add reverse edges to reverseAdjList.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    reverseAdjList.get(word2.charAt(j)).add(word1.charAt(j));
                    break;
                }
            }
        }

        final Map<Character, Boolean> seen = new HashMap<>();
        final StringBuilder sb = new StringBuilder();

        // Step 2: DFS to build up the output list.
        for (Character c : reverseAdjList.keySet()) {
            boolean isValid = dfsLc(c, reverseAdjList, seen, sb);
            if (!isValid) {
                return "";
            }
        }

        return sb.toString();
    }

    // Return true iff no cycles detected.
    private boolean dfsLc(
            Character ch,
            final Map<Character, List<Character>> reverseAdjList,
            Map<Character, Boolean> seen,
            final StringBuilder sb) {

        /**
         * start
         */
        if (seen.containsKey(ch)) {
            return seen.get(ch); // If this node was grey (false), a cycle was detected.
        }
        seen.put(ch, false);
        for (Character next : reverseAdjList.get(ch)) {
            boolean result = dfsLc(next, reverseAdjList, seen, sb);
            if (!result) {
                return false;
            }
        }
        seen.put(ch, true);
        sb.append(ch);
        return true;
    }

    /**
     * Luke - Trick 1: Use White/Grey/Black to detect cyclic graph.
     *      - Trick 2: Post Order DFS in combination with White/Grey/Black cyclic graph detection
     *      - Trick 3: Put all chars into adjMap, even if they are edge nodes
     *
     * Runtime: 6 ms Beats 68.19%
     * Memory: 40.5 MB Beats 94.14%
     *
     * Time: O(Number of words)
     * Space: O(1) or O(U+min⁡(U ^ 2, N))
     */
    public String alienOrderLuke(String[] words) {

        /**
         * build graph
         */
        final List<Edge> edges = new ArrayList<>();

        for (int i = 0, end = words.length - 1; i < end; i++) {

            /**
             * Invalid input if leading substring is greater than full string: ['string1', 'str']
             */
            if (words[i].length() > words[i + 1].length()) {
                if (words[i].indexOf(words[i + 1]) == 0) {
                    return "";
                }
            }

            Edge edge = retrieveEdge(words[i], words[i + 1]);
            if (edge == null) {
                continue;
            } else {
                edges.add(edge);
            }
        }

        /**
         * build adjList
         */
        final Map<Character, List<Character>> adjMap = new HashMap<>();

        /**
         * Trick 3: Put all unique letters into reverseAdjList as keys.
         *          Without this, ['zero', 'zero'] will result in empty result.
         */
        for (String word : words) {
            for (char c : word.toCharArray()) {
                adjMap.putIfAbsent(c, new ArrayList<>());
            }
        }

        for (Edge edge : edges) {
            adjMap.get(edge.getStart()).add(edge.getEnd());
        }

        /**
         * sort edges
         */

        final LinkedList<Character> list = new LinkedList<>();

        /**
         * DFS
         */
        final Set<Character> keys = adjMap.keySet();

        /**
         * Trick 1: White/Grey/Black cyclic graph detection
         */
        final Map<Character, Boolean> seen = new HashMap<>();

        for (Character ch : keys) {
            boolean isValid = dfsLuke(ch, adjMap, seen, list);
            if (!isValid) {
                return "";
            }
        }

        return list.stream().filter(e -> e != null).map(String::valueOf).collect(Collectors.joining());
    }

    /**
     * Luke - Trick 1: Use White/Grey/Black to detect cycle.
     *      - Trick 2: Post Order DFS
     *      - Trick 3: Put all chars into adjMap, even if they are edge nodes
     *
     * Time: O(Number of words)
     * Space: O(1) or O(U+min⁡(U ^ 2, N))
     */
    private boolean dfsLuke(
            Character cur,
            final Map<Character, List<Character>> adjMap,
            final Map<Character, Boolean> seen,
            final LinkedList<Character> list) {

        /**
         * start
         */
        if (seen.containsKey(cur)) {
            return seen.get(cur);
        }

        seen.put(cur, false);

        List<Character> neighbors = adjMap.get(cur);

        for (Character neighbor : neighbors) {
            boolean isValid = dfsLuke(neighbor, adjMap, seen, list);
            if (!isValid) {
                return false;
            }
        }

        /**
         * Trick 2: Post Order DFS in combination with White/Grey/Black cyclic graph detection
         */
        list.add(0, cur);
        seen.put(cur, true);

        return true;
    }

    Edge retrieveEdge(String w1, String w2) {
        int idx = 0;
        while (idx < w1.length() && idx < w2.length()) {
            if (w1.charAt(idx) == w2.charAt(idx)) {
                idx++;
            } else {
                return new Edge(w1.charAt(idx), w2.charAt(idx));
            }
        }

        return null;
    }
}


@Data
class Edge {
    private char start;
    private char end;

    public Edge(char start, char end) {
        this.start = start;
        this.end = end;
    }
}
