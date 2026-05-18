package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 210 - Course Schedule II
 *
 * Medium
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites
 * where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 *     For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is
 * impossible to finish all courses, return an empty array.
 *
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
 *
 * Example 2:
 * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2
 * should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
 *
 * Example 3:
 * Input: numCourses = 1, prerequisites = []
 * Output: [0]
 *
 * Constraints:
 *     1 <= numCourses <= 2000
 *     0 <= prerequisites.length <= numCourses * (numCourses - 1)
 *     prerequisites[i].length == 2
 *     0 <= ai, bi < numCourses
 *     ai != bi
 *     All the pairs [ai, bi] are distinct.
 */
@Log4j2
public class CourseScheduleII {

    public static void main(String[] args) {

        /**
         * Output: {0,2,1,3}
         */
        // final int numCourses = 4;
        // final int[][] prerequisites = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };

        /**
         * Output: []
         */
        // final int numCourses = 2;
        // final int[][] prerequisites = { { 1, 0 }, { 0, 1 } };

        /**
         * Output: {4, 6, 3, 5, 2, 0, 1, 7}
         */
        final int numCourses = 8;
        final int[][] prerequisites = {
                { 1, 2 },
                { 2, 3 },
                { 3, 4 },
                { 1, 5 },
                { 5, 3 },
                { 5, 6 },
                { 6, 4 } };

        CourseScheduleII courseScheduleII = new CourseScheduleII();

        var findOrderLcDfs = courseScheduleII.findOrderLcDfs(numCourses, prerequisites);
        log.debug("Course schedule II LC: {}", () -> findOrderLcDfs);
        log.debug("Course schedule II {} OK", () -> "findOrderLcDfs");

        var findOrderLukeDfs = courseScheduleII.findOrderLukeDfs(numCourses, prerequisites);
        log.debug("----> Course schedule II Luke: {}", () -> findOrderLukeDfs);
        log.debug("----> Course schedule II {} OK", () -> "findOrderLukeDfs");

        var findOrderLukeKahnIndegree = courseScheduleII.findOrderLukeKahnIndegree(numCourses, prerequisites);
        log.debug("Course schedule II Luke: {}", () -> findOrderLukeKahnIndegree);
        log.debug("Course schedule II {} OK", () -> "findOrderLukeKahnIndegree");

    }

    /**
     * Luke - Kahn Indegree
     *
     * Runtime: 7 ms, faster than 78.46% of Java online submissions for Course Schedule II.
     * Memory Usage: 43.3 MB, less than 94.43% of Java online submissions for Course Schedule II.
     *
     * Time: O(V + E)
     * Space: O(V + E)
     */
    public int[] findOrderLukeKahnIndegree(int numCourses, int[][] prerequisites) {

        /**
         * No depdency: retrun all the courses
         */
        if (prerequisites == null || prerequisites.length == 0 || (prerequisites.length == 1 && prerequisites[0].length == 0)) {
            int[] ret = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                ret[i] = i;
            }
            return ret;
        }

        /**
         * Dependency List
         *
         * Trick 1: Use array for faster access
         */
        final int[] topologicalOrderList = new int[numCourses];

        /**
         * Indegree
         */
        final int[] indegree = new int[numCourses];

        final Map<Integer, List<Integer>> adjacentcy = new HashMap<>();

        /**
         * Count indegrees
         */
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];

            List<Integer> list = adjacentcy.get(src);
            if (list == null) {
                list = new ArrayList<>();
                adjacentcy.put(src, list);
            }

            list.add(dest);

            /**
             * Trick 2: Add dest's indegree. Not src's indegree.
             */
            indegree[dest]++;
        }

        Queue<Integer> queue = new ConcurrentLinkedQueue<>();

        /**
         * Add zero indegree nodes to queue
         */
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        int i = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            topologicalOrderList[i++] = node;

            if (adjacentcy.containsKey(node)) {
                List<Integer> dests = adjacentcy.get(node);
                dests.forEach(dest -> {
                    indegree[dest]--;

                    if (indegree[dest] == 0) {
                        queue.add(dest);
                    }
                });
            }
        }

        /**
         * Trick 3: If it is cyclic, "i" will be less than "numCourses".
         */
        if (i == numCourses) {
            return topologicalOrderList;
        } else {
            return new int[0];
        }
    }

    /**
     * Luke - DFS
     *
     * Runtime: 69 ms, faster than 5.03% of Java online submissions for Course Schedule II.
     * Memory Usage: 50.3 MB, less than 30.06% of Java online submissions for Course Schedule II.
     *
     * Time: O(N + V)
     * Space: O(N + E)
     */
    public int[] findOrderLukeDfs(int numCourses, int[][] prerequisites) {

        /**
         * No depdency: retrun all the courses
         */
        if (prerequisites == null || prerequisites.length == 0 || (prerequisites.length == 1 && prerequisites[0].length == 0)) {
            int[] ret = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                ret[i] = i;
            }
            return ret;
        }

        /**
         * Build dependency map
         *
         * Trick 1: src is key, where src is prerequisite.
         */
        final Map<Integer, List<Integer>> mapSrcToDestAdjacency = new HashMap<>();

        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];

            if (!mapSrcToDestAdjacency.containsKey(src)) {
                mapSrcToDestAdjacency.put(src, new ArrayList<>());
            }

            mapSrcToDestAdjacency.get(src).add(dest);
        }

        Set<Integer> srcs = mapSrcToDestAdjacency.keySet();

        final Map<Integer, VertexState> vertexState = new HashMap<>();

        for (Integer src : srcs) {
            vertexState.put(src, VertexState.Unvisited);
        }

        /**
         * Dependency List
         */
        final LinkedList<Integer> topologicalOrderList = new LinkedList<>();

        /**
         * DFS
         */
        for (Integer key : srcs) {
            boolean isPossibleBecauseNoCyclicDetected = dfsLuke(mapSrcToDestAdjacency, key, topologicalOrderList, vertexState);
            if (!isPossibleBecauseNoCyclicDetected) {
                return new int[0];
            }
        }

        /**
         * Add remaining courses to the end of list
         *
         * Time: O(N * ROWS)
         */
        List<Integer> remainings = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!topologicalOrderList.contains(i)) {
                remainings.add(i);
            }
        }

        topologicalOrderList.addAll(remainings);

        return topologicalOrderList.stream().mapToInt(Integer::intValue).toArray();
    }

    boolean dfsLuke(
            final Map<Integer, List<Integer>> mapSrcToDestAdjacency,
            final Integer src,
            final LinkedList<Integer> topologicalOrderList,
            final Map<Integer, VertexState> vertexState) {

        if (mapSrcToDestAdjacency.containsKey(src)) {

            VertexState state = vertexState.get(src);

            switch (state) {
                case Seen:
                    /**
                     * Cyclic
                     */
                    return false;
                case Unvisited:
                    /**
                     * Start visit
                     */
                    vertexState.put(src, VertexState.Seen);

                    List<Integer> destList = mapSrcToDestAdjacency.get(src);

                    if (destList != null) {

                        for (Integer dest : destList) {
                            boolean isPossibleBecauseNoCyclicDetected = dfsLuke(mapSrcToDestAdjacency, dest, topologicalOrderList, vertexState);
                            if (!isPossibleBecauseNoCyclicDetected) {
                                return false;
                            }
                        }
                    } else {
                        topologicalOrderList.addFirst(src);
                    }

                    /**
                     * Bottom-Up: This is must. Why it cannot be done from Top-Down???
                     *
                     * Trick 2: Add src after recursion
                     */
                    topologicalOrderList.addFirst(src);
                    vertexState.put(src, VertexState.Visited);

                    break;
                case Visited:
                default:
                    /**
                     * Do nothing.
                     */
                    break;
            }
        } else {
            /**
             * Trick 3: Add final dest to the list
             */
            if (!topologicalOrderList.contains(src)) {
                topologicalOrderList.addLast(src);
            }
        }

        return true;
    }

    /**
     * LC - DFS
     *
     * Runtime: 5 ms, faster than 93.01% of Java online submissions for Course Schedule II.
     * Memory Usage: 43 MB, less than 97.21% of Java online submissions for Course Schedule II.
     *
     * Time: O(V + E) where V represents the number of vertices and E represents the number of edges. Essentially we iterate through
     *                each node and each vertex in the graph once and only once.
     * Space: O(V + E)
     */
    static int Unvisited = 1;
    static int Seen = 2;
    static int Visited = 3;

    boolean isCyclic;
    Map<Integer, Integer> vertexState;
    Map<Integer, List<Integer>> adjList;
    List<Integer> topologicalOrder;

    private void init(int numCourses) {
        this.isCyclic = false;
        this.vertexState = new HashMap<Integer, Integer>();
        this.adjList = new HashMap<Integer, List<Integer>>();
        this.topologicalOrder = new ArrayList<Integer>();

        // By default all vertces are WHITE
        for (int i = 0; i < numCourses; i++) {
            this.vertexState.put(i, Unvisited);
        }
    }

    private void dfs(int src) {

        // Don't recurse further if we found a cycle already
        if (this.isCyclic) {
            return;
        }

        // Start the recursion
        this.vertexState.put(src, Seen);

        // Traverse on neighboring vertices
        for (Integer dest : this.adjList.getOrDefault(src, new ArrayList<Integer>())) {
            if (this.vertexState.get(dest) == Unvisited) {
                this.dfs(dest);
            } else if (this.vertexState.get(dest) == Seen) {
                // An edge to a GRAY vertex represents a cycle
                this.isCyclic = true;
            }
        }

        // Recursion ends. We mark it as black
        this.vertexState.put(src, Visited);
        this.topologicalOrder.add(src);
    }

    public int[] findOrderLcDfs(int numCourses, int[][] prerequisites) {

        this.init(numCourses);

        // Create the adjacency list representation of the graph
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];
            List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
            lst.add(dest);
            adjList.put(src, lst);
        }

        // If the node is unprocessed, then call dfs on it.
        for (int i = 0; i < numCourses; i++) {
            if (this.vertexState.get(i) == Unvisited) {
                this.dfs(i);
            }
        }

        log.debug("this.topologicalOrder: {}", this.topologicalOrder);

        int[] order;

        if (this.isCyclic) {
            order = new int[0];
        } else {
            order = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                order[i] = this.topologicalOrder.get(numCourses - i - 1);
            }
        }

        return order;
    }

    /**
     * LC - Kahn - Indegree
     *
     * Time: O(V + E)
     * Space: O(V + E)
     */
    public int[] findOrderLcIndegree(int numCourses, int[][] prerequisites) {

        final Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
        final int[] indegree = new int[numCourses];
        final int[] topologicalOrder = new int[numCourses];

        // Create the adjacency list representation of the graph
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];
            List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
            lst.add(dest);
            adjList.put(src, lst);

            // Record in-degree of each vertex
            indegree[dest] += 1;
        }

        // Add all vertices with 0 in-degree to the queue
        Queue<Integer> queueZeroIndegree = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queueZeroIndegree.add(i);
            }
        }

        int i = 0;
        // Process until the Q becomes empty
        while (!queueZeroIndegree.isEmpty()) {
            int node = queueZeroIndegree.poll();
            topologicalOrder[i++] = node;

            // Reduce the in-degree of each neighbor by 1
            if (adjList.containsKey(node)) {
                for (Integer neighbor : adjList.get(node)) {
                    indegree[neighbor]--;

                    // If in-degree of a neighbor becomes 0, add it to the Q
                    if (indegree[neighbor] == 0) {
                        queueZeroIndegree.add(neighbor);
                    }
                }
            }
        }

        // Check to see if topological sort is possible or not.
        if (i == numCourses) {
            return topologicalOrder;
        } else {
            return new int[0];
        }
    }
}


enum VertexState {
    Unvisited, Seen, Visited;
}
