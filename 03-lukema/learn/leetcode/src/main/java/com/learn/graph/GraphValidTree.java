package com.learn.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-261 Graph Valid Tree
 *
 * Medium
 *
 * You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where edges[i] = [ai, bi] indicates
 * that there is an undirected edge between nodes ai and bi in the graph.
 *
 * Return true if the edges of the given graph make up a valid tree, and false otherwise.
 *
 * Example 1:
 * Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
 * Output: true
 *
 * Example 2:
 * Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
 * Output: false
 *
 * Constraints:
 *     1 <= n <= 2000
 *     0 <= edges.length <= 5000
 *     edges[i].length == 2
 *     0 <= ai, bi < n
 *     ai != bi
 *     There are no self-loops or repeated edges.
 */
@Log4j2
public class GraphValidTree {

    public static void main(String[] args) {

        /**
         * Expected: true
         */
        // final int n = 4;
        // final int[][] edges = { { 0, 1 }, { 2, 3 }, { 1, 2 } };

        /**
         * Expected: true
         */
        // final int n = 5;
        // final int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };

        /**
         * Expected: false
         */
        final int n = 4;
        final int[][] edges = { { 2, 3 }, { 1, 2 }, { 1, 3 } };

        /**
         * Expected: false
         */
        // final int n = 5;
        // final int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 } };
        // final int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 }, { 1, 4 } };

        GraphValidTree graphValidTree = new GraphValidTree();

        var validTreeLukeBrute = graphValidTree.validTreeLukeBrute(n, edges);
        log.debug("Graph Valid Tree: {}", () -> validTreeLukeBrute);
        log.debug("Graph Valid Tree {} OK", () -> "validTreeLukeBrute");

        var validTreeAdjListDfs = graphValidTree.validTreeAdjListDfs(n, edges);
        Assertions.assertEquals(validTreeLukeBrute, validTreeAdjListDfs);
        log.debug("Graph Valid Tree {} OK", () -> "validTreeAdjListDfs");

        var validTreeAdjListBfs = graphValidTree.validTreeAdjListBfs(n, edges);
        Assertions.assertEquals(validTreeLukeBrute, validTreeAdjListBfs);
        log.debug("Graph Valid Tree {} OK", () -> "validTreeAdjListBfs");

        var validTreeAdjListDfsRecursion = graphValidTree.validTreeAdjListDfsRecursion(n, edges);
        Assertions.assertEquals(validTreeLukeBrute, validTreeAdjListDfsRecursion);
        log.debug("Graph Valid Tree {} OK", () -> "validTreeAdjListDfsRecursion");

        var validTreeUnionFind = graphValidTree.validTreeUnionFind(n, edges);
        Assertions.assertEquals(validTreeLukeBrute, validTreeUnionFind);
        log.debug("Graph Valid Tree {} OK", () -> "validTreeUnionFind");

    }

    /**
     * LC - adjList - Theorems: (1) Any node can be root.
     *                          (2) All nodes are connected.
     *                          (3) There are n nodes and n - 1 edges.
     *                          (4) Start from one node, traverse all neighbor list. If there are non-visited nodes, the un-visited nodes are on a different graph.
     *                          (5) Cyclic detection: No need to detect cyclic. If in seen, do not re-visit.
     *
     * Runtime: 2 ms Beats 76.18%
     * Memory: 43.1 MB Beats 81.1%
     *
     * Time: O(N + E)
     * Space: O(N + E)
     */
    public boolean validTreeAdjListDfs(int n, int[][] edges) {
        /**
         * check number of edges and node
         */
        if (edges.length != n - 1) {
            return false;
        }

        if (n == 1) {
            return true;
        }

        final Map<Integer, List<Integer>> adjMap = new HashMap<>();

        for (int[] edge : edges) {
            if (adjMap.get(edge[0]) == null) {
                adjMap.put(edge[0], new ArrayList<>());
            }
            adjMap.get(edge[0]).add(edge[1]);
            if (adjMap.get(edge[1]) == null) {
                adjMap.put(edge[1], new ArrayList<>());
            }
            adjMap.get(edge[1]).add(edge[0]);
        }

        final Set<Integer> seen = new HashSet<>();

        Stack<Integer> stack = new Stack<>();

        /**
         * start from any vertex
         */
        stack.push(edges[0][0]);
        seen.add(edges[0][0]);

        while (!stack.isEmpty()) {
            Integer cur = stack.pop();
            List<Integer> neighbors = adjMap.get(cur);
            for (Integer neighbor : neighbors) {
                if (seen.contains(neighbor)) {
                    continue;
                } else {
                    seen.add(neighbor);
                    stack.add(neighbor);
                }
            }
        }

        return seen.size() == n;
    }

    /**
     * LC - adjMap
     * @see #validTreeAdjListDfs()
     *
     * Runtime: 6 ms Beats 50.29%
     * Memory: 46.8 MB Beats 45.27%
     *
     * Time: O(N + E)
     * Space: O(N + E)
     */
    public boolean validTreeAdjListBfs(int n, int[][] edges) {
        /**
         * check number of edges and node
         */
        if (edges.length != n - 1) {
            return false;
        }

        if (n == 1) {
            return true;
        }

        final Map<Integer, List<Integer>> adjMap = new HashMap<>();

        for (int[] edge : edges) {
            if (adjMap.get(edge[0]) == null) {
                adjMap.put(edge[0], new ArrayList<>());
            }
            adjMap.get(edge[0]).add(edge[1]);
            if (adjMap.get(edge[1]) == null) {
                adjMap.put(edge[1], new ArrayList<>());
            }
            adjMap.get(edge[1]).add(edge[0]);
        }

        final Set<Integer> seen = new HashSet<>();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(edges[0][0]);
        seen.add(edges[0][0]);

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            List<Integer> neighbors = adjMap.get(cur);
            for (Integer neighbor : neighbors) {
                if (seen.contains(neighbor)) {
                    continue;
                } else {
                    seen.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return seen.size() == n;
    }

    /**
     * LC - adjMap - Recursion - (Not perferred. Prefer iterative version)
     *
     * @see #validTreeAdjListDfs()
     *
     * Runtime: 2 ms Beats 76.18%
     * Memory: 42.4 MB Beats 92.80%
     *
     * Time: O(N + E)
     * Space: O(N + E)
     */
    public boolean validTreeAdjListDfsRecursion(int n, int[][] edges) {
        /**
         * check number of edges and node
         */
        if (edges.length != n - 1) {
            return false;
        }

        if (n == 1) {
            return true;
        }

        final Map<Integer, List<Integer>> adjMap = new HashMap<>();

        for (int[] edge : edges) {
            if (adjMap.get(edge[0]) == null) {
                adjMap.put(edge[0], new ArrayList<>());
            }
            adjMap.get(edge[0]).add(edge[1]);
            if (adjMap.get(edge[1]) == null) {
                adjMap.put(edge[1], new ArrayList<>());
            }
            adjMap.get(edge[1]).add(edge[0]);
        }

        final Set<Integer> seen = new HashSet<>();
        seen.add(edges[0][0]);

        dfs(edges[0][0], adjMap, seen);

        return seen.size() == n;
    }

    private void dfs(int cur, final Map<Integer, List<Integer>> adjMap, final Set<Integer> seen) {
        List<Integer> neighbors = adjMap.get(cur);
        for (Integer neighbor : neighbors) {
            if (seen.contains(neighbor)) {
                continue;
            } else {
                seen.add(neighbor);
                dfs(neighbor, adjMap, seen);
            }
        }
    }

    /**
     * Luke - Brute - (1) remove leaf edges (2) repeat
     *
     * Runtime4 ms Beats 62.14%
     * Memory47.2 MB Beats 39.27%
     *
     * Time: O(N ^ 2)
     * Space: O(N)
     */
    public boolean validTreeLukeBrute(int n, int[][] edges) {
        /**
         * check number of edges and node
         */
        if (edges.length != n - 1) {
            return false;
        }

        /**
         * create vertex map
         */
        final Map<Integer, List<int[]>> map = new HashMap<>();

        /**
         * Time: O(N)
         * Space: O(N)
         */
        for (int[] edge : edges) {
            if (map.get(edge[0]) == null) {
                map.put(edge[0], new ArrayList<>());
            }
            map.get(edge[0]).add(edge);

            if (map.get(edge[1]) == null) {
                map.put(edge[1], new ArrayList<>());
            }
            map.get(edge[1]).add(edge);
        }

        /**
         * remove leaves
         */
        final boolean[] removed = new boolean[edges.length];

        /**
         * Time: O(N ^ 2)
         */
        while (!map.isEmpty()) {
            boolean isCyclic = true;
            for (int i = 0; i < edges.length; i++) {
                boolean deleted = false;

                if (removed[i]) {
                    continue;
                }

                if (map.get(edges[i][0]).size() == 1) {
                    map.remove(edges[i][0]);

                    if (map.get(edges[i][1]).size() == 1) {
                        if (map.size() == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        map.get(edges[i][1]).remove(edges[i]);
                        deleted = true;

                        // log.debug("--- removed: {}", edges[i]);
                    }
                } else if (map.get(edges[i][1]).size() == 1) {
                    map.remove(edges[i][1]);

                    if (map.get(edges[i][0]).size() == 1) {
                        if (map.size() == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        map.get(edges[i][0]).remove(edges[i]);
                        deleted = true;

                        // log.debug("=== removed: {}", edges[i]);
                    }
                }

                if (deleted) {
                    removed[i] = true;
                    isCyclic = false;
                }

                // log.debug("       map: {}, deleted: {}", map, deleted);
            }

            if (isCyclic) {
                return false;
            }
        }

        return true;
    }

    /**
     * LC - Union Find
     *
     * Not Optimized:
     *
     * Runtime: 5 ms Beats 56.64%
     * Memory: 46.2 MB Beats 65.55%
     * Time: O(N ^ 2)
     *
     * vs Optimized
     *
     * Runtime: 1 ms Beats 94.15%
     * Memory: 46.3 MB Beats 65.55%
     *
     * Time: O(N)
     * Space: O(N)
     */
    public boolean validTreeUnionFind(int n, int[][] edges) {
        /**
         * check number of edges and node
         */
        if (edges.length != n - 1) {
            return false;
        }

        if (n == 1) {
            return true;
        }

        // UnionFind unionfind = new UnionFind(n);
        UnionFindOptimized unionfind = new UnionFindOptimized(n);

        for (int[] edge : edges) {
            if (!unionfind.union(edge[0], edge[1])) {
                /**
                 * cycle detected.
                 */
                return false;
            }
        }

        /*
        int representative = unionfind.find(edges[0][0]);

        for (int[] edge : edges) {
            if (unionfind.find(edge[0]) != representative) {
                return false;
            }
        }
        */

        return true;
    }
}


class UnionFind {

    private int[] parent;

    // For efficiency, we aren't using makeset, but instead initialising
    // all the sets at the same time in the constructor.
    public UnionFind(int n) {
        parent = new int[n];
        for (int node = 0; node < n; node++) {
            parent[node] = node;
        }
    }

    // The find method, without any optimizations. It traces up the parent
    // links until it finds the root node for A, and returns that root.
    public int find(int a) {
        while (parent[a] != a) {
            a = parent[a];
        }
        return a;
    }

    // The union method, without any optimizations. It returns True if a
    // merge happened, False if otherwise.
    public boolean union(int a, int b) {
        // Find the roots for a and b.
        int rootA = find(a);
        int rootB = find(b);
        // Check if A and B are already in the same set.
        if (rootA == rootB) {
            return false;
        }
        // Merge the sets containing A and B.
        parent[rootA] = rootB;
        return true;
    }
}


class UnionFindOptimized {

    private int[] parent;
    private int[] size; // We use this to keep track of the size of each set.

    // For efficiency, we aren't using makeset, but instead initialising
    // all the sets at the same time in the constructor.
    public UnionFindOptimized(int n) {
        parent = new int[n];
        size = new int[n];
        for (int node = 0; node < n; node++) {
            parent[node] = node;
            size[node] = 1;
        }
    }

    /**
     * Recursive
     */
    public int find(int a) {
        if (a == parent[a]) {
            return a;
        } else {
            int root = find(parent[a]);
            parent[a] = root;

            return root;
        }
    }

    /**
     * Iterative
     */
    // The find method, with path compression. There are ways of implementing
    // this elegantly with recursion, but the iterative version is easier for
    // most people to understand!
    public int find2(int a) {
        // Step 1: Find the root.
        int root = a;
        while (parent[root] != root) {
            root = parent[root];
        }
        // Step 2: Do a second traversal, this time setting each node to point
        // directly at A as we go.
        while (a != root) {
            int oldRoot = parent[a];
            parent[a] = root;
            a = oldRoot;
        }
        return root;
    }

    // The union method, with optimization union by size. It returns True if a
    // merge happened, False if otherwise.
    public boolean union(int A, int B) {
        // Find the roots for A and B.
        int rootA = find(A);
        int rootB = find(B);
        // Check if A and B are already in the same set.
        if (rootA == rootB) {
            return false;
        }
        // We want to ensure the larger set remains the root.
        if (size[rootA] < size[rootB]) {
            // Make rootB the overall root.
            parent[rootA] = rootB;
            // The size of the set rooted at B is the sum of the 2.
            size[rootB] += size[rootA];
        } else {
            // Make rootA the overall root.
            parent[rootB] = rootA;
            // The size of the set rooted at A is the sum of the 2.
            size[rootA] += size[rootB];
        }
        return true;
    }
}
