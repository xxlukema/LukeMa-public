package com.learn.other;


/**
 * LC-251 Flattern 2D Vector
 *
 * Medium
 *
 * Design an iterator to flatten a 2D vector. It should support the next and hasNext operations.
 *
 * Implement the Vector2D class:
 *
 *     Vector2D(int[][] vec) initializes the object with the 2D vector vec.
 *     next() returns the next element from the 2D vector and moves the pointer one step forward. You may assume that all the calls to next are valid.
 *     hasNext() returns true if there are still some elements in the vector, and false otherwise.
 *
 * Example 1:
 * Input
 * ["Vector2D", "next", "next", "next", "hasNext", "hasNext", "next", "hasNext"]
 * [[[[1, 2], [3], [4]]], [], [], [], [], [], [], []]
 * Output
 * [null, 1, 2, 3, true, true, 4, false]
 * Explanation
 * Vector2D vector2D = new Vector2D([[1, 2], [3], [4]]);
 * vector2D.next();    // return 1
 * vector2D.next();    // return 2
 * vector2D.next();    // return 3
 * vector2D.hasNext(); // return True
 * vector2D.hasNext(); // return True
 * vector2D.next();    // return 4
 * vector2D.hasNext(); // return False
 *
 * Constraints:
 *     0 <= vec.length <= 200
 *     0 <= vec[i].length <= 500
 *     -500 <= vec[i][j] <= 500
 *     At most 105 calls will be made to next and hasNext.
 *
 * Follow up: As an added challenge, try to code it using only iterators in C++ or iterators in Java.
 */
public class Flattern2DVector {

    public static void main(String[] args) {

    }
}


/**
 * Luke - Two pointers - Same approach used by LC
 *
 * One of the main purposes of an Iterator is to minimize the use of auxiliary space. We should try to utilize the existing data
 * structure as much as possible, only adding as much extra space as needed to keep track of the next value. In some situations,
 * the data structure we want to iterate over is too large to even fit in memory anyway (think of file systems).
 *
 * Runtime: 26 ms Beats 26.85%
 * Memory: 51.4 MB Beats 20.70%
 *
 * Time and Space complxities see each method.
 */
class Vector2D {

    private int[][] vec;
    private int idxArray;
    private int idxElement;

    /**
     * Time: O(1)
     * Space: O(1)
     */
    public Vector2D(int[][] vec) {
        this.vec = vec;
    }

    /**
     * Time: from O(1) to O(vec.length)
     * Space: O(1)
     */
    public int next() {
        if (hasNext()) {
            return vec[idxArray][idxElement++];
        } else {
            throw new java.util.NoSuchElementException();
        }
    }

    /**
     * Time: from O(1) to O(vec.length)
     *  Space: O(1)
     */
    public boolean hasNext() {
        if (this.vec == null) {
            return false;
        }

        while (idxArray < this.vec.length && (vec[idxArray] == null || (vec[idxArray] != null && idxElement >= vec[idxArray].length))) {
            idxArray++;
            idxElement = 0;
        }

        return idxArray < vec.length && vec[idxArray] != null && idxElement < vec[idxArray].length;
    }
}
