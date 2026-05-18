package com.learn.amzn25;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * 2534. Time Taken to Cross the Door
 *
 * Hard
 *
 * There are n persons numbered from 0 to n - 1 and a door. Each person can enter or exit through the door once, taking one second.
 *
 * You are given a non-decreasing integer array arrival of size n, where arrival[i] is the arrival time of the ith person at the door. You are also given an array
 * state of size n, where state[i] is 0 if person i wants to enter through the door or 1 if they want to exit through the door.
 *
 * If two or more persons want to use the door at the same time, they follow the following rules:
 *
 *     If the door was not used in the previous second, then the person who wants to exit goes first.
 *     If the door was used in the previous second for entering, the person who wants to enter goes first.
 *     If the door was used in the previous second for exiting, the person who wants to exit goes first.
 *     If multiple persons want to go in the same direction, the person with the smallest index goes first.
 *
 * Return an array answer of size n where answer[i] is the second at which the ith person crosses the door.
 *
 * Note that:
 *
 *     Only one person can cross the door at each second.
 *     A person may arrive at the door and wait without entering or exiting to follow the mentioned rules.
 *
 * Example 1:
 *
 * Input: arrival = [0,1,1,2,4], state = [0,1,0,0,1]
 * Output: [0,3,1,2,4]
 * Explanation: At each second we have the following:
 * - At t = 0: Person 0 is the only one who wants to enter, so they just enter through the door.
 * - At t = 1: Person 1 wants to exit, and person 2 wants to enter. Since the door was used the previous second for entering, person 2 enters.
 * - At t = 2: Person 1 still wants to exit, and person 3 wants to enter. Since the door was used the previous second for entering, person 3 enters.
 * - At t = 3: Person 1 is the only one who wants to exit, so they just exit through the door.
 * - At t = 4: Person 4 is the only one who wants to exit, so they just exit through the door.
 *
 * Example 2:
 *
 * Input: arrival = [0,0,0], state = [1,0,1]
 * Output: [0,2,1]
 * Explanation: At each second we have the following:
 * - At t = 0: Person 1 wants to enter while persons 0 and 2 want to exit. Since the door was not used in the previous second, the persons who want to exit get to go first. Since person 0 has a smaller index, they exit first.
 * - At t = 1: Person 1 wants to enter, and person 2 wants to exit. Since the door was used in the previous second for exiting, person 2 exits.
 * - At t = 2: Person 1 is the only one who wants to enter, so they just enter through the door.
 *
 * Constraints:
 *
 *     n == arrival.length == state.length
 *     1 <= n <= 10 ^ 5
 *     0 <= arrival[i] <= n
 *     arrival is sorted in non-decreasing order.
 *     state[i] is either 0 or 1.
 */
@Log4j2
public class TimeTokenToCrossTheDoor {

    public static void main(String[] args) {

        TimeTokenToCrossTheDoor timeTokenToCrossTheDoor = new TimeTokenToCrossTheDoor();

        /*
        int[] arrival = { 0, 1, 1, 2, 4 };
        int[] state = { 0, 1, 0, 0, 1 };
        int[] expected = { 0, 3, 1, 2, 4 };
        */

        /*
        int[] arrival = { 0, 0, 0 };
        int[] state = { 1, 0, 1 };
        int[] expected = { 0, 2, 1 };
        */

        int[] arrival = { 0, 0, 1, 2, 8, 10, 10, 10, 10, 10 };
        int[] state = { 0, 1, 0, 1, 1, 0, 0, 0, 1, 1 };
        int[] expected = { 1, 0, 2, 3, 8, 12, 13, 14, 10, 11 };

        var ret = timeTokenToCrossTheDoor.timeTakenLuke(arrival, state);
        log.info("Minimum Time Visiting All Points: {}", () -> ret);
        Assertions.assertArrayEquals(expected, ret);
        log.debug("Minimum Time Visiting All Points {} OK", () -> "timeTakenLuke");

        var retLc = timeTokenToCrossTheDoor.timeTakenLc(arrival, state);
        log.info("Minimum Time Visiting All Points: {}", () -> retLc);
        Assertions.assertArrayEquals(expected, retLc);
        log.debug("Minimum Time Visiting All Points {} OK", () -> "timeTakenLc");

    }

    /**
     * LC - Two Queue
     */
    public int[] timeTakenLc(int[] arrival, int[] state) {
        int len = arrival.length;

        int[] answer = new int[len];

        record Person(int idx, int arrivalTime) {
        }

        List<Person> enter = new ArrayList<Person>();
        List<Person> exit = new ArrayList<Person>();

        for (int i = 0; i < len; i++) {
            Person person = new Person(i, arrival[i]);
            if (state[i] == 0) {
                enter.add(person);
            } else {
                exit.add(person);
            }
        }

        int time = 0;
        boolean isExiting = true;

        while (!enter.isEmpty() && !exit.isEmpty()) {
            if (exit.get(0).arrivalTime == enter.get(0).arrivalTime) {
                if (time < exit.get(0).arrivalTime) {
                    time = exit.get(0).arrivalTime;
                    isExiting = true;
                }

                if (isExiting) {
                    Person p = exit.remove(0);
                    answer[p.idx] = time++;
                } else {
                    Person p = enter.remove(0);
                    answer[p.idx] = time++;
                }
            } else {
                if (exit.get(0).arrivalTime < enter.get(0).arrivalTime) {
                    if (time < exit.get(0).arrivalTime) {
                        time = exit.get(0).arrivalTime;
                        isExiting = true;
                    }

                    if (isExiting) {
                        Person p = exit.remove(0);
                        answer[p.idx] = time++;
                    } else {
                        Person p = enter.remove(0);
                        answer[p.idx] = time++;
                    }

                } else {
                    if (time < enter.get(0).arrivalTime) {
                        time = enter.get(0).arrivalTime;
                        isExiting = true;
                    }

                    if (isExiting) {
                        Person p = exit.remove(0);
                        answer[p.idx] = time++;
                    } else {
                        Person p = enter.remove(0);
                        answer[p.idx] = time++;
                    }
                }
            }
        }

        while (!enter.isEmpty()) {
            if (time < enter.get(0).arrivalTime) {
                time = enter.get(0).arrivalTime;
            }
            Person p = enter.remove(0);
            answer[p.idx] = time++;
        }

        while (!exit.isEmpty()) {
            if (time < exit.get(0).arrivalTime) {
                time = exit.get(0).arrivalTime;
            }
            Person p = exit.remove(0);
            answer[p.idx] = time++;
        }

        return answer;
    }

    /**
    * Luke - Time Limit Exceeded
    */
    public int[] timeTakenLuke(int[] arrival, int[] state) {
        int len = arrival.length;

        int[] answer = new int[len];

        record Person(int idx, int arrivalTime, boolean isExiting) {
        }

        Queue<Person> queueTime = new PriorityQueue<>((a, b) -> a.arrivalTime - b.arrivalTime);

        for (int i = 0; i < len; i++) {
            Person person = new Person(i, arrival[i], state[i] == 1);
            queueTime.add(person);
        }

        int time = 0;
        boolean isExiting = true;

        Queue<Person> queueIdx = new PriorityQueue<>((a, b) -> a.idx - b.idx);

        while (!queueTime.isEmpty() || !queueIdx.isEmpty()) {
            if (!queueTime.isEmpty() && queueIdx.isEmpty()) {
                int minArrivalTime = queueTime.peek().arrivalTime;
                if (time < minArrivalTime) {
                    time = minArrivalTime;
                    isExiting = true;
                }
            }

            while (!queueTime.isEmpty() && queueTime.peek().arrivalTime <= time) {
                queueIdx.add(queueTime.poll());
            }

            if (!queueIdx.isEmpty()) {
                boolean found = false;
                List<Person> notList = new ArrayList<>();
                List<Person> yesList = new ArrayList<>();

                while (!queueIdx.isEmpty()) {
                    Person p = queueIdx.poll();
                    if (p.isExiting == isExiting) {
                        found = true;
                        yesList.add(p);
                    } else {
                        notList.add(p);
                    }
                }

                if (found) {
                    Queue<Person> queueYes = new PriorityQueue<>((a, b) -> a.idx - b.idx);
                    queueYes.addAll(yesList);

                    Person p = queueYes.poll();
                    isExiting = p.isExiting;
                    answer[p.idx] = time++;

                    queueIdx.addAll(notList);
                    queueIdx.addAll(queueYes);
                } else {
                    queueIdx.addAll(notList);
                    Person p = queueIdx.poll();
                    isExiting = p.isExiting;
                    answer[p.idx] = time++;
                }
            }
        }

        return answer;
    }

}
