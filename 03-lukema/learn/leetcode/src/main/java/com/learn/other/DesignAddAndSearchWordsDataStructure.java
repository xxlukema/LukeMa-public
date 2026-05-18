package com.learn.other;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 211- Design Add And Search Words Data Structure
 *
 * Medium
 *
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 *
 * Implement the WordDictionary class:
 *     WordDictionary() Initializes the object.
 *     void addWord(word) Adds word to the data structure, it can be matched later.
 *     bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.'
 *     where dots can be matched with any letter.
 *
 * Example:
 * Input
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output
 * [null,null,null,null,false,true,true,true]
 * Explanation
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 *
 * Constraints:
 *     1 <= word.length <= 25
 *     word in addWord consists of lowercase English letters.
 *     word in search consist of '.' or lowercase English letters.
 *     There will be at most 3 dots in word for search queries.
 *     At most 104 calls will be made to addWord and search.
 */
@Log4j2
public class DesignAddAndSearchWordsDataStructure {

    public static void main(String[] args) {

        // Trie trie = new Trie();
        // trie.add('a');

        // Assertions.assertTrue(trie.search('a'));
        // Assertions.assertFalse(trie.search('b'));

        WordDictionary wordDictionary = new WordDictionary();

        wordDictionary.addWord("ab");
        wordDictionary.addWord("abc");

        Assertions.assertTrue(wordDictionary.search("ab"));
        Assertions.assertTrue(wordDictionary.search(".bc"));
        Assertions.assertFalse(wordDictionary.search("abcd"));
        Assertions.assertFalse(wordDictionary.search("a"));

        log.debug("Trie: {}", () -> "OK");
    }
}


/**
 * Luke
 *
 * Time Limit Exceeded
 *
 * Time: O(N * LEN), where N is amount of words, and LEN is the length of words
 * Space: O(26 * LEN * N) = O(N * LEN)
 */
class WordDictionary {

    private Trie trie;

    public WordDictionary() {
        this.trie = new Trie();
    }

    public void addWord(String word) {
        Trie curr = trie;

        for (int i = 0, n = word.length(); i < n; i++) {
            char ch = word.charAt(i);
            curr = curr.add(ch);
        }

        curr.setEnd();
    }

    public boolean search(String word) {
        if (word.indexOf('.') > -1) {
            /**
             * With '.' in word
             */
            Queue<Trie> queue = new ConcurrentLinkedQueue<>();
            queue.add(trie);

            for (int i = 0, n = word.length(); i < n; i++) {
                if (queue.isEmpty()) {
                    return false;
                } else {
                    char ch = word.charAt(i);
                    int len = queue.size();
                    for (int k = 0; k < len; k++) {
                        Trie t = queue.poll();
                        List<Trie> found = t.search(ch);
                        if (found.size() > 0) {
                            queue.addAll(found);
                        }
                    }
                }
            }

            while (!queue.isEmpty()) {
                Trie t = queue.poll();
                if (t.isEnd()) {
                    return true;
                }
            }

            return false;
        } else {
            /**
             * Without '.' in word
             */
            Trie curr = trie;

            for (int i = 0, n = word.length(); i < n; i++) {
                char ch = word.charAt(i);
                int idx = ch - 'a';
                if (curr.nodes[idx] == null) {
                    return false;
                } else {
                    curr = curr.nodes[idx];
                }
            }

            return curr.isEnd();
        }
    }
}


/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

@Log4j2
class Trie {

    private boolean isEnd;
    public static final int N = 26;

    Trie[] nodes;

    public Trie() {
        this.nodes = new Trie[N];
        log.debug("Trie() constructor");
    }

    public Trie add(char ch) {
        int idx = ch - 'a';
        if (nodes[idx] == null) {
            nodes[idx] = new Trie();
        }

        return nodes[idx];
    }

    public List<Trie> search(char ch) {
        List<Trie> list = new ArrayList<>();

        if (ch == '.') {
            for (int i = 0; i < N; i++) {
                if (nodes[i] != null) {
                    list.add(nodes[i]);
                }
            }
        } else {
            int idx = ch - 'a';
            if (nodes[idx] != null) {
                list.add(nodes[idx]);
            }
        }

        return list;
    }

    public void setEnd() {
        isEnd = true;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
