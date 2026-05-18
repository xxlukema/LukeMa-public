package com.learn.test.amzn2024;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SolutionsBeforeMarch09 {

    /**
     * LC-2904
     */
    @Test
    public void testShortestBeautifulSubstring() {
        log.debug(() -> "Start");

        // String s = "1100001110111100100";
        // int k = 8;
        String s = "00011001";
        int k = 3;
        // String s = "1011";
        // int k = 2;

        var ret = shortestBeautifulSubstring(s, k);

        log.debug("Result: {}", () -> ret);

    }

    /**
     * LC-2904
     *
     * Medium
     *
     * Time: O(s.length() - k)
     * Space: O(s.length() - k)
     */
    public String shortestBeautifulSubstring(String s, int k) {

        // 1. find all beautiful strings[]
        // two pointers
        List<String> list = new ArrayList<>();

        char[] chs = s.toCharArray();

        int pos = 0;
        int count = 0;

        for (int i = 0; i <= s.length() - k && pos <= s.length(); i++) {
            if (chs[i] == '0') {
                if (count == k) {
                    list.add(s.substring(i, pos));
                }
                continue;
            }

            while (pos < s.length()) {
                if (chs[pos++] == '0') {
                    continue;
                } else {
                    count++;
                    if (count == k) {
                        list.add(s.substring(i, pos));
                        break;
                    }
                }
            }

            if (chs[i] == '1') {
                count--;
            }
        }

        log.debug(list);

        if (list.size() == 0) {
            return "";
        }

        // 2. remove long strings
        int minLen = list.stream().mapToInt(e -> e.length()).min().getAsInt();

        list = list.stream().filter(e -> e.length() == minLen).toList();

        // 3. find the smallest one
        return list.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList()).get(0);
    }

    /**
     * LC-2861 Maximum Number of Alloys
     * Time Limit Exceeded
     */
    @Test
    public void testMaxNumberOfAlloys() {
        log.debug(() -> "Start");

        Integer[][] compositionArray = { { 1, 1, 1 }, { 1, 1, 10 } };

        List<List<Integer>> composition = new ArrayList<>();
        for (int i = 0; i < compositionArray.length; i++) {
            composition.add(Arrays.asList(compositionArray[i]));
        }

        List<Integer> stock = Arrays.asList(new Integer[] { 0, 0, 0 });
        List<Integer> cost = Arrays.asList(new Integer[] { 1, 2, 3 });
        int n = 3, k = 2, budget = 15;

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = maxNumberOfAlloysBinarySearch(n, k, budget, composition, stock, cost);

        log.debug("Result: {}", () -> ret);

    }

    /**
     * LC-2861 Maximum Number of Alloys
     * Time Limit Exceeded
     *
     * n == composition.size();
     * k == stock.size();
     */
    public int maxNumberOfAlloysLte(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int numAlloys = 0;
        for (int m = 0; m < composition.size(); m++) {
            int alloysForM = 0;
            List<Integer> comp = composition.get(m);
            int remBudget = budget;
            int len = comp.size();

            List<Integer> newStock = new ArrayList<>();
            stock.forEach(e -> newStock.add(e));

            while (remBudget > 0) {
                for (int j = 0; j < len; j++) {
                    if (newStock.get(j) >= comp.get(j)) {
                        newStock.set(j, newStock.get(j) - comp.get(j));
                    } else {
                        remBudget -= cost.get(j) * (comp.get(j) - newStock.get(j));
                        newStock.set(j, 0);
                        if (remBudget < 0) {
                            break;
                        }
                    }
                }

                if (remBudget >= 0) {
                    alloysForM++;
                }
            }

            numAlloys = Math.max(numAlloys, alloysForM);
        }

        return numAlloys;
    }

    /**
     * LC-2861 Maximum Number of Alloys
     * Binary Search - LTE
     *
     * n == composition.size();
     * k == stock.size();
     */
    public int maxNumberOfAlloysBinarySearch(
            int n,
            int k,
            int budget,
            List<List<Integer>> composition,
            List<Integer> stock,
            List<Integer> cost) {

        int maxNum = 0;

        for (int m = 0; m < composition.size(); m++) {
            maxNum = Math.max(maxNum, getMaxNumberOfAlloys(budget, composition.get(m), stock, cost));
        }

        return maxNum;
    }

    private int getMaxNumberOfAlloys(
            int budget,
            List<Integer> comp,
            List<Integer> stock,
            List<Integer> cost) {

        int eachCost = 0;
        for (int i = 0; i < comp.size(); i++) {
            eachCost += cost.get(i);
        }

        int max = budget / eachCost;
        int min = 0;
        int guess = 0;

        while (min <= max) {
            guess = min + (max - min) / 2;

            boolean can = canMakeNumberOfAlloys(guess, budget, comp, stock, cost);

            /**
             * Trick: Make min 1 larger than last guess, or max 1 smaller than last guess.
             */
            if (can) {
                min = guess + 1;
            } else {
                max = guess - 1;
            }
        }

        return guess;
    }

    private boolean canMakeNumberOfAlloys(
            int intent,
            int budget,
            List<Integer> comp,
            List<Integer> stock,
            List<Integer> cost) {

        int len = comp.size();

        int totalCost = 0;

        for (int i = 0; i < len; i++) {
            totalCost = cost.get(i) * (intent - stock.get(i));
        }

        return totalCost <= budget;
    }

    /**
     * LC-2870 Maximum Number of Alloys
     * Time Limit Exceeded
     */
    @Test
    public void testMinOperations() {
        log.debug(() -> "Start");

        // int[] nums = { 2, 3, 3, 2, 2, 4, 2, 3, 4 };
        // int[] nums = { 14, 12, 14, 14, 12, 14, 14, 12, 12, 12, 12, 14, 14, 12, 14, 14, 14, 12, 12 };
        int[] nums = { 2, 3, 3, 2, 2, 4, 2, 3, 4 };

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = minOperations(nums);

        log.debug("Result: {}", () -> ret);

    }

    /**
     * LC-2870
     *
     * Time: O(n)
     * Space: O(n)
     *
     * Rumtime: Beats 18%
     */
    public int minOperations(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        log.debug(map);

        int count = 0;
        for (int key : map.keySet()) {
            int val = map.get(key);
            int dom3 = val / 3;
            int rem3 = val % 3;

            int dom2 = 0;

            if (rem3 == 0) {
                count += dom3;
                continue;
            } else if (rem3 == 1) {
                if (dom3 == 0) {
                    return -1;
                } else {
                    dom3--;
                    dom2 = (val - dom3 * 3) / 2;
                }
            } else {
                dom2 = 1;
            }

            count += dom3 + dom2;
        }

        return count;
    }

    /**
    * LC-2832 Maximum Number of Alloys
    * Time Limit Exceeded
    */
    @Test
    public void testMaximumLengthOfRanges() {
        log.debug(() -> "Start");

        int[] nums = { 1, 5, 4, 3, 6 };

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = maximumLengthOfRangesLC(nums);

        log.debug("Result: {}", () -> ret);

    }

    /**
     * LC-2832 Brutal - LTE
     */
    public int[] maximumLengthOfRangesBrutal(int[] nums) {
        int[] ans = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            int left = i;
            int right = i;

            while (left > 0 && nums[left - 1] <= nums[i]) {
                left--;
            }

            while (right < nums.length && nums[right] <= nums[i]) {
                right++;
            }

            ans[i] = right - left;
        }

        return ans;
    }

    /**
     * LC-2832 LC - LTE
     *
     */
    public int[] maximumLengthOfRangesLC(int[] nums) {

        int len = nums.length;

        int[] ans = new int[len];

        record MyPair(int max, int pos) {
        }

        MyPair[] leftPairs = new MyPair[len];
        MyPair[] rightPairs = new MyPair[len];

        int leftMax = nums[0];
        int rightMax = nums[len - 1];

        leftPairs[0] = new MyPair(leftMax, 0);
        rightPairs[len - 1] = new MyPair(rightMax, len - 1);

        // 1,5,4,3,6
        // leftPairs:
        // 1,5,5,5,6
        // 0,1,1,1,4
        // rightPairs:
        // 6,6,6,6,6
        // 4,4,4,4,4

        for (int i = 1; i < len; i++) {
            if (nums[i] < leftPairs[i - 1].max) {
                leftPairs[i] = new MyPair(nums[i], i);
            } else {
                leftPairs[i] = new MyPair(leftPairs[i - 1].max, i);
            }
        }

        for (int k = len - 2; k >= 0; k--) {
            if (nums[k] < rightPairs[k + 1].max) {
                rightPairs[k] = new MyPair(nums[k], k);
            } else {
                rightPairs[k] = new MyPair(rightPairs[k + 1].max, k);
            }
        }

        for (int i = 0; i < len; i++) {
            ans[i] = rightPairs[i].pos - leftPairs[i].pos + 1;
        }

        return ans;
    }

    /**
     * LC-2482
     *
     * Medium
     *
     * LTE
     *
     */
    public int[][] onesMinusZerosLte(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] ans = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            int onesRowR = 0;
            int zerosRowR = 0;

            for (int cc = 0; cc < cols; cc++) {
                onesRowR += grid[r][cc] == 1 ? 1 : 0;
            }

            zerosRowR = rows - onesRowR;

            for (int c = 0; c < cols; c++) {
                int onesColC = 0;
                int zerosColC = 0;

                for (int rr = 0; rr < rows; rr++) {
                    onesColC += grid[rr][c] == 1 ? 1 : 0;
                }

                zerosColC = cols - onesColC;

                ans[r][c] = onesRowR + onesColC - zerosRowR - zerosColC;
            }
        }

        return ans;
    }

    /**
     * LC-2482
     *
     * Medium
     *
     * Time: O(rows * cols)
     * Space: O(rows + cols)
     *
     * Rumtime: 13ms, beats 34%
     * Memory: 84mb, beats 89%
     *
     */
    public int[][] onesMinusZeros(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] ans = new int[rows][cols];

        int[] onesRow = new int[rows];
        int[] zerosRow = new int[rows];
        int[] onesCol = new int[cols];
        int[] zerosCol = new int[cols];

        for (int rr = 0; rr < rows; rr++) {
            int ones = 0;
            for (int cc = 0; cc < cols; cc++) {
                if (grid[rr][cc] == 1) {
                    ones++;
                }
            }
            onesRow[rr] = ones;
            zerosRow[rr] = rows - ones;
        }

        for (int cc = 0; cc < cols; cc++) {
            int ones = 0;
            for (int rr = 0; rr < rows; rr++) {
                if (grid[rr][cc] == 1) {
                    ones++;
                }
            }
            onesCol[cc] = ones;
            zerosCol[cc] = cols - ones;
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ans[r][c] = onesRow[r] + onesCol[c] - zerosRow[r] - zerosCol[c];
            }
        }

        return ans;
    }

    /**
     * LC-2461
     */
    @Test
    public void testMaximumSubarraySum() {
        log.debug(() -> "Start");

        int[] nums = { 1, 5, 4, 2, 9, 9, 9 };
        int k = 3;

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = maximumSubarraySum(nums, k);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(15, ret);

    }

    /**
     * LC-2461
     *
     * Two pointers
     *
     * Mdeium
     *
     * Time: O(n)
     * Space: O(k)
     *
     * Runtime: 33 ms, beats 90%
     * Memory: 57 mb, beats 91%
     */
    public long maximumSubarraySum(int[] nums, int k) {
        long max = Integer.MIN_VALUE;

        Set<Integer> seen = new HashSet<>();

        int left = 0;
        int right = 0;

        long sum = 0;
        while (right < nums.length) {
            while (right - left < k && right < nums.length) {
                int val = nums[right];
                if (seen.contains(val)) {
                    seen.remove(nums[left]);
                    sum -= nums[left];
                    left++;
                } else {
                    seen.add(val);
                    sum += val;
                    right++;
                }
            }

            // log.debug("seen: {}, sum: {}", seen, sum);

            if (seen.size() == k) {
                max = Math.max(max, sum);
                seen.remove(nums[left]);
                sum -= nums[left];
                left++;
            } else {
                break;
            }
        }

        return max == Integer.MIN_VALUE ? 0 : max;
    }

    /**
     * LC-2461
     */
    @Test
    public void testMinimumSwaps() {
        log.debug(() -> "Start");

        int[] nums = { 3, 4, 5, 5, 3, 1 };
        // int[] nums = { 3, 2 };

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = minimumSwaps(nums);

        log.debug("Result: {}", () -> ret);

        Assertions.assertEquals(6, ret);

    }

    /**
     * LC-2340
     *
     * Medium
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: 3 ms, beats 95%
     * Memory: 58 mb, beats 7%
     */
    public int minimumSwaps(int[] nums) {
        int len = nums.length;

        int min = nums[0];
        int idxMin = 0;
        int max = nums[0];
        int idxMax = 0;

        for (int i = 1; i < len; i++) {
            if (nums[i] < min) {
                min = nums[i];
                idxMin = i;
            } else if (nums[i] >= max) {
                max = nums[i];
                idxMax = i;
            }
        }

        if (idxMin == idxMax) {
            return 0;
        } else if (idxMin < idxMax) {
            return idxMin + len - 1 - idxMax;
        } else {
            return idxMin + len - 1 - idxMax - 1;
        }
    }

    /**
     * LC-767
     */
    @Test
    public void testReorganizeString() {
        log.debug(() -> "Start");

        String s = "vvvlo";

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = reorganizeString(s);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(6, ret);
    }

    /**
     * LC-767
     *
     * Medium
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     *
     * Runtime: Beats 77%
     * Memory: Beats 95%
     */
    public String reorganizeString(String s) {
        int len = 26;

        int[] count = new int[len];
        char[] chs = s.toCharArray();

        for (char ch : chs) {
            int idx = ch - 'a';
            count[idx]++;
        }

        int idxMax = 0;
        int max = count[idxMax];
        for (int i = 1; i < len; i++) {
            if (max < count[i]) {
                idxMax = i;
                max = count[idxMax];
            }
        }

        int sLen = chs.length;

        if (max > (sLen + 1) / 2) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        while (sb.length() < sLen) {
            // 1. find max
            idxMax = 0;
            max = count[idxMax];
            for (int i = 1; i < len; i++) {
                if (max < count[i]) {
                    idxMax = i;
                    max = count[idxMax];
                }
            }

            // 2. find nextMax
            int idxNextToMax = 0;
            int nextToMax = 0;
            for (int i = 0; i < len; i++) {
                if (i == idxMax) {
                    continue;
                }

                if (count[i] > nextToMax) {
                    idxNextToMax = i;
                    nextToMax = count[idxNextToMax];
                }
            }

            sb.append((char) ('a' + idxMax));
            count[idxMax]--;

            if (count[idxNextToMax] == 0) {
                break;
            } else {
                sb.append((char) ('a' + idxNextToMax));
                count[idxNextToMax]--;
            }
        }

        return sb.toString();
    }

    /**
     * LC-1152
     */
    @Test
    public void testMostVisitedPattern() {
        log.debug(() -> "Start");

        // String[] username = { "joe", "joe", "joe", "james", "james", "james", "james", "mary", "mary", "mary" };
        // int[] timestamp = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        // String[] website = { "home", "about", "career", "home", "cart", "maps", "home", "home", "about", "career" };

        String[] username = { "zkiikgv", "zkiikgv", "zkiikgv", "zkiikgv" };
        int[] timestamp = { 436_363_475, 710_406_388, 386_655_081, 797_150_921 };
        String[] website = { "wnaaxbfhxp_2", "mryxsjc_3", "oz_1", "wlarkzzqht_4" };

        // var ret = maxNumberOfAlloysLte(n, k, budget, composition, stock, cost);
        var ret = mostVisitedPattern(username, timestamp, website);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(6, ret);
    }

    /**
     * LC-1152
     *
     * Medium
     *
     */
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        Map<String, Integer> scores = new HashMap<>();

        int len = username.length;

        record TsWeb(int ts, String web) {
        }

        Map<String, Queue<TsWeb>> userQueueMap = new HashMap<>();

        for (int i = 0; i < len; i++) {
            String user = username[i];
            if (!userQueueMap.containsKey(user)) {
                userQueueMap.put(user, new PriorityQueue<>((a, b) -> a.ts - b.ts));
            }
            userQueueMap.get(user).add(new TsWeb(timestamp[i], website[i]));
        }

        log.debug("userQueueMap: {}", userQueueMap);

        for (String user : userQueueMap.keySet()) {
            int count = 0;
            Queue<TsWeb> queue = userQueueMap.get(user);
            Queue<String> webs = new LinkedList<>();
            while (!queue.isEmpty()) {
                TsWeb ts = queue.poll();
                count++;
                webs.add(ts.web);
                if (count < 3) {
                    continue;
                }

                if (webs.size() < 3) {
                    break;
                }

                String key = webs.stream().collect(Collectors.joining("-"));
                scores.put(key, scores.getOrDefault(key, 0) + 1);

                webs.poll();
            }
        }

        log.debug("score: {}", scores);

        int max = scores.values().stream().mapToInt(e -> e.intValue()).max().getAsInt();

        List<String> list = new ArrayList<>();

        scores.forEach((k, v) -> {
            if (v == max) {
                list.add(k);
            }
        });

        if (list.size() == 1) {
            return Arrays.asList(list.get(0).split("-"));
        }

        // find the smallest
        return list.stream().map(e -> Arrays.asList(e.split("-"))).sorted((e1, e2) -> {

            int c = e1.get(0).compareTo(e2.get(0));
            if (c != 0) {
                return c;
            }

            c = e1.get(1).compareTo(e2.get(1));
            if (c != 0) {
                return c;
            }

            return e1.get(2).compareTo(e2.get(2));
        }).toList().get(0);
    }

    /**
     * LC-2055
     *
     * Medium
     *
     * Trick: Use `TreeSet::ceiling()` and `TreeSet::floor()`
     *
     * Runtime: Beats 15%
     * Memory: Beats 5%
     *
     * Time: O(queries.length * log(s.length()) + s.length())
     * Space: O(queries.length)
     */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int len = queries.length;
        char[] chs = s.toCharArray();

        int[] ans = new int[len];

        Map<Integer, Integer> memo = new HashMap<>();
        TreeSet<Integer> candles = new TreeSet<>();

        int count = 0;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] == '*') {
                count++;
            } else {
                /**
                 * Time: O(len)
                 */
                candles.add(i);
                memo.put(i, count);
            }
        }

        if (count == chs.length) {
            return ans;
        }

        for (int i = 0; i < len; i++) {
            ans[i] = platesBetweenCandlesHelper(chs, queries[i], memo, candles);
        }

        return ans;
    }

    private int platesBetweenCandlesHelper(char[] chs, int[] range, Map<Integer, Integer> memo, TreeSet<Integer> candles) {
        int left = range[0];
        int right = range[1];

        /**
         * Trick: Use `TreeSet::ceiling()` and `TreeSet::floor()`
         *
         * Time: O(log(candles.size()))
         */
        Integer ceiling = candles.ceiling(left);
        Integer floor = candles.floor(right);

        if (ceiling == null || floor == null || floor <= ceiling) {
            return 0;
        } else {
            return memo.get(floor) - memo.get(ceiling);
        }
    }

    /**
     * LC-2055
     *
     * Medium
     *
     */
    public int[] platesBetweenCandlesLte(String s, int[][] queries) {
        int len = queries.length;
        char[] chs = s.toCharArray();

        int[] ans = new int[len];

        for (int i = 0; i < len; i++) {
            int[] range = queries[i];
            int left = range[0];
            int right = range[1];

            while (left < right) {
                if (chs[left] == '*') {
                    left++;
                } else {
                    break;
                }
            }

            while (left < right) {
                if (chs[right] == '*') {
                    right--;
                } else {
                    break;
                }
            }

            int count = 0;

            while (left < right) {
                if (chs[left++] == '*') {
                    count++;
                }
            }

            ans[i] = count;
        }

        return ans;
    }

    /**
    * LC-2268
    */
    @Test
    public void testMinimumKeypresses() {
        log.debug(() -> "Start");

        String s = "abcdefghijkl";
        // String s = "apple";

        var ret = minimumKeypressesLc(s);

        log.debug("Result: {}", () -> ret);

        // Assertions.assertEquals(6, ret);
    }

    /**
     * LC-2268
     *
     * Medium
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: Beat2 25%
     * Memory: Beats 56%
     */
    public int minimumKeypresses(String s) {
        int len = 26;

        int[] count = new int[len];

        char[] chs = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            int idx = chs[i] - 'a';
            count[idx]++;
        }

        record Rank(int idx, int cnt) {
        }

        List<Rank> list = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            list.add(new Rank(i, count[i]));
        }

        list = list.stream().filter(e -> e.cnt != 0).sorted((a, b) -> b.cnt - a.cnt).toList();

        Map<Integer, Integer> rank = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            rank.put(list.get(i).idx, i);
        }

        int total = 0;
        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];

            int idx = ch - 'a';

            int r = rank.get(idx);

            int dom = r / 9;

            total += dom + 1;
        }

        return total;
    }

    /**
     * LC-2268
     *
     * Medium
     *
     * Time: O(n)
     * Space: O(1)
     *
     * Runtime: Beats 26%
     * Memory: Beats 5%
     */
    public int minimumKeypressesLc(String s) {
        int len = 26;

        Integer[] count = new Integer[len];

        Arrays.fill(count, 0);

        char[] chs = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            int idx = chs[i] - 'a';
            count[idx]++;
        }

        List<Integer> list = Arrays.asList(count);

        list = list.stream().filter(e -> e > 0).sorted((a, b) -> b - a).toList();

        int total = 0;

        for (int i = 0; i < list.size(); i++) {
            int dom = i / 9;
            total += (dom + 1) * list.get(i);
        }

        return total;
    }

}
