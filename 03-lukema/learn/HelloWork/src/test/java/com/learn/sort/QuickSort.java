package com.learn.sort;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


public class QuickSort {

    private static final Logger LOG = LogManager.getLogger();

    // public static final Integer[] ARRAY = { 9, 0, 1, 2, 3, 6, 4, 8, 7, 5 };
    // public static final Integer[] ARRAY = { 8, 2, 9 };
    // public static final Integer[] ARRAY = { 25, 54, 93, 3, 16, 39, 98, 68, 53, 78 };
    public static final Integer[] ARRAY = { 25, 54, 53, 3, 16, 39, 68 };

    @Ignore
    @Test
    public void testSort() {
        LOG.info("Test begin. " + ARRAY.length);

        SortUtils.print(ARRAY);
        sort(ARRAY);
        SortUtils.print(ARRAY);

        LOG.info("Test complete.");
    }

    // @Ignore
    @Test
    public void testSortRandom() {
        LOG.info("Test begin. " + ARRAY.length);

        Random random = new Random();
        List<Integer> list = random.ints(20, 0, 100).boxed().collect(Collectors.toList());
        Integer[] randomArray = list.toArray(new Integer[0]);
        SortUtils.print(randomArray);
        sort(randomArray);
        SortUtils.print(randomArray);

        LOG.info("Test complete.");
    }

    public void sort(Integer[] array) {
        qSort(array, 0, array.length - 1);
    }

    /**
     *  This function takes last element as pivot,
     *  places the pivot element at its correct
     *  position in sorted array, and places all
     *  smaller (smaller than pivot) to left of
     *  pivot and all greater elements to right
     *  of pivot 
     *  */
    public int partition(Integer arr[], int low, int high) {
        int pivot = arr[high];
        int pointer = (low - 1);
        LOG.debug("==== pivot: {}->{}", high, pivot);

        /** 
         * index of smaller element
         */
        for (int j = low; j <= high - 1; j++) {
            LOG.debug("pointer={} j={}", pointer, j);
            /**
             *  If current element is smaller than or equal to pivot,
             *  move pointer to here and swap  
             */
            if (arr[j] <= pivot) {
                pointer++;

                // swap arr[i] and arr[j] 
                if (pointer != j) {
                    int temp = arr[pointer];
                    arr[pointer] = arr[j];
                    arr[j] = temp;
                }
                LOG.debug("++pointer={} j={}", pointer, j);
                SortUtils.print(arr);
            }
        }

        /**
         *  swap arr[pointer+1] and arr[high] (or pivot) 
         */
        if (pointer + 1 != high) {
            int temp = arr[pointer + 1];
            arr[pointer + 1] = arr[high];
            arr[high] = temp;
        }

        return pointer + 1;
    }

    /**
     *  The main function that implements QuickSort()
     *  arr[] --> Array to be sorted,
     *  low --> Starting index,
     *  high --> Ending index 
     *  */
    public void qSort(Integer arr[], int low, int high) {
        if (low < high) {
            /**
             *  pi is partitioning index, arr[pi] is
             *  now at right place 
             *  */
            LOG.info("Partition start: ------------");
            int pi = partition(arr, low, high);
            LOG.info("Partition complete: pi=" + pi);

            // Recursively sort elements before 
            // partition and after partition 
            qSort(arr, low, pi - 1);
            qSort(arr, pi + 1, high);
        }
    }

}
