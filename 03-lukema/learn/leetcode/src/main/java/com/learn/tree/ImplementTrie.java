package com.learn.tree;


import java.util.HashSet;
import java.util.Set;


/**
 * LC - 208 - Implement Trie
 *
 * Medium
 *
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are
 * various applications of this data structure, such as autocomplete and spellchecker.
 *
 * Implement the Trie class:
 *
 *     Trie() Initializes the trie object.
 *     void insert(String word) Inserts the string word into the trie.
 *     boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
 *     boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 *
 * Example 1:
 * Input
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * Output
 * [null, null, true, false, true, null, true]
 * Explanation
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // return True
 * trie.search("app");     // return False
 * trie.startsWith("app"); // return True
 * trie.insert("app");
 * trie.search("app");     // return True
 *
 * Constraints:
 *     1 <= word.length, prefix.length <= 2000
 *     word and prefix consist only of lowercase English letters.
 *     At most 3 * 104 calls in total will be made to insert, search, and startsWith.
 */
public class ImplementTrie {

    public static void main(String[] args) {

    }
}


/**
 * Luke - HashSet + stream.filter(e -> e.startsWith(prefix)).count();
 *
 * Runtime: 726 ms, faster than 5.01% of Java online submissions for Implement Trie (Prefix Tree).
 * Memory Usage: 69.5 MB, less than 26.72% of Java online submissions for Implement Trie (Prefix Tree).
 *
 * Time: O(N)
 * Space: O(N)
 */
class TrieLuke {

    private final Set<String> set;

    public TrieLuke() {
        set = new HashSet<>();
    }

    public void insert(String word) {
        set.add(word);
    }

    public boolean search(String word) {
        return set.contains(word);

    }

    public boolean startsWith(String prefix) {
        return set.stream().filter(e -> e.startsWith(prefix)).count() > 0;
    }
}


/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

/**
 * LC - TrieNode
 *
 * Runtime: 36 ms, faster than 95.78% of Java online submissions for Implement Trie (Prefix Tree).
 * Memory Usage: 50.7 MB, less than 98.85% of Java online submissions for Implement Trie (Prefix Tree).
 *
 * Time: O(W), where W is the word length.
 * Space: O(W * 26) = O(W), where W is the word length.
 */

class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Time: O(W), where W is the word length.
     * Space: O(W * 26) = O(W), where W is the word length.
     */
    public void insert(String word) {
        TrieNode curr = root;

        for (int i = 0, n = word.length(); i < n; i++) {
            char ch = word.charAt(i);
            if (!curr.contains(ch)) {
                curr.put(ch, new TrieNode());
            }
            curr = curr.get(ch);
        }

        curr.setEnd();
    }

    /**
     * Time: O(W), where W is the word length.
     * Space: O(1)
     */
    public boolean search(String word) {
        TrieNode curr = root;

        for (int i = 0, n = word.length(); i < n; i++) {
            char ch = word.charAt(i);

            if (!curr.contains(ch)) {
                return false;
            }

            curr = curr.get(ch);
        }

        return curr.isEnd();
    }

    /**
     * Time: O(W), where W is the word length.
     * Space: O(1)
     */
    public boolean startsWith(String prefix) {
        TrieNode curr = root;

        for (int i = 0, n = prefix.length(); i < n; i++) {
            char ch = prefix.charAt(i);

            if (!curr.contains(ch)) {
                return false;
            }

            curr = curr.get(ch);
        }

        return true;
    }
}


class TrieNode {

    final int N = 26;
    final TrieNode[] link;
    private boolean isEnd = false;

    public TrieNode() {
        this.link = new TrieNode[N];
    }

    public boolean contains(char ch) {
        return this.link[ch - 'a'] != null;
    }

    public TrieNode get(char ch) {
        return this.link[ch - 'a'];
    }

    public void put(char ch, TrieNode node) {
        this.link[ch - 'a'] = node;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public void setEnd() {
        this.isEnd = true;
    }

}
