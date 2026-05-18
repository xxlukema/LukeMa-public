package com.learn.graph.dsu;


import java.util.Arrays;


/**
 * LC-305 Number of Islands II - Solution 3
 */
public class UnionFindLcArray {
    int count; // # of connected components
    int[] parents;
    int[] rank;

    public UnionFindLcArray(char[][] grid) { // for problem 200
        count = 0;
        final int ROWS = grid.length;
        final int COLS = grid[0].length;
        parents = new int[ROWS * COLS];
        rank = new int[ROWS * COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (grid[row][col] == '1') {
                    parents[row * COLS + col] = row * COLS + col;
                    ++count;
                }
                rank[row * COLS + col] = 0;
            }
        }
    }

    public UnionFindLcArray(int N) { // for problem 305 and others
        count = 0;
        parents = new int[N];
        rank = new int[N];
        Arrays.fill(parents, -1);
        Arrays.fill(rank, 0);
    }

    public boolean isFilled(int i) { // for problem 305
        return parents[i] >= 0;
    }

    public void add(int i) {
        setParent(i);
    }

    void setParent(int i) {
        parents[i] = i;
        ++count;
    }

    public int find(int i) { // path compression
        if (parents[i] != i) {
            parents[i] = find(parents[i]);
        }
        return parents[i];
    }

    /**
     * uion `src` into `dest`, and assume `dest`'s parent'
     * @param dest
     * @param src
     */
    public void union(int dest, int src) { // union with rank
        int rootDest = find(dest);
        int rootSrc = find(src);
        if (rootDest != rootSrc) {
            if (rank[rootDest] > rank[rootSrc]) {
                parents[rootSrc] = rootDest;
            } else if (rank[rootDest] < rank[rootSrc]) {
                parents[rootDest] = rootSrc;
            } else {
                parents[rootSrc] = rootDest;
                rank[rootDest] += 1;
            }
            --count;
        }
    }

    public int getCount() {
        return count;
    }
}
