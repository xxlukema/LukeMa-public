package com.learn.graph.dsu;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DisjointSetUnionMap<T> {

    Map<T, Set<T>> parentChildenMap;

    Map<T, T> childParentMap;

    public DisjointSetUnionMap() {
        childParentMap = new HashMap<>();
        parentChildenMap = new HashMap<>();
    }

    public DisjointSetUnionMap(Collection<T> nodes) {
        childParentMap = new HashMap<>();
        parentChildenMap = new HashMap<>();

        if (nodes != null) {

            for (T n : nodes) {
                childParentMap.put(n, n);

                parentChildenMap.putIfAbsent(n, new HashSet<>());
                parentChildenMap.get(n).add(n);
            }
        }
    }

    public int size() {
        return parentChildenMap.size();
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public boolean add(T node) {
        if (childParentMap.containsKey(node)) {
            return false;
        } else {
            childParentMap.put(node, node);
            parentChildenMap.put(node, new HashSet<>());
            parentChildenMap.get(node).add(node);
            return true;
        }
    }

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public T find(T node) {
        return childParentMap.get(node);
    }

    /**
     * Union right param into left param.
     * Union the second param into the first param.
     * Union `src` into `dest`, discard `src`'s previous parent. `src` is smaller, new node. `dest` is the bigger collection.
     *
     * Time: O(1) or O(N) --- O(1) in average. O(N) for the first time run compress
     * Space: O(1)
     */
    public boolean union(T dest, T src) {
        T parentDest = childParentMap.get(dest);
        T parentSrc = childParentMap.get(src);

        if (parentDest == parentSrc) {
            return false;
        } else {
            /**
             * compress
             *
             * change all children2 with parent1,
             * change parent1 to include all children2
             * remove parent2
             */
            Set<T> children2 = parentChildenMap.get(parentSrc);
            for (T node : children2) {
                childParentMap.put(node, parentDest);
            }
            parentChildenMap.get(parentDest).addAll(children2);
            parentChildenMap.remove(parentSrc);
            return true;
        }
    }

}
