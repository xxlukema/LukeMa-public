package com.learn.tree;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-310 Minimum Height Trees
 *
 * Medium
 *
 * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph
 * without simple cycles is a tree.
 *
 * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is
 * an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select
 * a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h)) are
 * called minimum height trees (MHTs).
 *
 * Return a list of all MHTs' root labels. You can return the answer in any order.
 *
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 *
 * Example 1:
 * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
 * Output: [1]
 * Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
 *
 * Example 2:
 * Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * Output: [3,4]
 *
 * Constraints:
 * 1 <= n <= 2 * 10 ^ 4
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * ai != bi
 * All the pairs (ai, bi) are distinct.
 * The given input is guaranteed to be a tree and there will be no repeated edges.
 */
@Log4j2
public class MinimumHeightTrees {

    public static void main(String[] args) {

        /**
         * Expected: [3, 4]
         */
        /*
        final int[][] edges = {
                { 3, 0 },
                { 3, 1 },
                { 3, 2 },
                { 3, 4 },
                { 5, 4 } };

        final int n = 6;
        */

        /**
         * Expected: [3]
         */
        final int[][] edges = {
                { 0, 1 },
                { 0, 2 },
                { 0, 3 },
                { 3, 4 },
                { 4, 5 } };

        final int n = 6;

        /**
         * Expected: [1]
         */
        /*
        final int[][] edges = {
                { 1, 0 },
                { 1, 2 },
                { 1, 3 } };

        final int n = 4;
        */

        MinimumHeightTrees minimumHeightTrees = new MinimumHeightTrees();

        var findMinHeightTreesBacktrack = minimumHeightTrees.findMinHeightTreesBacktrack(n, edges);
        log.debug("Minimum Height Trees: {}", () -> findMinHeightTreesBacktrack);
        log.debug("Minimum Height Trees {} OK", () -> "findMinHeightTreesBacktrack");

        var findMinHeightTreesLcRemoveLeavesLuke = minimumHeightTrees.findMinHeightTreesLcRemoveLeavesLuke(n, edges);
        Assertions.assertEquals(findMinHeightTreesBacktrack, findMinHeightTreesLcRemoveLeavesLuke);
        log.debug("Minimum Height Trees {} OK", () -> "findMinHeightTreesLcRemoveLeavesLuke");

        var findMinHeightTreesLcRemoveLeaves = minimumHeightTrees.findMinHeightTreesLcRemoveLeaves(n, edges);
        Assertions.assertEquals(findMinHeightTreesBacktrack, findMinHeightTreesLcRemoveLeaves);
        log.debug("Minimum Height Trees {} OK", () -> "findMinHeightTreesLcRemoveLeaves");

    }

    /**
     * Luke - Remove leaves until last one or two nodes left
     *      - Trick 1: BFS
     *      - Trick 2: For the tree-alike graph, the number of centroids is no more than 2.
     *
     * Runtime: 2695 ms Beats 5.5%
     * Memory: 60.7 MB Beats 61.24%
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public List<Integer> findMinHeightTreesLcRemoveLeavesLuke(int n, int[][] edges) {
        final LinkedList<Integer> result = new LinkedList<>();

        /**
         * one edge only
         */
        if (edges.length == 1) {
            result.add(edges[0][0]);
            result.add(edges[0][1]);

            return result;
        }

        final Map<Integer, Set<Integer>> adjMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            adjMap.put(i, new HashSet<>());
        }

        for (int[] edge : edges) {
            int a = edge[0], z = edge[1];
            adjMap.get(a).add(z);
            adjMap.get(z).add(a);
        }

        /**
         * Trick 1: BFD
         * Trick 2: For the tree-alike graph, the number of centroids is no more than 2.
         *
         * remove leaves until 1 or 2 members left
         */
        while (adjMap.size() > 2) {
            Set<Integer> keySet = adjMap.keySet();
            Set<Integer> keysToRemove = new HashSet<>();
            for (Integer key : keySet) {
                Set<Integer> neighbors = adjMap.get(key);
                if (neighbors.size() == 1) {
                    keysToRemove.add(key);
                }
            }

            for (Integer key : keysToRemove) {
                Set<Integer> neighbors = adjMap.get(key);
                Iterator<Integer> it = neighbors.iterator();
                Integer neighbor = it.next();

                adjMap.get(neighbor).remove(key);
                adjMap.remove(key);
            }
        }

        Set<Integer> keys = adjMap.keySet();
        result.addAll(keys);

        return result;
    }

    /**
     * LC - Remove Leaves
     *    - Trick 0: BFS
     *    - Trick 2: For the tree-alike graph, the number of centroids is no more than 2.
     *    - Trick 3: Leaf's parent is potentially new leaf for next round. Therefor, do not search for leaves from all vertices.
     *
     * Runtime: 49 ms Beats 57.91%
     * Memory: 60.7 MB Beats 61.24%
     *
     * Time: O(V)
     * Space: O(V)
     */
    public List<Integer> findMinHeightTreesLcRemoveLeaves(int n, int[][] edges) {

        final LinkedList<Integer> result = new LinkedList<>();

        /**
         * 1 or 2 nodes only
         */
        if (n < 2) {
            for (int i = 0; i < n; i++) {
                result.add(i);
            }

            return result;
        }

        final Map<Integer, Set<Integer>> adjMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            adjMap.put(i, new HashSet<>());
        }

        for (int[] edge : edges) {
            int a = edge[0], z = edge[1];
            adjMap.get(a).add(z);
            adjMap.get(z).add(a);
        }

        /**
         * Trick 1: BFD
         * Trick 2: For the tree-alike graph, the number of centroids is no more than 2.
         *
         * remove leaves until 1 or 2 members left
         */
        Set<Integer> keySet = adjMap.keySet();
        Set<Integer> keysToRemove = new HashSet<>();
        for (Integer key : keySet) {
            Set<Integer> neighbors = adjMap.get(key);
            if (neighbors.size() == 1) {
                keysToRemove.add(key);
            }
        }

        while (adjMap.size() > 2) {
            Set<Integer> newKeysToRemove = new HashSet<>();

            for (Integer key : keysToRemove) {
                Set<Integer> neighbors = adjMap.get(key);
                adjMap.remove(key);

                Iterator<Integer> it = neighbors.iterator();
                Integer neighbor = it.next();

                Set<Integer> neighborsNeighbors = adjMap.get(neighbor);
                neighborsNeighbors.remove(key);

                /**
                 * Trick 3: Leaf's parent is potentially new leaf for next round. Therefor, do not search for leaves from all vertices.
                 */
                if (neighborsNeighbors.size() == 1) {
                    newKeysToRemove.add(neighbor);
                }
            }

            keysToRemove = newKeysToRemove;
        }

        Set<Integer> keys = adjMap.keySet();
        result.addAll(keys);

        return result;
    }

    /**
     * Luke - Top Down
     *
     * Time Limit Exceeded
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public List<Integer> findMinHeightTreesBacktrack(int n, int[][] edges) {

        final LinkedList<Integer> result = new LinkedList<>();

        /**
         * one edge only
         */
        if (edges.length == 1) {
            result.add(edges[0][0]);
            result.add(edges[0][1]);

            return result;
        }

        final List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int a = edge[0], z = edge[1];
            adjList.get(a).add(z);
            adjList.get(z).add(a);
        }

        final int[] heights = new int[n];

        for (int i = 0; i < n; i++) {
            final boolean[] visited = new boolean[n];
            final Integer[] memo = new Integer[n];
            heights[i] = backtrack(adjList, i, 0, visited, memo);
        }

        // log.debug("--- heights: {}", heights);

        int min = IntStream.of(heights).min().getAsInt();
        for (int i = 0; i < n; i++) {
            if (heights[i] == min) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    private int backtrack(List<List<Integer>> adjList, int idx, int height, boolean[] visited, Integer[] memo) {
        if (memo[idx] != null) {
            return memo[idx];
        }

        height++;

        visited[idx] = true;

        int newHeight = 0;

        for (int neighbor : adjList.get(idx)) {
            /**
             * skip if (1) visited, or (2) leaf node
             */
            if (!visited[neighbor] && adjList.get(neighbor).size() > 1) {
                newHeight = Math.max(newHeight, backtrack(adjList, neighbor, height, visited, memo));
            }
        }

        return memo[idx] = Math.max(height, newHeight);
    }
}
