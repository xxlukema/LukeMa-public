package com.learn.other;


import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SimplifyPath {

    public static void main(String[] args) {

        String path = "/a/b///c/./d/../../e/.";

        SimplifyPath simplifyPath = new SimplifyPath();

        var retStack = simplifyPath.simplifyPathStack(path);
        log.debug("SimplifyPath Luke: {}", () -> retStack);

        var retList = simplifyPath.simplifyPathLinkedList(path);
        log.debug("SimplifyPath Luke: {}", () -> retList);

        Assertions.assertEquals(retList, retStack);
    }

    /**
     * Runtime: 14 ms, faster than 23.41% of Java online submissions for Simplify Path.
     * Memory Usage: 43.9 MB, less than 54.20% of Java online submissions for Simplify Path.
     * 
     * Time: O(n)
     * Space: O(n)
     * 
     */
    public String simplifyPathStack(String path) {

        var fields = path.split(("/"));

        // LinkedList<String> list = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String s : fields) {
            if (s.isEmpty() || s.equals(".")) {
                continue;
            } else if (s.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.add(s);
            }
        }

        return "/" + stack.stream().collect(Collectors.joining("/"));
    }

    /**
     * Runtime: 11 ms, faster than 33.52% of Java online submissions for Simplify Path.
     * Memory Usage: 42.6 MB, less than 88.12% of Java online submissions for Simplify Path.
     * 
     * Time: O(n)
     * Space: O(n)
     * 
     */
    public String simplifyPathLinkedList(String path) {

        var fields = path.split(("/"));

        // Stack<String> stack = new Stack<>();
        LinkedList<String> list = new LinkedList<>();

        for (String s : fields) {
            if (s.isEmpty() || s.equals(".")) {
                continue;
            } else if (s.equals("..")) {
                if (!list.isEmpty()) {
                    list.removeLast();
                }
            } else {
                list.add(s);
            }
        }

        return "/" + list.stream().collect(Collectors.joining("/"));
    }
}
