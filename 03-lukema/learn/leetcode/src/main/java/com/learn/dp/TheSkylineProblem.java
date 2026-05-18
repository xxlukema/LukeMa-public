package com.learn.dp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 218 - The Skyline Problem
 *
 * Hard
 *
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the
 * locations and heights of all the buildings, return the skyline formed by these buildings collectively.
 *
 * The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti, heighti]:
 *     lefti is the x coordinate of the left edge of the ith building.
 *     righti is the x coordinate of the right edge of the ith building.
 *     heighti is the height of the ith building.
 *
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 *
 * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x1,y1],[x2,y2],...]. Each key point is
 * the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate 0 and is used
 * to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part
 * of the skyline's contour.
 *
 * Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...,[2 3],[4 5],[7 5],[11 5],[12 7],...]
 * is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...,[2 3],[4 5],[12 7],...]
 *
 * Example 1:
 * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * Explanation:
 * Figure A shows the buildings of the input.
 * Figure B shows the skyline formed by those buildings. The red points in figure B represent the key points in the output list.
 *
 * Example 2:
 * Input: buildings = [[0,2,3],[2,5,3]]
 * Output: [[0,3],[5,0]]
 *
 * Constraints:
 *     1 <= buildings.length <= 104
 *     0 <= lefti < righti <= 2 ^ 31 - 1
 *     1 <= heighti <= 2 ^ 31 - 1
 *     buildings is sorted by lefti in non-decreasing or
 */
@Log4j2
public class TheSkylineProblem {

    public static void main(String[] args) {

        /**
         * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
         */
        /*
        final int[][] buildings = {
                { 2, 9, 10 },
                { 3, 7, 15 },
                { 5, 12, 12 },
                { 15, 20, 10 },
                { 19, 24, 8 } };
        */

        /**
         * Output: [[0,3],[5,0]]
         */
        /*
        final int[][] buildings = {
                { 0, 2, 3 },
                { 2, 5, 3 } };
        */

        /**
         * Output: [[2, 10], [9, 12], [12, 0]]
         */
        /*
        final int[][] buildings = {
                { 2, 9, 10 },
                { 9, 12, 15 } };
        */

        /**
         * Output: [[1, 3], [3, 0]]
         */
        /*
        final int[][] buildings = {
                { 1, 2, 1 },
                { 1, 2, 2 },
                { 1, 2, 3 },
                { 2, 3, 1 },
                { 2, 3, 2 },
                { 2, 3, 3 } };
        */

        /**
         * Output: [[1, 3], [3, 0]]
         */
        final int[][] buildings = {
                { 2, 4, 7 },
                { 2, 4, 5 },
                { 2, 4, 6 } };

        TheSkylineProblem theSkylineProblem = new TheSkylineProblem();

        var getSkylineLukeTwoPriorityQueue = theSkylineProblem.getSkylineLukeTwoPriorityQueue(buildings);
        log.debug("The Skyline Problem: {}", () -> getSkylineLukeTwoPriorityQueue);
        log.debug("The Skyline Problem {} OK", () -> "getSkylineLukeTwoPriorityQueue");
    }

    /**
     * Luke - Two PriorityQueue
     *
     * Runtime: 256 ms, faster than 39.00% of Java online submissions for The Skyline Problem.
     * Memory Usage: 52 MB, less than 58.81% of Java online submissions for The Skyline Problem.
     *
     * Time: O(N * log(N)), where PriorotyQueue add() takes time O(log(N))
     * Space: O(N)
     */
    public List<List<Integer>> getSkylineLukeTwoPriorityQueue(final int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();

        final int ROWS = buildings.length;

        record Node(int x, int start, int end, int height, boolean isStart) {

            @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + end;
                result = prime * result + start;
                return result;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;
                Node other = (Node) obj;
                if (end != other.end)
                    return false;
                if (start != other.start)
                    return false;
                return true;
            }

        }

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.x - b.x);

        PriorityQueue<Node> activeHighest = new PriorityQueue<>((a, b) -> b.height - a.height);

        for (int r = 0; r < ROWS; r++) {
            /**
             * Add to PriorityQuete takes time O(log(N))
             */
            pq.add(new Node(buildings[r][0], buildings[r][0], buildings[r][1], buildings[r][2], true));
            pq.add(new Node(buildings[r][1], buildings[r][0], buildings[r][1], buildings[r][2], false));
        }

        Deque<Node> deque = new LinkedList<>();

        while (!pq.isEmpty()) {

            Node curr = pq.poll();

            Node left = deque.peekLast();

            if (curr.isStart) {
                activeHighest.add(curr);
            } else {
                activeHighest.remove(curr);
            }

            if (left == null || left.height < curr.height) {
                if (curr.isStart) {
                    deque.add(curr);
                }
            }
            /**
             * left.height > curr.height
             */
            if (!curr.isStart) {
                if (activeHighest.isEmpty()) {
                    deque.add(new Node(curr.x, curr.start, curr.end, 0, curr.isStart));
                } else {
                    Node peekActive = activeHighest.peek();
                    if (curr.height > peekActive.height) {
                        deque.add(new Node(curr.x, curr.start, curr.end, peekActive.height, curr.isStart));
                    }
                }
            }
        }

        /**
         * Merge deque
         */

        Node curr = null;

        deque.add(null);

        Node last = null;

        while ((curr = deque.poll()) != null) {
            if (last != null) {
                if (curr.height == last.height) {
                    continue;
                }
            }

            Node next = deque.peekFirst();
            if (next != null) {
                if (curr.x == next.x) {
                    if (curr.height != next.height) {
                        continue;
                    }
                }
            }

            deque.add(curr);
            last = curr;
        }

        while (!deque.isEmpty()) {
            curr = deque.pollFirst();

            List<Integer> list = new ArrayList<>();
            result.add(list);
            list.add(curr.x);
            list.add(curr.height);
        }

        return result;
    }

    /**
     * LC - Two PriorityQueue
     */
    public List<List<Integer>> getSkylineLcTwoPriorityQueue(int[][] buildings) {
        // Iterate over all buildings, for each building = [left, right, height]
        // add (left, height) and (right, height) to 'edges'.
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < buildings.length; ++i) {
            edges.add(Arrays.asList(buildings[i][0], buildings[i][2]));
            edges.add(Arrays.asList(buildings[i][1], -buildings[i][2]));
        }

        /**
         * Time: O(N * Log(N))
         */
        Collections.sort(edges, (a, b) -> {
            return a.get(0) - b.get(0);
        });

        // Initailize two empty priority queues 'live' and 'past',
        // an empty list 'answer' to store the skyline key points.
        Queue<Integer> live = new PriorityQueue<>((a, b) -> b - a);

        Queue<Integer> past = new PriorityQueue<>((a, b) -> b - a);

        List<List<Integer>> answer = new ArrayList<>();

        int idx = 0;

        // Iterate over all the sorted edges.
        while (idx < edges.size()) {
            // Since we might have multiple edges at same x,
            // Let the 'currX' be the current position.
            int currX = edges.get(idx).get(0);

            // While we are handling the edges at 'currX':
            while (idx < edges.size() && edges.get(idx).get(0) == currX) {
                // The height of the current building.
                int height = edges.get(idx).get(1);

                // If this is a left edge, add `height` to 'live'.
                // Otherwise, add `height` to `past`.
                if (height > 0) {
                    live.offer(height);
                } else {
                    past.offer(-height);
                }
                idx++;
            }

            // If the tallest live building has been passed,
            // we remove it from 'live'.
            while (!past.isEmpty() && live.peek().equals(past.peek())) {
                live.remove();
                past.remove();
            }

            // Get the maximum height from 'live'.
            int currHeight = live.isEmpty() ? 0 : live.peek();

            // If the height changes at 'currX', we add this
            // skyline key point [currX, max_height] to 'answer'.
            if (answer.isEmpty() || answer.get(answer.size() - 1).get(1) != currHeight) {
                answer.add(Arrays.asList(currX, currHeight));
            }
        }

        // Return 'answer' as the skyline.
        return answer;
    }
}
