package com.learn.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 199 - Binary Tree Right Side View
 * 
 * Medium
 * 
 * Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 * 
 * Example 1:
 * Input: root = [1,2,3,null,5,null,4]
 * Output: [1,3,4]
 * 
 * Example 2:
 * Input: root = [1,null,3]
 * Output: [1,3]
 * 
 * Example 3:
 * Input: root = []
 * Output: []
 * 
 * Constraints:
 *     The number of nodes in the tree is in the range [0, 100].
 *     -100 <= Node.val <= 100
 */
@Log4j2
public class BinaryTreeRightSideView {

  public static void main(String[] args) {

    final Integer[] nums = { 1, 2, 3, null, 5, null, 4 };

    final TreeNode root = TreeNode.toTreeBfsWithNullIntegers(nums);

    BinaryTreeRightSideView binaryTreeRightSideView = new BinaryTreeRightSideView();

    var rightSideViewLukeDfsRecursive = binaryTreeRightSideView.rightSideViewLukeDfsRecursive(root);
    log.debug("Binary tree right side view: {}", () -> rightSideViewLukeDfsRecursive);
    log.debug("Binary tree right side view {} OK", () -> "rightSideViewLukeDfsRecursive");

    var rightSideViewLukeBfs = binaryTreeRightSideView.rightSideViewLukeBfs(root);
    Assertions.assertEquals(rightSideViewLukeDfsRecursive.toString(), rightSideViewLukeBfs.toString());
    log.debug("Binary tree right side view {} OK", () -> "rightSideViewLukeBfs");
  }

  /**
   * Luke - DFS - Recursive
   * 
   * Runtime: 1 ms, faster than 94.81% of Java online submissions for Binary Tree Right Side View.
   * Memory Usage: 42.8 MB, less than 38.90% of Java online submissions for Binary Tree Right Side View.
   * 
   * Time: O(N)
   * Space: O(H) Max depth of tree
   */
  public List<Integer> rightSideViewLukeDfsRecursive(final TreeNode root) {

    final List<Integer> list = new ArrayList<>();

    if (root == null) {
      return list;
    }

    return rightSideViewLukeDfsRecursive(root, list, 0);
  }

  int maxDepth = 0;

  private List<Integer> rightSideViewLukeDfsRecursive(final TreeNode root, final List<Integer> list, int depth) {

    if (root == null) {
      return list;
    }

    if (++depth > maxDepth) {
      maxDepth = depth;
      list.add(root.val);
    }

    rightSideViewLukeDfsRecursive(root.right, list, depth);
    rightSideViewLukeDfsRecursive(root.left, list, depth);

    return list;
  }

  /**
   * Luke - BFS with Queue - !!! NOT Stack !!!
   * 
   * Runtime: 8 ms, faster than 12.38% of Java online submissions for Binary Tree Right Side View.
   * Memory Usage: 43.2 MB, less than 14.08% of Java online submissions for Binary Tree Right Side View.
   * 
   * Time: O(N)
   * Space: O(N / 2)
   */
  public List<Integer> rightSideViewLukeBfs(final TreeNode root) {

    final List<Integer> list = new ArrayList<>();

    if (root == null) {
      return list;
    }

    final Queue<TreeNode> queue = new ConcurrentLinkedDeque<>();

    queue.add(root);

    while (!queue.isEmpty()) {

      int size = queue.size();

      list.add(queue.peek().val);

      for (int i = 0; i < size; i++) {
        TreeNode curr = queue.poll();

        if (curr.right != null) {
          queue.offer(curr.right);
        }

        if (curr.left != null) {
          queue.offer(curr.left);
        }
      }
    }

    return list;
  }

  /**
   * Luke - Morris - Can we use Morris for this? Maybe not.
   * 
   * Time: O(N)
   * Space: O(1)
   */
}
