package com.learn.test.amzn2024;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class Solution1 {

    /**
     * LC-2256 Minimum Average Difference
     *
     * Medium
     *
     * Star: 11:15 AM
     * End: 12:19 PM
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n)
     * Space: O(1)
     */
    @Test
    void testMinimumAverageDifference() {
        log.debug(() -> "Start");

        // int[] nums = { 1, 1, 1, 1, 1 };
        // int expected = 0;

        int[] nums = { 2, 5, 3, 9, 5, 3 };
        int expected = 3;

        var ret = minimumAverageDifferenceLc(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-2256 Minimum Average Difference
     *
     * Medium
     *
     * Time: Beats 93% java users.
     * Space: Beats 11.3% java users.
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int minimumAverageDifferenceLc(int[] nums) {
        int len = nums.length;

        long sum = 0;
        long lsum = 0;

        for (int i = 0; i < len; i++) {
            sum += nums[i];
        }

        long min = Long.MAX_VALUE;
        int minIdx = 0;

        for (int i = 0; i < len; i++) {
            lsum += nums[i];
            long rsum = sum - lsum;

            long diff = 0;
            if (len - i - 1 == 0) {
                diff = lsum / (i + 1);
            } else {
                diff = Math.abs(lsum / (i + 1) - rsum / (len - i - 1));
            }
            if (diff < min) {
                min = diff;
                minIdx = i;
            }
        }

        return minIdx;
    }

    /**
     * LC-2256 Minimum Average Difference
     *
     * Medium
     *
     * Time: Beats 20.7% java users.
     * Space: Beats 98.8% java users.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int minimumAverageDifferenceLuke(int[] nums) {
        int len = nums.length;

        long[] lsums = new long[len + 1];
        long[] rsums = new long[len + 1];

        for (int i = 0; i < len; i++) {
            lsums[i + 1] = nums[i] + lsums[i];
            int r = len - i;
            rsums[r - 1] = nums[r - 1] + rsums[r];
        }

        long min = Long.MAX_VALUE;
        int minIdx = 0;

        long[] ave = new long[len];

        for (int i = 0; i < len; i++) {
            // use lsums to store new ave
            if (len - i - 1 == 0) {
                ave[i] = lsums[i + 1] / (i + 1);
            } else {
                ave[i] = Math.abs(lsums[i + 1] / (i + 1) - rsums[i + 1] / (len - i - 1));
            }
            if (ave[i] < min) {
                min = ave[i];
                minIdx = i;
            }
        }

        return minIdx;
    }

    /**
     * LC-347 Top K Frequent Elements
     *
     * Medium
     *
     * Star: 12:23 PM
     * End: 12:57 PM
     *
     * Runtime: Beats 11% of java users
     * Memory: Beats 53% of java users
     *
     * Time: O(n * Log(n))
     * Space: O(n)
     */
    @Test
    void testTopKFrequent() {
        log.debug(() -> "Start");

        int[] nums = { 4, 1, -1, 2, -1, 2, 3 };
        int k = 2;
        // int expected = 2;

        var ret = topKFrequent(nums, k);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-347 Top K Frequent Elements
     *
     * Medium
     *
     * Star: 12:23 PM
     * End: 12:57 PM
     *
     * Runtime: Beats 22% of java users
     * Memory: Beats 36% of java users
     *
     * Time: O(n * Log(n))
     * Space: O(n)
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        record Node(int key, int value) {
        }

        List<Node> list = new ArrayList<>();

        /**
         * `k` is already in use in `topKFrequent(int[] nums, int k)`.
         */
        map.forEach((k1, v) -> list.add(new Node(k1, v)));

        /**
         * `list` already used by lambda. Therefore, must assign result to another variable.
         */
        List<Node> sortedList = list.stream().sorted((a, b) -> b.value - a.value).toList();

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = sortedList.get(i).key;
        }

        return ans;
    }

    /**
    * LC-503 Next Greater Element II
    *
    * Medium
    *
    * Star: 11:00 PM
    * End: 12:35 AM - LC
    *
    * Runtime: Beats 45% of java users
    * Memory: Beats 35% of java users
    *
    * Time: O(n)
    * Space: O(n)
    */
    @Test
    void testNextGreaterElements() {
        log.debug(() -> "Start");

        int[] nums = { 1, 2, 1 };
        int[] expected = { 2, -1, 2 };

        var ret = nextGreaterElementsLc(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertArrayEquals(expected, ret);
        // Assertions.assertEquals(expected, ret);
    }

    public int[] nextGreaterElementsLc(int[] nums) {
        int len = nums.length;

        int[] ans = new int[len];

        /**
         * Stack of idx, not val, because val can be duplicated.
         */
        Stack<Integer> stack = new Stack<>();

        for (int i = len - 1; i >= 0; --i) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? -1 : nums[stack.peek()];
            stack.push(i);
        }

        for (int i = len - 1; i >= 0; --i) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? -1 : nums[stack.peek()];
            stack.push(i);
        }

        return ans;
    }

    /**
     * LC-1727 Largest Submatrx with Rearrangements
     *
     * Medium
     *
     * Star: 12:42 AM
     * End: 12:35 AM - LC
     *
     * Runtime: Beats ??% of java users
     * Memory: Beats ??% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Test
    void testLargestSubmatrix() {
        log.debug(() -> "Start");

        int[][] matrix = { { 0, 0, 1 }, { 1, 1, 1 }, { 1, 0, 1 } };
        int expected = 4;

        var ret = largestSubmatrix(matrix);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertArrayEquals(expected, ret);
        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-1727 Largest Submatrx with Rearrangements
     *
     * Time: O(ROWS * COLS * log(COLS))
     * Space: O(ROWS * COLS)
     *
     * It is generally considered a bad practice to modify the input and when you do,
     * you should count it as part of the space complexity.
     */
    public int largestSubmatrix(int[][] matrix) {
        int ROWS = matrix.length;
        int COLS = matrix[0].length;
        int ans = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (matrix[row][col] != 0 && row > 0) {
                    matrix[row][col] += matrix[row - 1][col];
                }
            }

            int[] currRow = matrix[row].clone();

            /**
             * Trick: int[] can only be sorted ascendingly.
             *
             * Time: O(COLS * log(COLS))
             */
            Arrays.sort(currRow);
            for (int i = 0; i < COLS; i++) {
                /**
                 * Trick: Use `(COLS - i)` because the array is in ascending order.
                 */
                ans = Math.max(ans, currRow[i] * (COLS - i));
            }
        }

        return ans;
    }

    /**
     * LC-1727 Largest Submatrx with Rearrangements
     *
     * Time: O(ROWS * COLS * log(COLS))
     * Space: O(COLS)
     */
    public int largestSubmatrixNotModifyInput(int[][] matrix) {
        int ROWS = matrix.length;
        int COLS = matrix[0].length;
        int[] prevRow = new int[COLS];
        int ans = 0;

        for (int row = 0; row < ROWS; row++) {
            int[] currRow = matrix[row].clone();
            for (int col = 0; col < COLS; col++) {
                if (currRow[col] != 0) {
                    currRow[col] += prevRow[col];
                }
            }

            int[] sortedRow = currRow.clone();

            /**
             * Trick: int[] can only be sorted ascendingly.
             *
             * Time: O(COLS * log(COLS))
             */
            Arrays.sort(sortedRow);
            for (int i = 0; i < COLS; i++) {

                /**
                 * Trick: Use `(COLS - i)` because the array is in ascending order.
                 */
                ans = Math.max(ans, sortedRow[i] * (COLS - i));
            }

            prevRow = currRow;
        }

        return ans;
    }

    /**
     * LC-1727 Largest Submatrx with Rearrangements
     *
     * Time: O(ROWS * COLS)
     * Space: O(COLS)
     */
    public int largestSubmatrixNotSorting(int[][] matrix) {
        int ROWS = matrix.length;
        int COLS = matrix[0].length;

        record Node(int height, int col) {
        }

        List<Node> prevHeights = new ArrayList<>();
        int ans = 0;

        for (int row = 0; row < ROWS; row++) {
            List<Node> heights = new ArrayList<>();
            boolean[] seen = new boolean[COLS];

            for (Node pair : prevHeights) {
                int height = pair.height;
                int col = pair.col;
                if (matrix[row][col] == 1) {
                    heights.add(new Node(height + 1, col));
                    seen[col] = true;
                }
            }

            for (int col = 0; col < COLS; col++) {
                if (seen[col] == false && matrix[row][col] == 1) {
                    heights.add(new Node(1, col));
                }
            }

            for (int i = 0; i < heights.size(); i++) {
                ans = Math.max(ans, heights.get(i).col * (i + 1));
            }

            prevHeights = heights;
        }

        return ans;
    }

    /**
     * LC-582 Kill Process
     *
     * Medium
     *
     * Star: 4:39 PM
     * End: 7:27 PM - LC
     *
     * Runtime: Beats 13.4% of java users
     * Memory: Beats 31.2% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Test
    void testkillProcess() {
        log.debug(() -> "Start");

        Integer[] pid = { 1, 2, 3, 4, 5 };
        Integer[] ppid = { 0, 1, 1, 1, 1 };
        Integer kill = 1;
        Integer[] expected = { 1, 2, 3, 4, 5 };

        var ret = killProcessDFS(List.of(pid), List.of(ppid), kill);

        log.debug("Result: {}", () -> ret);
        log.debug("expected: {}", () -> expected);

        // Assertions.assertArrayEquals(expected, ret);
        // Assertions.assertEquals(expected, ret);
        Assertions.assertArrayEquals(expected, ret.toArray(new Integer[ret.size()]));
    }

    /**
     * LC-582 Kill Process
     *
     * Medium
     *
     * Star: 4:39 PM
     * End: 7:27 PM - LC
     *
     * Runtime: Beats 33.6% of java users
     * Memory: Beats 31.2% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    public List<Integer> killProcessDFS(List<Integer> pid, List<Integer> ppid, int kill) {
        if (kill == 0) {
            return pid;
        }

        // TODO: change list to array to make it faster.
        Map<Integer, Set<Integer>> ppidToChildren = new HashMap<>();

        for (int i = 0; i < pid.size(); i++) {
            ppidToChildren.putIfAbsent(ppid.get(i), new HashSet<>());
            ppidToChildren.get(ppid.get(i)).add(pid.get(i));
        }

        List<Integer> ans = new ArrayList<>();

        dfs(kill, ans, ppidToChildren);

        return ans;
    }

    private void dfs(int kill, List<Integer> ans, Map<Integer, Set<Integer>> ppidToChildren) {
        Set<Integer> children = ppidToChildren.get(kill);
        ans.add(kill);

        if (children == null || children.size() == 0) {
            return;
        }

        for (Integer child : children) {
            dfs(child, ans, ppidToChildren);
        }
    }

    /**
     * LC-582 Kill Process
     *
     * Medium
     *
     * Star: 4:39 PM
     * End: 7:27 PM - LC
     *
     * Runtime: Beats 13.4% of java users
     * Memory: Beats 31.2% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    public List<Integer> killProcessBFS(List<Integer> pid, List<Integer> ppid, int kill) {
        if (kill == 0) {
            return pid;
        }

        // TODO: change list to array to make it faster.
        Map<Integer, Set<Integer>> ppidToChildren = new HashMap<>();

        for (int i = 0; i < pid.size(); i++) {
            ppidToChildren.putIfAbsent(ppid.get(i), new HashSet<>());
            ppidToChildren.get(ppid.get(i)).add(pid.get(i));
        }

        // find child

        List<Integer> ans = new ArrayList<>();

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.add(kill);

        while (!queue.isEmpty()) {
            Integer curr = queue.poll();
            ans.add(curr);

            Set<Integer> children = ppidToChildren.get(curr);
            if (children != null) {
                for (Integer child : children) {
                    queue.add(child);
                }
            }
        }

        return ans;
    }

    /**
     * LC-791 Custom Sort String
     *
     * Medium
     *
     * Star: 7:57 PM
     * End: 2:58 PM - Time Limit Exceeded
     * End: 3:42 PM - Runtime: Beats 16% java users. Memory: Beats 6% java users.
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    @Test
    void testCustomSortString() {
        log.debug(() -> "Start");

        String order = "cba";
        String s = "abcd";

        var ret = customSortStringSorting(order, s);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-791 Custom Sort String
     *
     * Medium
     *
     * Star: 7:57 PM
     * End: 8:36 PM
     *
     * Runtime: Beats 23% of java users
     * Memory: Beats 56% of java users
     *
     * Time: O(s.length() * (log(s.length()) * order.length()))
     * Space: O(s.length())
     */
    public String customSortStringSorting(String order, String s) {
        Character[] chs = new Character[s.length()];

        int idx = 0;
        for (char ch : s.toCharArray()) {
            chs[idx++] = ch;
        }

        /**
         * Time: O(s.length() * (log(s.length()) * order.length()))
         */
        Arrays.sort(chs, (a, b) -> order.indexOf(a) - order.indexOf(b));

        StringBuilder sb = new StringBuilder();
        for (Character ch : chs) {
            sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * LC-791 Custom Sort String
     *
     * Medium
     *
     * Star: 7:57 PM
     * End: 8:36 PM
     *
     * Runtime: Beats 83% of java users
     * Memory: Beats 56% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    public String customSortString(String order, String s) {
        int[] seq = new int[26];
        Arrays.fill(seq, -1);
        int pos = 0;
        for (char ch : order.toCharArray()) {
            seq[(int) (ch - 'a')] = pos++;
        }

        StringBuilder sb = new StringBuilder();

        StringBuilder extras = new StringBuilder();

        @SuppressWarnings("unchecked")
        List<Character>[] todo = new List[26];
        /**
         * Trick: Cannot do this: `Arrays.fill(todo, new ArrayList<>());`. This will create a one List with all instances point to the same reference.
         */
        // Arrays.fill(todo, new ArrayList<>());
        for (int i = 0; i < 26; i++) {
            todo[i] = new ArrayList<>();
        }

        for (char ch : s.toCharArray()) {
            int idx = (int) (ch - 'a');

            if (seq[idx] == -1) {
                extras.append(ch);
            } else {
                todo[seq[idx]].add(ch);
            }
        }

        for (int i = 0; i < 26; i++) {
            List<Character> list = todo[i];
            if (list.size() > 0) {
                for (char ch : list) {
                    sb.append(ch);
                }
            }
        }

        sb.append(extras);

        return sb.toString();
    }

    /**
     * LC-779 K-th Symbol in Grammer
     *
     * Medium
     *
     * Star: 11:31 PM
     * End: 11:44 PM - Time Limit Exceeded
     * End: 3:42 PM - Runtime: Beats 16% java users. Memory: Beats 6% java users.
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(2 ^ n)
     * Space: O(n)
     */
    @Test
    void testKthGrammar() {
        log.debug(() -> "Start");

        // Integer[] root = { 2, 3, 1, 3, 1, null, 1 };
        // int expected = 2;

        // TreeNode rootNode = TreeNode.toTreeBfsWithNullIntegers(root);

        // var ret = pseudoPalindromicPaths(rootNode);

        // log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-779 K-th Symbol in Grammer
     *
     * Medium
     *
     * Star: 11:31 PM
     * End: 11:48 PM - Time Limit Exceeded
     * End: 3:42 PM - Runtime: Beats 16% java users. Memory: Beats 6% java users.
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    public int kthGrammarLTE(int n, int k) {
        StringBuffer sb = new StringBuffer("0");

        for (int i = 0; i < n; i++) {
            StringBuffer sb2 = new StringBuffer();
            for (int m = 0; m < sb.length(); m++) {
                char ch = sb.charAt(m);

                if (ch == '0') {
                    sb2.append("01");
                } else {
                    sb2.append("10");
                }
            }

            sb = sb2;
        }

        return sb.charAt(k - 1) == '0' ? 0 : 1;
    }

    /**
     * LC-779 K-th Symbol in Grammer
     *
     * Medium
     *
     * Time: O(log(n))
     * Space: O(n)
     */
    public int depthFirstSearch(int n, int k, int rootVal) {
        if (n == 1) {
            return rootVal;
        }

        int totalNodes = (int) Math.pow(2, n - 1);

        // Target node will be present in the right half sub-tree of the current root node.
        if (k > (totalNodes / 2)) {
            int nextRootVal = (rootVal == 0) ? 1 : 0;
            return depthFirstSearch(n - 1, k - (totalNodes / 2), nextRootVal);
        }
        // Otherwise, the target node is in the left sub-tree of the current root node.
        else {
            int nextRootVal = (rootVal == 0) ? 0 : 1;
            return depthFirstSearch(n - 1, k, nextRootVal);
        }
    }

    public int kthGrammar(int n, int k) {
        return depthFirstSearch(n, k, 0);
    }

    /**
     * LC-1457 Pseudo-Palindromic Paths in a Binary Tree
     *
     * Medium
     *
     * Star: 1:36 PM
     * End: 2:58 PM - Time Limit Exceeded
     * End: 3:42 PM - Runtime: Beats 16% java users. Memory: Beats 6% java users.
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    @Test
    void testNext() {
        log.debug(() -> "Start");

        // Integer[] root = { 2, 3, 1, 3, 1, null, 1 };
        // int expected = 2;

        // TreeNode rootNode = TreeNode.toTreeBfsWithNullIntegers(root);

        // var ret = pseudoPalindromicPaths(rootNode);

        // log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }
}
