package com.learn.other;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC-252 Meeting Rooms
 *
 * Easy
 *
 * Given an array of meeting time intervals where intervals[i] = [starti, endi], determine if a person could attend all meetings.
 *
 * Example 1:
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: false
 *
 * Example 2:
 * Input: intervals = [[7,10],[2,4]]
 * Output: true
 *
 * Constraints:
 *     0 <= intervals.length <= 10 ^ 4
 *     intervals[i].length == 2
 *     0 <= starti < endi <= 10 ^ 6
 */
@Log4j2
public class MeetingRooms {

    public static void main(String[] args) {

        /**
         * Expected: false
         */
        // final int[][] intervals = { { 0, 30 }, { 5, 10 }, { 15, 20 } };

        /**
         * Expected: true
         */
        // final int[][] intervals = { { 7, 10 }, { 2, 4 } };

        /**
         * Expected: false
         */
        final int[][] intervals = { { 5, 8 }, { 6, 8 } };

        MeetingRooms meetingRooms = new MeetingRooms();

        var canAttendMeetingsBrute = meetingRooms.canAttendMeetingsBrute(intervals);
        log.debug("Meeting Rooms: {}", () -> canAttendMeetingsBrute);
        log.debug("Meeting Rooms {} OK", () -> "canAttendMeetingsBrute");

    }

    /**
     * Luke - brute
     *
     * Runtime: 478 ms Beats 5.3%
     * Memory: 44.8 MB Beats 62.74%
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public boolean canAttendMeetingsBrute(int[][] intervals) {
        for (int row = 0, len = intervals.length; row < len; row++) {
            int[] cur = intervals[row];
            for (int k = row + 1; k < len; k++) {
                if (cur[0] >= intervals[k][1] || cur[1] <= intervals[k][0]) {
                    continue;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Luke - Sort (TimSort)
     *
     * Runtime: 15 ms Beats 43.82%
     * Memory: 45.4 MB Beats 16.22%
     *
     * Time: O(N log(N)) for TimSort, O(N) for RadixSort
     * Space: O(N)
     */
    public boolean canAttendMeetingsSort(int[][] intervals) {

        /**
         * 1. Sort
         */
        List<int[]> list = new ArrayList<>();

        for (int row = 0, len = intervals.length; row < len; row++) {
            list.add(intervals[row]);
        }

        /**
         * Time: O(N log(N))
         * Space: O(N)
         */
        list.sort((a, b) -> a[0] - b[0]);

        /**
         * 2. walk the list
         *
         * Time: O(N)
         */
        for (int row = 0, len = list.size() - 1; row < len; row++) {
            if (list.get(row)[1] > list.get(row + 1)[0]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Not in-use
     */
    boolean hasOverlap(final int[] a, final int[] b) {
        if (a[0] >= b[1] || a[1] <= b[0]) {
            return false;
        } else {
            return true;
        }
    }
}
