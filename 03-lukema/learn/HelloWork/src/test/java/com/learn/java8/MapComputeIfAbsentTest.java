package com.learn.java8;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import lombok.extern.log4j.Log4j2;


// @TestMethodOrder(MethodOrderer.MethodName.class)
// @TestMethodOrder(MethodOrderer.DisplayName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class MapComputeIfAbsentTest {

    private int counter = 0;

    /**
     * REF: LC - 138 - Copy List With Random Pointer
     * - `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls
     * - `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`
     */
    private final Map<Integer, String> hashmMap = new HashMap<>();

    private final Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

    @Test
    @Order(0)
    public void testComputerIfAbsentFailFastWitConcurrentHashMap() {

        /**
         * REF: LC - 138 - Copy List With Random Pointer
         * - `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls
         * - `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`
         */

        recursionConcurrentHashMap();

        log.debug(() -> "Test Success.");

        /**
         * In case of recursion or cross thread calls, for HashMap, do the following. Do NOT use "HashMap.computeIfAbsent()":
         */
        /*
         *
         *   if (!hashMap.containsKey(1)) {
         *       hashMap.put(1, "my String");
         *   }
         *   return hashMap.get(1);
         *
         */
    }

    @Test
    @Order(0)
    public void testComputerIfAbsentFailFastWitHashMap() {

        /**
         * REF: LC - 138 - Copy List With Random Pointer
         * - `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls
         * - `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`
         */

        recursionHashMap();

        log.debug(() -> "Test Fail.");

        // Assertions.fail("It should not reach here.");

        /**
         * In case of recursion or cross thread calls, for HashMap, do the following. Do NOT use "HashMap.computeIfAbsent()":
         */
        /*
         *
         *   if (!hashMap.containsKey(1)) {
         *       hashMap.put(1, "my String");
         *   }
         *   return hashMap.get(1);
         *
         */
    }

    /**
     * REF: LC - 138 - Copy List With Random Pointer
     * - `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls
     * - `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`
     */
    void recursionConcurrentHashMap() {
        concurrentHashMap.computeIfAbsent(counter, key -> {
            return String.format("Integer is %d", key);
        });

        if (counter++ > 3) {
            log.debug("concurrentHashMap: {}", () -> concurrentHashMap);

            return;
        } else {
            recursionHashMap();
        }
    }

    /**
     * REF: LC - 138 - Copy List With Random Pointer
     * - `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls
     * - `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`
     *
     * In case of recursion or cross thread calls, for HashMap, do the following. Do NOT use "HashMap.computeIfAbsent()":
     *
     *   if (!hashMap.containsKey(key)) {
     *       hashMap.put(key, "my String " + key);
     *   }
     *   return hashMap.get(1);
     *
     */
    void recursionHashMap() {
        hashmMap.computeIfAbsent(counter, key -> {
            return String.format("Integer is %d", key);
        });

        /**
         * For HashMap in recursive calls, do traditional way:
         */
        /*
        if(!hashmMap.containsKey(counter)) {
            hashmMap.put(counter, String.format("Integer is %d", counter));
        }
        // return hashmMap.remove(counter);
        */

        if (counter++ > 3) {
            log.debug("hashMap: {}", () -> hashmMap);

            return;
        } else {
            recursionHashMap();
        }
    }

    @Test
    @Order(1)
    public void testComputerIfAbsent() {
        log.debug(() -> "Start Test");

        Map<String, String> map = new HashMap<>();
        String str = map.computeIfAbsent("Tom", k -> k + " and Jerry");

        Assertions.assertEquals("Tom and Jerry", map.get("Tom"));
        Assertions.assertEquals("Tom and Jerry", str);

        log.debug("Complete Test. str: {}", () -> str);
    }

    /**
     * Map.getOrDefault(key, default) - Does NOT put default into map. You have to put the key/default explicitly!
     */
    @Test
    @Order(2)
    public void testGetOrDefault() {
        log.debug(() -> "Start Test");

        String key = "Tom";

        Map<String, String> map = new HashMap<>();
        String str = map.getOrDefault(key, "Tom and Jerry");

        Assertions.assertNull(map.get(key));
        Assertions.assertEquals("Tom and Jerry", str);

        log.debug("Complete Test. str: {}, map.get(key): {}", () -> str, () -> map.get(key));
    }

    @Test
    @Order(4)
    public void testComputerIfAbsent2() {
        log.debug(() -> "Start Test");

        Map<String, String> map = new HashMap<>();

        map.put("one", "The First Value");

        map.computeIfAbsent("two", key -> "The Second Value");

        /**
         * No effect, because the the key exists.
         */
        map.computeIfAbsent("one", key -> "This has no effect because the key 'one' is present.");

        map.computeIfAbsent("three", key -> "The Third Value");

        /**
         * Has effect, because it is "do it exists".
         */
        map.computeIfPresent("three", (key, val) -> key + " ---> " + val.toUpperCase());

        log.debug("Complete Test. map: {}", () -> map);
    }
}
