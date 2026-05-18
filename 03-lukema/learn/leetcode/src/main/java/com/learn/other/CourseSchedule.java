package com.learn.other;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 207 - Course Schedule
 *
 * Medium
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites
 * where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 *     For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return true if you can finish all courses. Otherwise, return false.
 *
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 *
 * Example 2:
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 *
 * Constraints:
 *     1 <= numCourses <= 2000
 *     0 <= prerequisites.length <= 5000
 *     prerequisites[i].length == 2
 *     0 <= ai, bi < numCourses
 *     All the pairs prerequisites[i] are unique.
 */
@Log4j2
public class CourseSchedule {

    public static void main(String[] args) {

        /**
         * Output: false
         */
        // final int numCourses = 2;
        // final int[][] prerequisites = { { 1, 0 }, { 0, 1 } };

        /**
         * Output: false
         */
        // final int numCourses = 3;
        // final int[][] prerequisites = { { 1, 0 }, { 1, 2 }, { 0, 1 } };

        /**
         * Output: true
         */
        // final int numCourses = 2;
        // final int[][] prerequisites = { { 1, 0 } };

        /**
         * Output: false
         */
        final int numCourses = 2;
        final int[][] prerequisites = { {} };

        CourseSchedule courseSchedule = new CourseSchedule();

        var ret = courseSchedule.canFinish(numCourses, prerequisites);
        log.debug("Course schedule: {}", () -> ret);
        log.debug("Course schedule {} OK", () -> "ret");
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        final int ROWS = prerequisites.length;

        if (ROWS == 0 || (ROWS > 0 && prerequisites[0].length != 2)) {
            return true;
        }

        /**
         * Build dependency map
         */
        Map<Integer, Integer> mapPrerequisites = new HashMap<>();

        for (int r = 0; r < ROWS; r++) {
            mapPrerequisites.put(prerequisites[r][0], prerequisites[r][1]);
        }

        /**
         * Remove cyclic depending courses
         */
        for (int r = 0; r < ROWS; r++) {
            int course = prerequisites[r][0];
            Set<Integer> seen = new HashSet<>();

            while (true) {
                Integer prereq = mapPrerequisites.get(course);

                /**
                 * Non-cyclic
                 */
                if (prereq == null) {
                    break;
                }

                /**
                 * Remove cyclic
                 */
                if (seen.contains(prereq)) {
                    mapPrerequisites.remove(prerequisites[r][0]);
                    break;
                } else {
                    seen.add(prereq);
                }

                course = prereq.intValue();
            }
        }

        return mapPrerequisites.size() >= numCourses - 1;
    }
}
