package com.learn.backtrack.redo;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 127 - Word Ladder - BFS
 *
 * Hard
 *
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 *
 *     Every adjacent pair of words differs by a single letter.
 *     Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 *     sk == endWord
 *
 * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord,
 * or 0 if no such sequence exists.
 *
 * Example 1:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
 *
 * Example 2:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 * Constraints:
 *     1 <= beginWord.length <= 10
 *     endWord.length == beginWord.length
 *     1 <= wordList.length <= 5000
 *     wordList[i].length == beginWord.length
 *     beginWord, endWord, and wordList[i] consist of lowercase English letters.
 *     beginWord != endWord
 *     All the words in wordList are unique.
 */
@Log4j2
public class WordLadder {

    public static void main(String[] args) {

        /**
         * Expected: 0
         */
        // final String beginWord = "hot", endWord = "dog";
        // final String[] wordList = { "hot", "dog" };

        /**
         * Expected: 5
         */
        final String beginWord = "hit", endWord = "cog";
        final String[] wordList = { "hot", "dot", "dog", "lot", "log", "cog" };

        /**
         * Expected: 1
         */
        // final String beginWord = "a", endWord = "c";
        // final String[] wordList = { "a", "b", "c" };

        /**
         * Expected: 11
         */
        /*
        final String beginWord = "cet", endWord = "ism";
        final String[] wordList = { "kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay", "sip", "kay", "per", "val", "mes",
                "ohs", "now", "boa", "cet", "pal", "bar", "die", "war", "hay", "eco", "pub", "lob", "rue", "fry", "lit", "rex", "jan", "cot",
                "bid", "ali", "pay", "col", "gum", "ger", "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark", "has", "zip",
                "fez", "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap", "cry", "led", "abe", "box", "ore", "pig", "fie",
                "toy", "fat", "cal", "lie", "noh", "sew", "ono", "tam", "flu", "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax",
                "jim", "san", "pan", "map", "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct", "old", "fin", "feb", "chi",
                "sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe", "ivy", "fan", "age", "fax", "hip", "jib", "mel", "hus", "sob",
                "ifs", "tab", "ara", "dab", "jag", "jar", "arm", "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk", "far",
                "mew", "wit", "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag", "ron", "soy", "gin", "don", "tug", "fay",
                "vic", "boo", "nam", "ave", "buy", "sop", "but", "orb", "fen", "paw", "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam",
                "pew", "web", "hod", "hun", "gyp", "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere", "dig", "era", "cat",
                "fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new", "aye", "ani", "and", "ibm", "yap", "can", "pyx", "tar",
                "kin", "fog", "hum", "pip", "cup", "dye", "lyx", "jog", "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom",
                "vat", "eat", "pus", "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few", "opt", "poe", "art", "ash", "oar",
                "cap", "lop", "may", "shy", "rid", "bat", "sum", "rim", "fee", "bmw", "sky", "maj", "hue", "thy", "ava", "rap", "den", "fla",
                "auk", "cox", "ibo", "hey", "saw", "vim", "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let", "see", "zit",
                "maw", "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo", "any", "dow", "cod", "bed", "vet", "ham", "sis",
                "hex", "via", "fir", "nod", "mao", "aug", "mum", "hoe", "bah", "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who",
                "bet", "gos", "son", "ear", "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada", "bin", "nil", "mia", "ewe",
                "hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid", "tad", "ken", "wad", "rye", "pap", "bog", "gut", "ito", "woe",
                "our", "ado", "sin", "mad", "ray", "hon", "roy", "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep", "bun",
                "try", "lad", "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip", "geo", "cog", "pas", "zen", "odd", "nan",
                "lay", "pod", "fit", "hem", "joy", "bum", "rio", "yon", "dec", "leg", "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur",
                "sid", "sun", "oil", "red", "doc", "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug", "pop", "tub", "sgt",
                "lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag", "mil", "jig", "hub", "low", "did", "tin", "get", "gte",
                "sox", "lei", "mig", "fig", "lon", "use", "ban", "flo", "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant",
                "net", "tux", "ode", "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic", "ted", "wot", "del", "imp", "cob",
                "way", "ann", "tan", "mci", "job", "wet", "ism", "err", "him", "all", "pad", "hah", "hie", "aim", "ike", "jed", "ego", "mac",
                "baa", "min", "com", "ill", "was", "cab", "ago", "ina", "big", "ilk", "gal", "tap", "duh", "ola", "ran", "lab", "top", "gob",
                "hot", "ora", "tia", "kip", "han", "met", "hut", "she", "sac", "fed", "goo", "tee", "ell", "not", "act", "gil", "rut", "ala",
                "ape", "rig", "cid", "god", "duo", "lin", "aid", "gel", "awl", "lag", "elf", "liz", "ref", "aha", "fib", "oho", "tho", "her",
                "nor", "ace", "adz", "fun", "ned", "coo", "win", "tao", "coy", "van", "man", "pit", "guy", "foe", "hid", "mai", "sup", "jay",
                "hob", "mow", "jot", "are", "pol", "arc", "lax", "aft", "alb", "len", "air", "pug", "pox", "vow", "got", "meg", "zoe", "amp",
                "ale", "bud", "gee", "pin", "dun", "pat", "ten", "mob" };
        */

        WordLadder wordLadder = new WordLadder();

        var ladderLengthBfs = wordLadder.ladderLengthBfs(beginWord, endWord, List.of(wordList));
        log.debug("Word Ladder: {}", () -> ladderLengthBfs);
        log.debug("Word Ladder {} OK", () -> "ladderLengthBfs");

        /*
        var ladderLengthDfs = wordLadder.ladderLengthDfs(beginWord, endWord, List.of(wordList));
        log.debug("Word Ladder: {}", () -> ladderLengthDfs);
        log.debug("Word Ladder {} OK", () -> "ladderLengthDfs");
        */
    }

    /**
     * Luke - backtrack - BFS
     *
     * Runtime: 574 ms Beats 22.85%
     * Memory: 44.4 MB Beats 91.18%
     *
     * Time: O(size) * O(len ^ 2)
     * Space: O(size) * O(len)
     */
    public int ladderLengthBfs(final String beginWord, final String endWord, final List<String> wordList) {
        final int SIZE = wordList.size();
        final int LEN = beginWord.length();

        final boolean[] selected = new boolean[SIZE];

        if (!wordList.contains(endWord)) {
            return 0;
        }

        int idx = wordList.indexOf(beginWord);
        if (idx > -1) {
            selected[idx] = true;
        }

        if (isOneCharDiff(beginWord, endWord, LEN)) {
            return 2;
        }

        /**
         * BFS uses Queue or PriorityQueue
         */
        int level = 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        while (!queue.isEmpty()) {

            level++;

            int queueSize = queue.size();

            /**
             * Time 1: O(size)
             *
             * Time 1 and Time 2 combined is O(size), because once an element is selected, it will be skipped.
             */
            for (int i = 0; i < queueSize; i++) {

                String word = queue.poll();

                /**
                 * Time 2: O(size)
                 */
                for (int k = 0; k < SIZE; k++) {

                    if (!selected[k]) {
                        String target = wordList.get(k);
                        /**
                         * Time: O(word.len)
                         */
                        if (isOneCharDiff(word, target, LEN)) {

                            /**
                             * Found the end
                             *
                             * Time: O(word.len)
                             */
                            if (target.equals(endWord)) {
                                return level + 1;
                            }

                            queue.offer(target);
                            selected[k] = true;
                        }
                    }
                }
            }
        }

        /**
         * No matches found.
         */
        return 0;
    }

    /**
     * Luke - backtrack - DFS
     *
     * Time Limit Exceeded
     *
     * Time: O(size ^ 2) * O(len)
     * Space: O(size) * O(len)
     */
    public int ladderLengthDfs(final String beginWord, final String endWord, final List<String> wordList) {
        final int size = wordList.size();
        final int len = beginWord.length();

        final boolean[] selected = new boolean[size];

        final AtomicInteger a = new AtomicInteger();

        if (!wordList.contains(endWord)) {
            return 0;
        }

        int idx = wordList.indexOf(beginWord);
        if (idx > -1) {
            selected[idx] = true;
        }

        int level = backtrackDfs(0, beginWord, endWord, wordList.toArray(new String[0]), size, len, selected, a, 1);

        return level == 0 ? 0 : 1 + level;
    }

    private int backtrackDfs(
            final int idx,
            final String beginWord,
            final String endWord,
            // final List<String> wordList,
            final String wordList[],
            final int size,
            final int len,
            final boolean[] selected,
            final AtomicInteger a,
            final int level) {

        // start

        if (level > size) {
            return a.get();
        }

        if (a.get() > 0 && level > a.get()) {
            return a.get();
        }

        /**
         * Connected to endWord. Terminate backtrack.
         *
         * Time: O(len)
         */
        if (isOneCharDiff(beginWord, endWord, len)) {
            if (level < a.get() || a.get() == 0) {
                a.set(level);
            }
            return a.get();
        }

        /**
         * Time: O(size)
         */
        for (int i = 0; i < size; i++) {
            if (selected[i]) {
                continue;
            }

            String word = wordList[i];
            /**
             * Time: O(len)
             */
            if (isOneCharDiff(beginWord, word, len)) {
                selected[i] = true;

                /**
                 * Time: O(size)
                 */
                backtrackDfs(idx, word, endWord, wordList, size, len, selected, a, level + 1);

                selected[i] = false;
            }
        }

        return a.get();
    }

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
