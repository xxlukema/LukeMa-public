package com.learn.lc75;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 841. Key and Room
 *
 * Medium
 *
 * There are n rooms labeled from 0 to n - 1 and all the rooms are locked except for room 0. Your goal is to visit all the rooms. However, you cannot enter a locked room without having its key.

When you visit a room, you may find a set of distinct keys in it. Each key has a number on it, denoting which room it unlocks, and you can take all of them with you to unlock the other rooms.

Given an array rooms where rooms[i] is the set of keys that you can obtain if you visited room i, return true if you can visit all the rooms, or false otherwise.

Example 1:

Input: rooms = [[1],[2],[3],[]]
Output: true
Explanation:
We visit room 0 and pick up key 1.
We then visit room 1 and pick up key 2.
We then visit room 2 and pick up key 3.
We then visit room 3.
Since we were able to visit every room, we return true.

Example 2:

Input: rooms = [[1,3],[3,0,1],[2],[0]]
Output: false
Explanation: We can not enter room number 2 since the only key that unlocks it is in that room.

Constraints:

    n == rooms.length
    2 <= n <= 1000
    0 <= rooms[i].length <= 1000
    1 <= sum(rooms[i].length) <= 3000
    0 <= rooms[i][j] < n
    All the values of rooms[i] are unique.
 */

@Log4j2
public class KeyAndRoom {

    public static void main(String[] args) {

        KeyAndRoom keyAndRoom = new KeyAndRoom();

        Integer[][] rooms = { { 1, 3 }, { 3, 0, 1 }, { 2 }, { 0 } };
        boolean excepted = false;

        // Integer[][] rooms = { { 1 }, { 2 }, { 3 }, {} };
        // boolean excepted = true;

        // Integer[][] rooms = { { 1 }, { 0 } };
        // boolean excepted = true;

        // Integer[][] rooms = { { 1 }, {}, { 0, 3 }, { 1 } };
        // boolean excepted = false;

        List<List<Integer>> list = new ArrayList<>();

        for (Integer[] sub : rooms) {
            list.add(List.of(sub));
        }

        var ret = keyAndRoom.canVisitAllRooms(list);
        log.debug("Key and Room: {}", () -> ret);
        Assertions.assertEquals(excepted, ret);
        log.debug("Key and Room: {}", () -> "canVisitAllRooms");

    }

    /**
     * BFS
     *
     * Time: O("number of rooms" + "number of keys")
     * Space: O(n)
     *
     * Runtime: 3ms Beats 26.52
     * Memory: 43.33mb Beats 55.81
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> todoSet = new HashSet<>();

        for (int i = 0, size = rooms.size(); i < size; i++) {
            todoSet.add(i);
        }

        Queue<Integer> queue = new LinkedList<>();

        List<Integer> firstRoomKeys = rooms.get(0);
        queue.addAll(firstRoomKeys);

        todoSet.remove(Integer.valueOf(0));

        while (!queue.isEmpty() && !todoSet.isEmpty()) {
            for (int i = 0, len = queue.size(); i < len; i++) {
                Integer key = queue.poll();

                List<Integer> keys = rooms.get(key);

                for (Integer room : keys) {
                    if (todoSet.contains(room)) {
                        queue.add(room);
                        todoSet.remove(room);
                    }
                }

                todoSet.remove(Integer.valueOf(key));
            }
        }

        return todoSet.isEmpty();
    }

    public boolean canVisitAllRoomsNoWorking(List<List<Integer>> rooms) {
        UnionSet us = new UnionSet(rooms.size());

        log.debug("us.parent: {}", us.parent);

        for (int i = 0, size = rooms.size(); i < size; i++) {
            for (Integer key : rooms.get(i)) {
                us.uion(i, key);
            }

            log.debug("--- us.parent: {}", us.parent);
        }

        log.debug("us.parent: {}", us.parent);

        return us.count == 1;
    }
}


class UnionSet {
    int[] parent;
    int count = 0;

    public UnionSet(int size) {
        parent = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }

        count = size;
    }

    public int find(int id) {

        int parentId = parent[id];

        if (parentId == id) {
            return id;
        }

        while (parentId != parent[parentId]) {
            parentId = parent[parentId];
        }

        return parentId;
    }

    public boolean uion(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);

        if (parentA == parentB) {
            return false;
        } else {
            while (parentB == parent[b]) {
                int p = parent[b];
                parent[b] = parentA;
                b = p;
            }

            count--;

            return true;
        }
    }

    public int count() {
        return count;
    }

}
