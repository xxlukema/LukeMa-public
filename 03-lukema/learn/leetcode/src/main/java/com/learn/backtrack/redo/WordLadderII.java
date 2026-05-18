package com.learn.backtrack.redo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


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

        /**
         * Expected: [[hit, hot, dot, dog, cog], [hit, hot, hop, dog, cog], [hit, hot, dot, dop, cog], [hit, hot, hop, dop, cog]]
         */
        // final String beginWord = "hit", endWord = "cog";
        // final String[] wordList = { "hot", "dot", "dog", "lot", "log", "cog", "hop", "dop", "cop" };

        /**
         * Expected: [[hit, hot, dot, dog, cog], [hit, hot, hop, dog, cog], [hit, hot, dot, dop, cog], [hit, hot, hop, dop, cog]]
         */

        /**
         * Expected: 2
         */
        // final String beginWord = "a", endWord = "c";
        // final String[] wordList = { "a", "b", "c" };

        /**
         * Expected: 1
         */
        final String beginWord = "hot", endWord = "dog";
        final String[] wordList = { "hot", "cog", "dog", "tot", "hog", "hop", "pot", "dot" };

        /**
         * Expected: [[hit, hot, dot, dog, cog], [hit, hot, hop, dog, cog], [hit, hot, dot, dop, cog], [hit, hot, hop, dop, cog]]
         */
        // final String beginWord = "hit", endWord = "cog";
        // final String[] wordList = { "hot", "dot", "dog", "lot", "log", "cog" };

        WordLadderII wordLadderII = new WordLadderII();
        var ret = wordLadderII.findLadders(beginWord, endWord, List.of(wordList));
        log.debug("Word Ladder: {}", () -> ret);
        log.debug("Word Ladder {} OK", () -> "ret");

    }

    /**
     * Luke - BFS
     *
     * Runtime: 33 ms Beats 60.54%
     * Memory: 44.2 MB Beats 76.86%
     *
     * Time: O(N ^ 2) * O(wordList.size())
     * Space: O(wordList.size() * Levels * LEN) * O(Queue size of N)
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        final List<List<String>> result = new ArrayList<>();

        final Set<String> wordSet = new LinkedHashSet<>(wordList);

        wordSet.remove(beginWord);

        final int LEN = beginWord.length();

        if (isOneCharDiff(beginWord, endWord, LEN)) {
            result.add(new ArrayList<>());
            result.get(0).add(beginWord);
            result.get(0).add(endWord);
            return result;
        }

        if (!wordSet.contains(endWord)) {
            return result;
        }

        wordSet.remove(endWord);

        final Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        final LinkedList<List<String>> levelNodes = new LinkedList<>();
        boolean foundEndWord = false;

        while (!queue.isEmpty()) {

            int size = queue.size();
            List<String> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                String cur = queue.poll();

                Iterator<String> it = wordSet.iterator();

                while (it.hasNext()) {
                    String str = it.next();

                    /**
                     * Time: O(LEN)
                     */
                    if (isOneCharDiff(cur, str, LEN)) {

                        level.add(str);
                        queue.offer(str);
                        it.remove();

                        if (isOneCharDiff(endWord, str, LEN)) {
                            foundEndWord = true;
                        }
                    }
                }
            }

            levelNodes.add(level);

            if (foundEndWord) {
                break;
            }
        }

        log.debug("levelNodes: {}", levelNodes);

        if (foundEndWord) {
            List<String> lastLevel = levelNodes.removeLast();
            for (String str : lastLevel) {
                if (isOneCharDiff(endWord, str, LEN)) {
                    LinkedList<String> solution = new LinkedList<>();
                    solution.add(endWord);
                    solution.addFirst(str);
                    result.add(solution);
                }
            }

            while (!levelNodes.isEmpty()) {
                lastLevel = levelNodes.removeLast();

                List<List<String>> partialResult = List.copyOf(result);
                result.clear();

                for (List<String> partial : partialResult) {
                    for (String str : lastLevel) {
                        if (isOneCharDiff(str, partial.get(0), LEN)) {
                            List<String> newPartialResult = new ArrayList<>();
                            result.add(newPartialResult);
                            newPartialResult.add(str);
                            newPartialResult.addAll(List.copyOf(partial));
                        }
                    }
                }
            }

            result.forEach(list -> {
                list.add(0, beginWord);
            });

            return result;
        } else {
            return result;
        }
    }

    /**
     * Time: O(LEN)
     * Space: O(1)
     */
    boolean isOneCharDiff(final String src, final String dest, final int len) {
        int diffs = 0;
        for (int i = 0; i < len; i++) {
            if (src.charAt(i) != dest.charAt(i)) {
                diffs++;

                if (diffs > 1) {
                    return false;
                }
            }
        }

        return diffs == 1;
    }
}
