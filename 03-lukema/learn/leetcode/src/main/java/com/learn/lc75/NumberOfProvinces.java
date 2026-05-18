package com.learn.lc75;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 547. Number of Provinces
 *
 * Medium
 *
 * There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c,
 * then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.

Example 1:

Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2

Example 2:

Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3

Constraints:

    1 <= n <= 200
    n == isConnected.length
    n == isConnected[i].length
    isConnected[i][j] is 1 or 0.
    isConnected[i][i] == 1
    isConnected[i][j] == isConnected[j][i]
 */

@Log4j2
public class NumberOfProvinces {

    public static void main(String[] args) {

        NumberOfProvinces numberOfProvinces = new NumberOfProvinces();

        /*
        int[][] isConnected = {
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 0, 0, 1 } };

        int expected = 2;
        */

        int[][] isConnected = {
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 0, 0, 1 } };

        int expected = 3;

        var ret = numberOfProvinces.findCircleNum(isConnected);
        log.debug("Number of Provinces: {}", () -> ret);
        Assertions.assertEquals(expected, ret);
        log.debug("Number of Provinces: {} OK", () -> "findCircleNum");

    }

    /**
     * Time: O(n ^ 2)
     * Space: O(n)
     *
     * Runtime: 1ms Beats 99.51%
     * Memory: 46.65mb Beats 6.18%
     */
    public int findCircleNum(int[][] isConnected) {
        UnionFind uf = new UnionFind(isConnected.length);

        for (int r = 0, rows = isConnected.length; r < rows; r++) {
            for (int c = r, cols = isConnected[0].length; c < cols; c++) {
                if (isConnected[r][c] == 1) {
                    uf.uion(r, c);
                }
            }
        }

        return uf.size;
    }
}


class UnionFind {

    int[] parent;
    int size;

    public UnionFind(int size) {
        parent = new int[size];
        this.size = size;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int id) {
        int pid = parent[id];

        if (pid == id) {
            return pid;
        }

        while (pid != id) {
            id = pid;
            pid = parent[pid];
        }

        return pid;
    }

    public boolean uion(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);

        if (parentA == parentB) {
            return false;
        }

        while (parent[b] != parentA) {
            int p = parent[b];
            parent[b] = parentA;
            b = p;
        }

        size--;

        return true;
    }

    public int size() {
        return size;
    }

}
