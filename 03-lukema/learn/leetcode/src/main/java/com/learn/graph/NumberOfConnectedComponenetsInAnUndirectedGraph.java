package com.learn.graph;


import com.learn.graph.dsu.DisjointSetUnionIntArr;

import lombok.extern.log4j.Log4j2;


/**
 * LC-323 Number of Connected Componenets in an Undirected Graph
 *
 * Medium
 *
 * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.
 *
 * Return the number of connected components in the graph.
 *
 * Example 1:
 * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
 * Output: 2
 *
 * Example 2:
 * Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
 * Output: 1
 *
 * Constraints:
 *     1 <= n <= 2000
 *     1 <= edges.length <= 5000
 *     edges[i].length == 2
 *     0 <= ai <= bi < n
 *     ai != bi
 *     There are no repeated edges.
 */
@Log4j2
public class NumberOfConnectedComponenetsInAnUndirectedGraph {

    public static void main(String[] args) {

        /**
         * expected: 1
         */
        // final int n = 5;
        // final int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 } };

        /**
         * expected: 2
         */
        // final int n = 5;
        // final int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };

        /**
         * expected: 2
         */
        final int n = 4;
        final int[][] edges = { { 2, 3 }, { 1, 2 }, { 1, 3 } };

        NumberOfConnectedComponenetsInAnUndirectedGraph numberOfConnectedComponenetsInAnUndirectedGraph = new NumberOfConnectedComponenetsInAnUndirectedGraph();

        var countComponentsLuke = numberOfConnectedComponenetsInAnUndirectedGraph.countComponentsLuke(n, edges);
        log.debug("Number of Connected Componenets in an Undirected Graph: {}", () -> countComponentsLuke);
        log.debug("Number of Connected Componenets in an Undirected Graph {} OK", () -> "countComponentsLuke");

        var countComponentsLc = numberOfConnectedComponenetsInAnUndirectedGraph.countComponentsLc(n, edges);
        log.debug("Number of Connected Componenets in an Undirected Graph: {}", () -> countComponentsLc);
        log.debug("Number of Connected Componenets in an Undirected Graph {} OK", () -> "countComponentsLc");

    }

    /**
     * Luke - DisjointSetUnion
     *
     * Runtime: 2 ms Beats 71.94%
     * Memory: 42.8 MB Beats 38.51%
     *
     * Time: O(N)
     * Space: O(N + E)
     */
    public int countComponentsLuke(int n, int[][] edges) {

        DisjointSetUnionIntArr disjointSetUnionIntArr = new DisjointSetUnionIntArr(n);

        for (int i = 0; i < n; i++) {
            disjointSetUnionIntArr.add(i);
        }

        for (int i = 0; i < edges.length; i++) {
            disjointSetUnionIntArr.uion(edges[i][0], edges[i][1]);
        }

        return disjointSetUnionIntArr.getSize();
    }

    /**
     * LC - UnionFind
     *
     */

    int[] parents;
    int size = 0;

    public int countComponentsLc(int n, int[][] edges) {

        parents = new int[n];

        for (int i = 0; i < n; i++) {
            parents[i] = i;
            size++;
        }

        for (int i = 0; i < edges.length; i++) {
            union(edges[i][0], edges[i][1]);
        }

        return size;
    }

    int find(int i) {

        int parent = parents[i];

        while (parent != parents[parent]) {
            parents[parent] = parents[parents[parent]];
            parent = parents[parent];
        }

        return parent;
    }

    boolean union(int dest, int src) {
        int parentDest = find(dest);
        int parentSrc = find(src);

        if (parentDest == parentSrc) {
            return false;
        } else {
            while (parents[src] == parentSrc) {
                int grandParent = parents[parents[src]];
                parents[src] = parentDest;
                src = grandParent;
            }

            size--;
            return true;
        }
    }

}
