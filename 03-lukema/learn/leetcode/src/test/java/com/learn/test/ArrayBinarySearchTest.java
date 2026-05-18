package com.learn.test;


import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * If not found, it returns a negative number. The absolute value of the negative number is the
 * insertion index with first index starts with 1.
 * Therefore, the `Math.abs(negative number) - 1` is the insertion index when index starts with 0.
 */
@Log4j2
public class ArrayBinarySearchTest {

    @Test
    public void testBinarySearch() {
        int[] nums = { -1 };

        var ans = Arrays.binarySearch(nums, 1);

        log.debug("ans: {}", () -> ans);

        log.debug("ans: {}", () -> Arrays.binarySearch(nums, -2));

    }

    @Test
    public void testBinarySearchPerson() {
        record Person(String name, int age) {
        }

        Person[] persons = { new Person("0a", 0),
                new Person("1b", 1),
                new Person("2c", 3),
                new Person("3d", 3),
        };

        var index = Arrays.binarySearch(persons, new Person("new", 5), (a, b) -> a.age - b.age);

        log.debug("index: {}", index);

    }

}
