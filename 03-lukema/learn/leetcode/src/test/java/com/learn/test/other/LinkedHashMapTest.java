package com.learn.test.other;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class LinkedHashMapTest {

    @Test
    public void testLinkedHashMap() {

        Map<Integer, Integer> map = new LinkedHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(1, 11);

        log.debug("map: {}", map);
        log.debug("map keySet: {}", map.keySet());

        map.remove(2);

        map.put(2, 2);

        log.debug("map: {}", map);
        log.debug("map keySet: {}", map.keySet());
    }

    @Test
    public void testLinkedHashSet() {
        Set<Integer> set = new LinkedHashSet<>();

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);

        log.debug("set: {}", set);

        set.remove(2);

        set.add(2);
        set.add(1);

        log.debug("set: {}", set);
    }
}
