package com.learn.other;


import java.util.HashMap;
import java.util.Map;


/**
 * LC - 277 - Find The Celebrity
 *
 * Medium
 *
 * Suppose you are at a party with n people labeled from 0 to n - 1 and among them, there may exist one celebrity. The definition of a celebrity
 * is that all the other n - 1 people know the celebrity, but the celebrity does not know any of them.
 *
 * Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is ask questions
 * like: "Hi, A. Do you know B?" to get information about whether A knows B. You need to find out the celebrity (or verify there is not one) by
 * asking as few questions as possible (in the asymptotic sense).
 *
 * You are given a helper function bool knows(a, b) that tells you whether A knows B. Implement a function int findCelebrity(n). There will be
 * exactly one celebrity if they are at the party.
 *
 * Return the celebrity's label if there is a celebrity at the party. If there is no celebrity, return -1.
 *
 * Example 1:
 * Input: graph = [[1,1,0],[0,1,0],[1,1,1]]
 * Output: 1
 * Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means
 * person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
 *
 * Example 2:
 * Input: graph = [[1,0,1],[1,1,0],[0,1,1]]
 * Output: -1
 * Explanation: There is no celebrity.
 *
 * Constraints:
 *     n == graph.length == graph[i].length
 *     2 <= n <= 100
 *     graph[i][j] is 0 or 1.
 *     graph[i][i] == 1
 *
 * Follow up: If the maximum number of allowed calls to the API knows is 3 * n, could you find a solution without exceeding the maximum number of calls?
 */
public class FindTheCelebrity
    extends Relation {

    /**
     * LC - brute
     *
     * Runtime: 87 ms Beats 24.5%
     * Memory: 42.9 MB Beats 84.12%
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public int findCelebrityBrute(int n) {
        for (int i = 0; i < n; i++) {
            if (isCelebrity(i, n)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isCelebrity(int i, int numberOfPeople) {
        for (int k = 0; k < numberOfPeople; k++) {
            if (i == k) {
                continue; // Don't ask if they know themselves.
            }
            if (knows(i, k) || !knows(k, i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Luke - brute
     *
     * Runtime: 86 ms Beats 25.12%
     * Memory: 42.4 MB Beats 91.10%
     *
     * Time: O(n ^ 2)
     * Space; O(1)
     */
    public int findCelebrityLuke(final int n) {
        if (n < 2) {
            return -1;
        }

        for (int i = 0; i < n; i++) {
            if (isCelebrity(i, n)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * LC - With analysis
     *
     * Runtime: 20 ms Beats 86.5%
     * Memory: 53.8 MB Beats 63.85%
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int findCelebritySmart(final int n) {
        int candidate = 0;
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
        }

        if (isCelebrity(candidate, n)) {
            return candidate;
        } else {
            return -1;
        }
    }

}


class Relation {

    /**
     * LC - Improvement
     *
     * Use HashMap to remember knows()
     */

    Map<Pair, Boolean> map = new HashMap<>();

    boolean knows(int a, int b) {
        Pair pair = new Pair(a, b);
        if (map.get(pair) != null) {
            return map.get(pair);
        } else {
            /**
             * true/false does not matter for compiling purpose
             */
            boolean isKnow = true;
            map.put(pair, isKnow);
            return isKnow;
        }
    }

    /**
     * record Pair(int a, int b)
     */
    public record Pair(int a, int b) {
    }
}
