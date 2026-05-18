package com.learn.dp;

import lombok.extern.log4j.Log4j2;

/**
 * https://www.educative.io/courses/grokking-dynamic-programming-patterns-for-coding-interviews/RM1BDv71V60
 * 
 * Given two integer arrays to represent weights and profits of 'N' items, we need to find a subset of these
 * items which will give us maximum profit such that their cumulative weight is not more than a given number
 * 'C'. Write a function that returns the maximum profit. Each item can only be selected once, which means
 * either we put an item in the knapsack or skip it.
 * 
 * Method 1: Topdown Memoization
 */
@Log4j2
public class KnapsackMemo {

    public static void main(String[] args) {
        log.debug("{}", () -> "Start");

        /*
        KnapsackMemo knapsackMemo = new KnapsackMemo();
        int[] profits = { 1, 6, 10, 16 };
        int[] weights = { 1, 2, 3, 5 };

        log.debug(" <<: {}", ()-> (1 << 3));
        */


        /*

        List<Integer> profixList = Arrays.stream(profits).boxed().toList();
        List<Integer> weightList = Arrays.stream(weights).boxed().toList();


        int maxProfit = knapsackMemo.solveKnapsack(profits, weights, 7);
        log.debug("Total knapsack profit ---> {}", ()-> maxProfit);
        maxProfit = knapsackMemo.solveKnapsack(profits, weights, 6);
        log.debug("Total knapsack profit ---> {}", ()-> maxProfit);
        */
    }

    /**
     * Brute Force
     */
    /*
    private int solveKnapsack(int[] profits, int[] weights, int maxWeight) {

         // 1. Build sacks
        Integer[] sack = buildSack(weights, maxWeight);

        return 0;
    }

    private Integer[] buildSack(int[] weights, int idx, int maxWeight) {
        List<Integer> sack = new ArrayList<>();
        while (idx < weights.length && weight(sack) < maxWeight) {
            sack.add(weights[idx++]);
        }

        return sack.toArray(new Integer[0]);
    }

    private int weight(List<Integer> sack) {
        return sack.stream().reduce(0, (a, b) -> a + b);
    }
    */
}
