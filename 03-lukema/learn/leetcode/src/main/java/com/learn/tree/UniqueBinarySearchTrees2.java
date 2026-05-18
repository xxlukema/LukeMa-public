package com.learn.tree;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


/**
 * LC 95
 */
@Log4j2
public class UniqueBinarySearchTrees2 {

    public static void main(String[] args) {

        int n = 3;

        UniqueBinarySearchTrees2 uniqueBinarySearchTrees2 = new UniqueBinarySearchTrees2();

        List<TreeNode> ret = uniqueBinarySearchTrees2.generateTreesLcBreakConquer(n);
        log.info("Unique binary search trees II: {}", () -> ret.size());
    }

    /**
     * LC Break and Conquer
     * 
     * Runtime: 3 ms, faster than 47.83% of Java online submissions for Unique Binary Search Trees II.
     * Memory Usage: 47.3 MB, less than 6.84% of Java online submissions for Unique Binary Search Trees II.
     * 
     * Time: nGn, or O(4 ^ N / N ^ (1/2))
     * 
     * The main computations are to construct all possible trees with a given root, that is actually Catalan 
     * number Gn​ as was discussed above. This is done n times, that results in time complexity nGn​. Catalan 
     * numbers grow as 4^n / n ^ (3/2)​ that gives the final complexity O(4 ^ N / N ^ (1/2)).
     * 
     * Space: nGn, or O(4 ^ N / N ^ (1/2))
     */
    public List<TreeNode> generateTreesLcBreakConquer(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        return breakAndConquer(1, n);
    }

    private List<TreeNode> breakAndConquer(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        if (start > end) {
            result.add(null);
            return result;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTrees = breakAndConquer(start, i - 1);
            List<TreeNode> rightTrees = breakAndConquer(i + 1, end);

            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }

        return result;
    }
}
