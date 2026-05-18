package com.learn.graph.dsu;


import java.util.Arrays;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class DisjointSetUnionIntArr {

    int[] parents;
    int size;

    /**
     * Uion of [0, n - 1]. All elements must be included in the constructor. Every element has itself as parent.
     */
    public DisjointSetUnionIntArr(int len) {
        parents = new int[len];
        Arrays.fill(parents, -1);
        size = len;
    }

    /**
     * Find parent of i
     *
     * @param i
     * @return parent
     */
    public int find(int i) {
        if (i < 0 || i >= parents.length || parents[i] == -1) {
            throw new ArrayIndexOutOfBoundsException("Uion of [0, n - 1]. All elements must be included in the constructor");
        }

        if (parents[i] != i) {
            // compress
            // parent[i] = find(parent[i]);
            // or
            while (parents[i] != parents[parents[i]]) {
                parents[i] = parents[parents[i]];
            }
        }

        return parents[i];
    }

    /**
     * uion `src` into `dest`, and both use `dest`'s parent.
     *
     * @return false if both are already in the same group, true otherwise.
     */
    public boolean uion(int dest, int src) {
        int parentDest = find(dest);
        int parentSrc = find(src);

        if (parentDest == parentSrc) {
            return false;
        } else {
            /**
             * Trick 5: For Luke's version of `DisjointSetUnionIntArr`, **Compress** inside union makes runtime `20x` faster than **without compress**
             *
             * compress
             *
             * (with union compress)
             * After compress
             * Runtime: 18 ms Beats 55.49%
             * Memory: 63.5 MB Beats 25.55%
             * vs
             * (without uion compress)
             * Runtime: 284 ms Beats 6.43%
             * Memory: 63.5 MB Beats 25.55%
             */
            while (parents[src] == parentSrc) {
                int p = parents[src];
                parents[src] = parentDest;
                src = p;
            }

            size--;

            return true;
        }
    }

    public boolean add(int i) {
        if (parents[i] == -1) {
            parents[i] = i;
            size++;
            return true;
        } else {
            return false;
        }
    }

    public int getSize() {
        return size;
    }

    public void log(String msg) {
        log.debug("{} parent: {}", () -> msg, () -> parents);
    }

}
