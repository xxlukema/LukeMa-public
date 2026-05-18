package com.learn.test.amzn2024;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.learn.tree.TreeNode;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SolutionMarch24 {

    /**
     * LC-2781
     *
     * Hard
     *
     * Start: 3:42 PM
     * End: 4:09 PM TLE
     */
    @Test
    @Disabled
    public void testMinimumKeypresses() {
        log.debug(() -> "Start");

        // String word = "cbaaaabc";
        // String[] forbidden = { "aaa", "cb" };
        // int expected = 4;

        String word = "leetcode";
        String[] forbidden = { "de", "le", "e" };
        int expected = 4;

        // String word = "a";
        // String[] forbidden = { "n" };
        // int expected = 1;

        // String word = "bcac";
        // String[] forbidden = { "bcac", "caca", "bcac", "bca" };
        // int expected = 3;

        var ret = longestValidSubstring(word, List.of(forbidden));

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-2781
     *
     * Hard
     *
     * Time: O(N * lg(forbidden.size()))
     * Space: O(forbidden.size())
     *
     * Runtime: 279 ms. Beats 76% of java users.
     * Memory: 61 mb. Beats 77% of java users.
     */
    public int longestValidSubstring(String word, List<String> forbidden) {
        // leeeeetcode
        // 1. build set for forbidden words
        Set<String> set = new HashSet<>();
        int lenMax = 0;
        for (String f : forbidden) {
            set.add(f);
            lenMax = Math.max(lenMax, f.length());
        }

        // 2. two pointer/sliding window

        int max = 0;
        int left = 0;

        for (int right = left, len = word.length(); right < len; right++) {
            // leetcode
            // cbaaaabc
            // 0123456789---
            for (int i = 0; i < lenMax; i++) {
                int start = right - i;
                if (start >= left) {
                    String sub = word.substring(start, right + 1);
                    // log.debug("sub: {}", sub);
                    if (set.contains(sub)) {
                        // log.debug("==== bad: {}", sub);
                        left = start + 1;
                        break;
                    }
                }
            }

            max = Math.max(max, right - left + 1);
            // log.debug("max: {}", max);
        }

        return max;
    }

    /**
     * LC-2357
     *
     * Easy
     *
     * Start: 10:39 PM
     * End: 11:04 PM TLE
     */
    @Test
    @Disabled
    public void testMinimumOperations() {
        log.debug(() -> "Start");

        int[] nums = { 1, 5, 0, 3, 5 };
        int expected = 3;

        var ret = minimumOperations(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-2357
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     *
     * Brutal force
     *
     * Runtime: Beats 17% of java users.
     * Memory: Beats 97% of java users.
     */
    public int minimumOperations(int[] nums) {
        int count = 0;
        int min = 0;
        int max = 0;

        do {

            log.debug("nums: {}", nums);

            min = Integer.MAX_VALUE;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > 0) {
                    min = Math.min(min, nums[i]);
                }
            }

            if (min == Integer.MAX_VALUE) {
                break;
            }

            max = 0;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > 0) {
                    nums[i] -= min;
                }
                max = Math.max(max, nums[i]);
            }

            count++;
        } while (max > 0);

        return count;
    }

    /**
     * LC-2357
     *
     * Time: O(n * log(n))
     * Space: O(1)
     *
     * count number of unique integers.
     *
     * Runtime: Beats 67% of java users.
     * Memory: Beats 80% of java users.
     */
    public int minimumOperationsLc(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            set.add(n);
        }

        return set.size();
    }

    /**
     * Time: O(n)
     * Space: O(1)
     *
     * count number of unique integers.
     *
     * Runtime: Beats 100% of java users.
     * Memory: Beats 80% of java users.
     */
    public int minimumOperationsFastest(int[] nums) {
        int[] tracking = new int[101];

        int count = 0;

        for (int n : nums) {
            if (n != 0) {
                if (tracking[n] == 0) {
                    count++;
                }
            }

            tracking[n]++;
        }

        return count;
    }

    /**
     * LC-472
     *
     * Hard
     *
     * Start: 12:27 AM
     * End: 2:16 AM
     */
    @Test
    @Disabled
    public void testFindAllConcatenatedWordsInADict() {
        log.debug(() -> "Start");

        String[] words = { "cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat" };
        // String[] words = { "cat", "cats", "dog", "dogcatsdog", };

        var ret = findAllConcatenatedWordsInADictNC(words);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-472
     *
     * Hard
     *
     * Time: O(words.length() * log(words.length()) * words[i].length() ^ 3)
     * Space: O(words.length() * word[i].length())
     *
     * Runtime: Beats 15% of java users.
     * Memory: Beats 39% of java users.
     */
    public List<String> findAllConcatenatedWordsInADictNC(String[] words) {
        List<String> result = new ArrayList<>();
        Queue<String> queue = new PriorityQueue<>((a, b) -> b.length() - a.length());
        Set<String> set = new HashSet<>();
        for (String w : words) {
            queue.add(w);
            set.add(w);
        }

        Map<String, Boolean> memo = new HashMap<>();

        while (queue.size() > 1) {
            String str = queue.poll();
            set.remove(str);

            // backtrack
            if (isConcatenatedWords(str, set, result, memo)) {
                result.add(str);
            }
        }

        return result;
    }

    private boolean isConcatenatedWords(String str, Set<String> set, List<String> result, Map<String, Boolean> memo) {
        if (memo.containsKey(str)) {
            return memo.get(str);
        }

        for (int i = 0, len = str.length(); i < len - 1; i++) {
            String prefix = str.substring(0, i + 1);
            String suffix = str.substring(i + 1, len);

            if (set.contains(prefix) && (set.contains(suffix) || isConcatenatedWords(suffix, set, result, memo))) {

                memo.put(str, true);
                return true;
            }
        }

        memo.put(str, false);

        return false;
    }

    /**
     * Time: O(N ^ 2)
     *
     * Time: O(words.length() * log(words.length()) * words[i].length() ^ 3)
     * Space: O(words.length() * word[i].length())
     *
     * Runtime: Beats 15% of java users.
     * Memory: Beats 39% of java users.
     */
    public List<String> findAllConcatenatedWordsInADictNC2(String[] words) {
        List<String> result = new ArrayList<>();
        Queue<String> queue = new PriorityQueue<>((a, b) -> b.length() - a.length());
        Set<String> set = new HashSet<>();
        for (String w : words) {
            queue.add(w);
            set.add(w);
        }

        Map<String, Boolean> memo = new HashMap<>();

        while (queue.size() > 1) {
            String str = queue.poll();
            set.remove(str);

            // backtrack
            if (isConcatenatedWords(str, set, result, memo)) {
                result.add(str);
            }
        }

        return result;
    }

    /**
     * LC-2355
     *
     * Hard
     *
     * Star: 11:21 PM
     * End: 11:59 PM TLE
     *
     * Start: 12:27 AM
     * End: 2:16 AM
     */
    @Test
    @Disabled
    public void testMaximumBookst() {
        log.debug(() -> "Start");

        // int[] books = { 8, 5, 2, 7, 9 };
        // int expected = 19;

        // int[] books = { 7, 0, 3, 4, 5 };
        // int expected = 12;

        int[] books = { 8, 2, 3, 7, 3, 4, 0, 1, 4, 3 };
        int expected = 13;

        // int[] books = { 1, 2, 3, 0, 4 };
        // int expected = 6;

        var ret = maximumBooks(books);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     *
     *
     * Time: O(N)
     * Space: O(N)
     */
    public long maximumBooks(int[] books) {
        int len = books.length;

        int[] curr = new int[len];
        int[] dp = new int[len];
        dp[len - 1] = books[len - 1];

        curr[len - 1] = books[len - 1];

        int sum = curr[len - 1];
        int last = curr[len - 1];

        for (int i = len - 2; i >= 0; i--) {

            //  8  2  3  7  3  4

            //     0  1  2  3  4
            //       10  9  7  4

            //  1  2  3  7  3  4
            // 13 12 10  7  7  4

            if (last > 0) {
                curr[i] = Math.min(books[i], last - 1);
                sum += curr[i];
            } else {
                curr[i] = books[i];
                sum = books[i];
            }

            dp[i] = Math.max(sum, dp[i + 1]);

            if (sum <= books[i]) {
                curr[i] = books[i];
                sum = books[i];
            }

            if (last == 0) {
                curr[i] = books[i];
                sum = books[i];
            }

            last = curr[i];
        }

        log.debug("books: {}", books);
        log.debug(" curr: {}", curr);
        log.debug("   dp: {}", dp);

        return dp[0];
    }

    /**
     * TLE
     *
     * Time: O(N ^ 2)
     * Space: O(1)
     */
    public long maximumBooksTLE(int[] books) {
        int len = books.length;
        int max = 0;

        for (int r = len - 1; r >= 0; r--) {
            int count = 0;
            int l = r - 1;
            int last = books[r];
            count += last;
            while (l >= 0) {
                int curr = books[l] >= last ? last - 1 : books[l];
                if (curr == 0) {
                    break;
                }
                count += curr;
                last = curr;
                l--;
            }
            max = Math.max(max, count);
            r = l;
        }

        return max;
    }

    /**
     * LC-1531
     *
     * Hard
     *
     * Star: 12:07 PM
     * End: 03:03 PM
     */
    @Test
    @Disabled
    public void testGetLengthOfOptimalCompression() {
        log.debug(() -> "Start");

        // String s = "aaabcccd";
        // int k = 2;
        // int expected = 4;

        // String s = "abc";
        // int k = 2;
        // int expected = 1;

        //          012345678901234
        String s = "aabaabbcbbbaccc";
        int k = 6;
        int expected = 4;

        log.debug("s: {}", () -> s);

        var ret = getLengthOfOptimalCompression(s, k);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    public int getLengthOfOptimalCompression(String s, int k) {
        // 1. same chars idx, length, effect
        char[] chs = s.toCharArray();

        record Effect(int idx, char ch, int length, int effect) {
        }

        List<Effect> list = new ArrayList<>();

        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];
            int length = 1;
            int next = i + 1;
            while (next < chs.length && chs[next++] == ch) {
                length++;
            }
            int left = i - 1;
            int right = i + length;
            int effect = 0;
            while (left >= 0 && right < chs.length) {
                if (chs[left] == chs[i - 1] && chs[left] == chs[right]) {
                    effect++;
                } else {
                    break;
                }

                left--;
                right++;
            }

            Effect e = new Effect(i, ch, length, effect);
            list.add(e);

            if (length > 1) {
                i += length - 1;
            }
        }

        // 2. build a list of substrings to delete
        Queue<Effect> queue = new PriorityQueue<>((a, b) -> (b.effect - b.length) - (a.effect - a.length));
        list.forEach(e -> queue.add(e));

        List<Effect> deleteList = new ArrayList<>();

        int rem = k;
        while (!queue.isEmpty() && rem > 0) {
            var e = queue.poll();
            if (e.length > rem) {
                continue;
            }
            deleteList.add(e);
            rem -= e.length;
        }

        // 3. delete
        deleteList = deleteList.stream().sorted((a, b) -> b.idx - a.idx).toList();

        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < deleteList.size(); i++) {
            var e = deleteList.get(i);
            sb.delete(e.idx, e.idx + e.length);
        }

        // 4. count
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            int count = 1;
            char ch = sb.charAt(i);
            int pos = i + 1;
            while (pos < sb.length() && sb.charAt(pos) == ch) {
                count++;
                pos++;
            }

            i += count - 1;

            res.append(ch);

            if (count > 1) {
                res.append(count);
            }
        }

        // log.debug("res: {}", res);

        // 5. return
        return res.length();
    }

    /**
     * LC-1420
     *
     * Hard
     *
     * Star: 09:32 AM
     * End: 10:32 AM
     */
    @Disabled
    @Test
    public void testNumOfArrays() {
        log.debug(() -> "Start");

        int n = 2, m = 3, k = 1;
        int expected = 6;

        var ret = numOfArrays(n, m, k);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-1420
     *
     * Hard
     *
     */
    public int numOfArrays(int n, int m, int k) {
        if (m == k) {
            return 1;
        }

        if (m < k) {
            return 0;
        }

        int count = dfs(0, n, m, k);

        return count;
    }

    int dfs(int idx, int n, int m, int k) {

        if (m == k) {
            return 1;
        }

        if (m < k) {
            return 0;
        }

        if (m < 1) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n < 1) {
            return 0;
        }

        if (k == 0) {
            return 1;
        }

        if (k < 0) {
            return 0;
        }

        if (idx >= n) {
            return 0;
        }

        // 1. increment this by 1
        int count1 = dfs(idx, n, m - 1, k - 1) + 1;

        // 2. not increment this by 1
        int count2 = dfs(idx + 1, n, m, k);

        return count1 + count2;
    }

    /**
     * LC-1727 Largest Submatrix With Rearrangements
     *
     * Medium
     *
     * Star: 12:02 PM
     * End:
     */
    @Disabled
    @Test
    public void test1727() {
    }

    /**
     * LC-1567
     *
     * Medium
     *
     * Star: 11:00 PM
     * End: 12:02 AM
     */
    @Disabled
    @Test
    public void testGetMaxLen() {
        log.debug(() -> "Start");

        // dp        0 1  2  3  4   0  1  0  1
        // int[] nums = { 1, 2, 3, 5, -6, 4, 0, 10 };
        // int expected = 4;

        // dp         0  0  0   0  1  2    4   5  6
        int[] nums = { -16, 0, -5, 2, 2, -13, 11, 8 };
        int expected = 6;

        var ret = getMaxLen(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    public int getMaxLen(int[] nums) {
        int max = 0;

        int positives = 0;
        int negatives = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 0) {
                positives = 0;
                negatives = 0;
            } else if (nums[i] > 0) {
                positives++;
                negatives = (negatives == 0) ? 0 : negatives + 1;
            } else {
                int pos = positives;
                positives = (negatives == 0) ? 0 : negatives + 1;
                negatives = (pos == 0) ? 1 : pos + 1;
            }

            max = Math.max(max, positives);
        }

        return max;
    }

    /**
    * LC-2288 Apply discount to price
    *
    * Medium
    *
    * Star: 11:35 PM
    * End: 12:09 AM
    */
    @Test
    @Disabled
    public void test2288() {
        log.debug(() -> "Start");
    }

    /**
     * LC-2288 Apply discount to price
     *
     * Medium
     *
     * Runtime: Beats 61% of java users.
     * Memory: Beats 48% of java users.
     *
     * Time: O(N)
     * Memory: O(N)
     */
    public String discountPrices(String sentence, int discount) {
        String[] words = sentence.split(" ");
        StringBuilder sb = new StringBuilder();

        double factor = 1.0 - discount / 100.0;

        for (String w : words) {
            if (sb.length() > 0) {
                sb.append(' ');
            }

            if (w.charAt(0) == '$') {
                String sub = w.substring(1);
                try {
                    long price = Long.valueOf(sub);
                    double newPrice = price * factor;
                    String str = String.format("$%.2f", newPrice);
                    sb.append(str);
                } catch (Exception e) {
                    sb.append(w);
                }
            } else {
                sb.append(w);
            }
        }

        return sb.toString();
    }

    /**
     * LC-935 Knight Dialer
     *
     * Medium
     *
     * Star: 12:25 AM
     * End: 36 hours with LC editorial
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Disabled
    @Test
    public void testKnightDialer() {
        log.debug(() -> "Start");

        // int n = 1;
        // int expected = 10;

        // int n = 2;
        // int expected = 20;

        int n = 3131;
        int expected = 136006598;

        var ret = knightDialerLuke(n);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-935 Knight Dialer
     *
     * Medium
     *
     * Star: 12:25 AM
     * End: 36 hours with LC editorial
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int knightDialerLuke(int n) {
        int[][] dial = {
                { 4, 6 }, // 0
                { 8, 6 }, // 1
                { 7, 9 }, // 2
                { 4, 8 }, // 3
                { 3, 9, 0 }, // 4
                {}, // 5
                { 1, 7, 0 }, // 6
                { 6, 2 }, // 7
                { 1, 3 }, // 8
                { 4, 2 } // 9
        };

        int MOD = (int) 1e9 + 7;

        int[][] memo = new int[10][n + 1];

        int ans = 0;
        for (int key = 0; key < 10; key++) {
            ans = (ans + dfsKnightDialer(dial, key, n - 1, MOD, memo)) % MOD;
        }

        return ans;
    }

    int dfsKnightDialer(int[][] dial, int key, int n, int MOD, int[][] memo) {
        if (n == 0) {
            return 1;
        }

        if (memo[key][n] != 0) {
            return memo[key][n];
        }

        int ans = 0;
        for (int nextKey : dial[key]) {
            ans = (ans + dfsKnightDialer(dial, nextKey, n - 1, MOD, memo)) % MOD;
        }

        memo[key][n] = ans;

        return ans;
    }

    public int dpLcKnightDialer(int remain, int key, int[][] memo, int[][] jumps, int MOD) {
        if (remain == 0) {
            return 1;
        }

        if (memo[remain][key] != 0) {
            return memo[remain][key];
        }

        int ans = 0;
        for (int nextKey : jumps[key]) {
            ans = (ans + dpLcKnightDialer(remain - 1, nextKey, memo, jumps, MOD)) % MOD;
        }

        memo[remain][key] = ans;
        return ans;
    }

    public int knightDialerLc(int n) {

        int MOD = (int) 1e9 + 7;
        int[][] jumps = {
                { 4, 6 },
                { 6, 8 },
                { 7, 9 },
                { 4, 8 },
                { 3, 9, 0 },
                {},
                { 1, 7, 0 },
                { 2, 6 },
                { 1, 3 },
                { 2, 4 }
        };

        int[][] memo = new int[n + 1][10];

        int ans = 0;
        for (int key = 0; key < 10; key++) {
            ans = (ans + dpLcKnightDialer(n - 1, key, memo, jumps, MOD)) % MOD;
        }

        return ans;
    }

    /**
     * LC-545 Boundary of Binary Tree
     *
     * Medium
     *
     * Star: 3:01 PM
     * End: 8:24 PM
     *
     * Runtime: Beats 11% of java users
     * Memory: Beats 40% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Disabled
    @Test
    public void testBoundaryOfBinaryTree() {
        log.debug(() -> "Start");

        Integer[] root = { 1, null, 2, 3, 4 };

        TreeNode rootNode = TreeNode.toTreeBfsWithNullIntegers(root);

        var ret = boundaryOfBinaryTree(rootNode);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-545 Boundary of Binary Tree
     *
     * Medium
     *
     * Star: 3:01 PM
     * End: 8:24 PM
     *
     * Runtime: Beats 11% of java users
     * Memory: Beats 40% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {

        List<Integer> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        // 1. left boundary
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        TreeNode curr = root.left;

        while (curr != null) {
            if (curr.left == null) {
                if (curr.right == null) {
                    break;
                } else {
                    queue.add(curr);
                    curr = curr.right;
                }
            } else {
                queue.add(curr);
                curr = curr.left;
            }
        }

        while (!queue.isEmpty()) {
            ans.add(queue.poll().val);
        }

        // 2. leaves

        List<Integer> leaves = new ArrayList<>();

        dfs(root.left, leaves);
        dfs(root.right, leaves);

        ans.addAll(leaves);

        // 3. right boundary

        curr = root.right;
        Stack<TreeNode> stack = new Stack<>();

        while (curr != null) {
            if (curr.right == null) {
                if (curr.left == null) {
                    break;
                } else {
                    stack.push(curr);
                    curr = curr.left;
                }
            } else {
                stack.push(curr);
                curr = curr.right;
            }
        }

        while (!stack.isEmpty()) {
            ans.add(stack.pop().val);
        }

        return ans;
    }

    void dfs(TreeNode root, List<Integer> leaves) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            leaves.add(root.val);
            return;
        }

        dfs(root.left, leaves);
        dfs(root.right, leaves);
    }

    /**
     * LC-994 Rotting Orange
     *
     * [solution]<https://leetcode.com/problems/rotting-oranges/submissions/1212283200/>
     *
     * Medium
     *
     * Star: 10:23 PM
     * End: 11:39 PM
     *
     * Runtime 1 ms: Beats 99.99% of users with Java
     * Memory 41.72 MB: Beats 96.29% of users with Java
     *
     * Time: Time: O(ROWS x COLS * Math.max(ROWS, COLS))
     * Space: O(m * n)
     */
    @Test
    @Disabled
    public void testRottingOrange() {
        log.debug(() -> "Start");

        int[][] grid = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
        int expected = -1;

        var ret = orangesRotting(grid);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    /**
     * LC-994 Rotting Orange
     *
     * [solution]<https://leetcode.com/problems/rotting-oranges/submissions/1212283200/>
     *
     * Medium
     *
     * Star: 10:23 PM
     * End: 11:39 PM
     *
     * Runtime 1 ms: Beats 99.99% of users with Java
     * Memory 41.72 MB: Beats 96.29% of users with Java
     *
     * Time: Time: O(ROWS x COLS * Math.max(ROWS, COLS))
     * Space: O(m * n)
     */
    // 2 1 2
    // 0 1 1
    // 1 0 1
    public int orangesRotting(int[][] grid) {
        int ROWS = grid.length;
        int COLS = grid[0].length;

        int[][] newGrid = new int[ROWS + 2][COLS + 2];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                newGrid[r + 1][c + 1] = grid[r][c];
            }
        }

        int minutes = 0;

        boolean done = false;

        while (!done) {
            done = true;
            boolean hasGood = false;
            for (int r = 1; r <= ROWS; r++) {
                for (int c = 1; c <= COLS; c++) {
                    if (newGrid[r][c] == 1) {
                        hasGood = true;
                        if (hasBadNeighbor(r, c, newGrid)) {
                            newGrid[r][c] = 3;
                            done = false;
                        }
                    }
                }
            }

            if (!done) {
                minutes++;
                for (int r = 1; r <= ROWS; r++) {
                    for (int c = 1; c <= COLS; c++) {
                        if (newGrid[r][c] == 3) {
                            newGrid[r][c] = 2;
                        }
                    }
                }
            }

            if (hasGood && done) {
                return -1;
            }
        }

        return minutes;
    }

    boolean hasBadNeighbor(int row, int col, int[][] grid) {
        //     left                       top                        right                      bottom
        return grid[row][col - 1] == 2 || grid[row - 1][col] == 2 || grid[row][col + 1] == 2 || grid[row + 1][col] == 2;
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
    public void testPseudoPalindromicPaths() {
        log.debug(() -> "Start");

        Integer[] root = { 2, 3, 1, 3, 1, null, 1 };
        int expected = 2;

        TreeNode rootNode = TreeNode.toTreeBfsWithNullIntegers(root);

        var ret = pseudoPalindromicPaths(rootNode);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(expected, ret);
    }

    int ctr = 0;
    int[] count = new int[10];

    public int pseudoPalindromicPaths(TreeNode root) {
        // 1. dfs
        // 2. isPalindrom

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        count[root.val]++;

        dfs(root, stack);

        return ctr;
    }

    void dfs(TreeNode root, Stack<TreeNode> stack) {

        // leaf
        if (root.left == null && root.right == null) {
            // List<TreeNode> list = new ArrayList<>(stack);

            log.debug("stack: {}, count: {}", stack.stream().map(a -> a.val).toList(), count);

            if (isPalindrom(stack)) {
                ctr++;
            }
        } else {
            if (root.left != null) {
                stack.push(root.left);
                count[root.left.val]++;

                dfs(root.left, stack);

                stack.pop();
                count[root.left.val]--;
            }

            if (root.right != null) {
                stack.push(root.right);
                count[root.right.val]++;

                dfs(root.right, stack);

                stack.pop();
                count[root.right.val]--;
            }
        }
    }

    boolean isPalindrom(Stack<TreeNode> stack) {

        if (stack.size() < 2) {
            return true;
        }

        int len = stack.size();
        boolean isOdd = len % 2 == 1;

        int oddCount = 0;
        for (int i = 1; i < 10; i++) {
            if (count[i] % 2 == 1) {
                oddCount++;
            }
        }

        if (isOdd) {
            return oddCount == 1;
        } else {
            return oddCount == 0;
        }
    }

    /**
     * LC-935 Knight Dialer
     *
     * Medium
     *
     * Star: 12:25 AM
     * End: 36 hours with LC editorial
     *
     * Runtime: Beats 64% of java users
     * Memory: Beats 76% of java users
     *
     * Time: O(n)
     * Space: O(n)
     */
    @Disabled
    @Test
    public void testNext() {
        log.debug(() -> "Start");
    }
}
