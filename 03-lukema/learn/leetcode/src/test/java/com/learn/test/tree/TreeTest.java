package com.learn.test.tree;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


/**
 *
 */
@Log4j2
public class TreeTest {

    /**
     * This is not a valid BST. The inorder traversal result is: [1, 2, 4, 3, 5, 6, 7, 8, 9]
     */
    private Integer[] numsInvalid = { 5, 4, 6, 2, 3, null, 7, 1, null, null, null, null, 8, null, null, null, 9 };

    /**
     * This is a valid BST. The inorder traversal result is: [1, 2, 3, 4, 5, 6, 7, 8, 9]
     */
    private Integer[] numsValid = { 5, 3, 6, 2, 4, null, 7, 1, null, null, null, null, 8, null, null, null, 9 };

    private TreeNode rootInvalid = TreeNode.toTreeBfsWithNullIntegers(numsInvalid);

    private TreeNode rootValid = TreeNode.toTreeBfsWithNullIntegers(numsValid);

    @Test
    public void testBuildBst() {
        log.debug("root invalid: {}", () -> rootInvalid);
        log.debug("root valid: {}", () -> rootValid);
    }

    @Test
    public void testInorderTraversal() {
        List<Integer> listInvalid = TreeNode.inorderTraversalLukeRecursion(rootInvalid);
        log.debug("Inorder traversal invalid: {}", () -> listInvalid);

        List<Integer> listLcInvalid = TreeNode.inorderTraversalLcStack(rootInvalid);
        Assertions.assertEquals(listInvalid, listLcInvalid);

        List<Integer> listValid = TreeNode.inorderTraversalLukeRecursion(rootValid);
        log.debug("Inorder traversal valid: {}", () -> listValid);

        List<Integer> listLcValid = TreeNode.inorderTraversalLcStack(rootValid);
        Assertions.assertEquals(listValid, listLcValid);
    }

    @Test
    public void testPreorderTraversal() {
        List<Integer> listInvalid = TreeNode.preorderTraversalLukeRecursion(rootInvalid);
        log.debug("Preorder traversal invalid: {}", () -> listInvalid);

        List<Integer> listLcInvalid = TreeNode.preorderTraversalLcStack(rootInvalid);
        Assertions.assertEquals(listInvalid, listLcInvalid);

        List<Integer> listValid = TreeNode.preorderTraversalLukeRecursion(rootValid);
        log.debug("Preorder traversal valid: {}", () -> listValid);

        List<Integer> listLcValid = TreeNode.preorderTraversalLcStack(rootValid);
        Assertions.assertEquals(listValid, listLcValid);
    }

    @Test
    public void testValidateBst() {
        boolean retInvalid = TreeNode.validateBst(rootInvalid);
        log.debug("Is Bst valid: {}", () -> retInvalid);

        Assertions.assertFalse(retInvalid);

        boolean retValid = TreeNode.validateBst(rootValid);
        log.debug("Is Bst valid: {}", () -> retValid);

        Assertions.assertTrue(retValid);
    }

}
