package com.learn.sort;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class BubbleRecursiveSort {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testSortRandom() {
        LOG.info("Test begin. ");

        Random random = new Random();
        List<Integer> list = random.ints(20, 0, 100).boxed().collect(Collectors.toList());
        Integer[] randomArray = list.toArray(new Integer[0]);
        SortUtils.print(randomArray);
        sort(randomArray);
        SortUtils.print(randomArray);

        LOG.info("Test complete.");
    }

    public void sort(Integer[] array) {
        bubbleSort(array, array.length);
    }

    // A function to implement bubble sort 
    public void bubbleSort(Integer arr[], int n) {
        // Base case 
        if (n == 1)
            return;

        // One pass of bubble sort. After 
        // this pass, the largest element 
        // is moved (or bubbled) to end. 
        for (int i = 0; i < n - 1; i++)
            if (arr[i] > arr[i + 1]) {
                // swap arr[i], arr[i+1] 
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }

        // Largest element is fixed, 
        // recur for remaining array 
        bubbleSort(arr, n - 1);
    }

}
