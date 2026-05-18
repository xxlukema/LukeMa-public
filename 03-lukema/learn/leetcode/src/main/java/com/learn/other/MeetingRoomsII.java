package com.learn.other;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC-253 Meeting Rooms II
 *
 * Medium
 *
 * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.
 *
 * Example 1:
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 *
 * Example 2:
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 *
 * Constraints:
 *     1 <= intervals.length <= 10 ^ 4
 *     0 <= starti < endi <= 10 ^ 6
 */
@Log4j2
public class MeetingRoomsII {

    public static void main(String[] args) {

        /**
         * Expected: 2
         */
        // final int[][] intervals = { { 0, 30 }, { 5, 10 }, { 15, 20 } };

        /**
         * Expected: 1
         */
        final int[][] intervals = { { 7, 10 }, { 2, 4 } };

        MeetingRoomsII meetingRoomsII = new MeetingRoomsII();

        var minMeetingRoomsLinkedList = meetingRoomsII.minMeetingRoomsLinkedList(intervals);
        log.debug("Meeting Rooms II: {}", () -> minMeetingRoomsLinkedList);
        log.debug("Meeting Rooms II {} OK", () -> "minMeetingRoomsLinkedList");

        var minMeetingRoomsSortArray = meetingRoomsII.minMeetingRoomsSortArray(intervals);
        Assertions.assertEquals(minMeetingRoomsLinkedList, minMeetingRoomsSortArray);
        log.debug("Meeting Rooms II {} OK", () -> "minMeetingRoomsSortArray");

    }

    /**
     * LC - (1) sort meetings on start time
     *      (2) build PriorityQueue sorted with meeting end time
     *
     * Runtime: 9 ms Beats 77.49%
     * Memory: 41.9 MB Beats 96.35%
     *
     * Time: O(N log(N))
     * Space: O(N)
     */
    public int minMeetingRoomsLinkedList(int[][] intervals) {
        /**
         * 1. sort meeting on start time
         */
        final int LEN = intervals.length;

        LinkedList<int[]> llist = new LinkedList<>();

        for (int i = 0; i < LEN; i++) {
            llist.add(intervals[i]);
        }

        /**
         * Time: O(N log(N))
         * Space: O(N)
         */
        llist.sort((a, b) -> a[0] - b[0]);

        /**
         * 2. build a min-PriorityQueue (min-heap) with meeting end time
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        /**
         * 3. schedule
         */

        int overlaps = 0;

        while (!llist.isEmpty()) {
            int[] cur = llist.removeFirst();
            while (!pq.isEmpty() && pq.peek()[1] <= cur[0]) {
                pq.poll();
            }

            /**
             * Time: O(N log(N))
             * Space: O(N)
             */
            pq.offer(cur);
            overlaps = Math.max(overlaps, pq.size());
        }

        return overlaps;
    }

    /**
     * LC - (1) sort meetings on start time
     *      (2) build PriorityQueue sorted with meeting end time
     *
     * Runtime: 7 ms Beats 84.84%
     * Memory: 42 MB Beats 96.35%
     *
     * Time: O(N log(N))
     * Space: O(N)
     */
    public int minMeetingRoomsSortArray(int[][] intervals) {
        /**
         * 1. sort meeting on start time
         *
         * Time: O(N log(N))
         * Space: O(N)
         */
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        /**
         * 2. build a min-PriorityQueue (min-heap) with meeting end time
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        /**
         * 3. schedule
         */

        int overlaps = 0;

        /**
         * Time: O(N) * O(log(N))
         * Space: O(N)
         */
        for (int i = 0, len = intervals.length; i < len; i++) {
            int[] cur = intervals[i];
            while (!pq.isEmpty() && pq.peek()[1] <= cur[0]) {
                pq.poll();
            }

            /**
             * Time: O(log(N))
             * Space: O(N)
             */
            pq.offer(cur);
            overlaps = Math.max(overlaps, pq.size());
        }

        return overlaps;
    }

}
