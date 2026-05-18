package com.learn.sort;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class InsertionSort {

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
        for (int pointer = 1; pointer < array.length; pointer++) {
            for (int head = 0; head < pointer; head++) {
                if (array[pointer] < array[head]) {
                    swapEndToHeadandShiftOneRight(array, head, pointer);
                }
            }
        }
    }

    public void swapEndToHeadandShiftOneRight(Integer[] array, int startIndex, int endIndexInclusive) {
        int tmp = array[endIndexInclusive];
        for (int i = endIndexInclusive; i > startIndex; i--) {
            array[i] = array[i - 1];
        }
        array[startIndex] = tmp;
    }

}
