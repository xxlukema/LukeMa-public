package com.learn.java8;


import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StreamTest {

    @Test
    public void testFilter()
        throws Exception {
        log.info("Begin Test");

        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList.stream().filter(s -> s.startsWith("c")).map(String::toUpperCase).sorted().forEach(log::info);

        log.info("End Test.");

    }

    @Test
    public void testFindFirst()
        throws Exception {
        log.info("Begin Test");

        Arrays.asList("a1", "a2", "a3").stream().findFirst().ifPresent(log::info);

        log.info("End Test.");

    }

    @Test
    public void testStreamOf()
        throws Exception {
        log.info("Begin Test");

        Stream.of("a1", "a2", "a3").findFirst().ifPresent(log::info);

        log.info("End Test.");

    }

    @Test
    public void testRange()
        throws Exception {
        log.info("Begin Test");

        IntStream.range(1, 4).forEach(log::info);

        log.info("End Test.");

    }

    @Test
    public void testAverage()
        throws Exception {
        log.info("Begin Test");

        Arrays.stream(new int[] { 1, 2, 3 }).map(n -> 2 * n + 1).average().ifPresent(log::info);

        log.info("End Test.");

    }

    @Test
    public void testMapToInt()
        throws Exception {
        log.info("Begin Test");

        Stream.of("a1", "a2", "a3").map(s -> s.substring(1)).mapToInt(Integer::parseInt).max().ifPresent(log::info);

        log.info("End Test.");

    }

    @Test
    public void testToObject()
        throws Exception {
        log.info("Begin Test");

        IntStream.range(1, 4).mapToObj(i -> "a" + i).forEach(log::info);

        log.info("End Test.");

    }

    @Test
    public void testDoubleToInt()
        throws Exception {
        log.info("Begin Test");

        Stream.of(1.0, 2.0, 3.0).mapToInt(Double::intValue).mapToObj(i -> "a" + i).forEach(log::info);

        log.info("End Test.");

    }

    @Test
    public void testOrder()
        throws Exception {
        log.info("Begin Test");

        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            log.info("filter: " + s);
            return true;
        }).forEach(s -> log.info("forEach: " + s));

        log.info("End Test.");

    }

    @Test
    public void testOrderMatters()
        throws Exception {
        log.info("Begin Test");

        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("A");
        }).forEach(s -> System.out.println("forEach: " + s));

        log.info("End Test.");

    }

    @Test
    public void testApplyMatch()
        throws Exception {
        log.info("Begin Test");

        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).anyMatch(s -> {
            System.out.println("anyMatch: " + s);
            return s.startsWith("A");
        });

        log.info("End Test.");
    }

    @Test
    public void testSorted()
        throws Exception {
        log.info("Begin Test");

        Stream.of("d2", "a2", "b1", "b3", "c").sorted((s1, s2) -> {
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        }).filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> log.info("forEach: " + s));

        log.info("End Test.");
    }

    @Test
    public void testSortedChain()
        throws Exception {
        log.info("Begin Test");

        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).sorted((s1, s2) -> {
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> log.info("forEach: " + s));

        log.info("End Test.");
    }

    @Test
    public void testReuse()
        throws Exception {
        log.info("Begin Test");

        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true); // ok
        streamSupplier.get().noneMatch(s -> true); // ok

        log.info("End Test.");
    }

    @Test
    public void testReuse2()
        throws Exception {
        log.info("Begin Test");

        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true); // ok
        streamSupplier.get().noneMatch(s -> true); // ok

        log.info("End Test.");
    }

    @Test
    public void testMap()
        throws Exception {

        log.info("Begin Test");

        List<String> myList1 = Arrays.asList("a1", "a2");
        List<String> myList2 = Arrays.asList("b1", "b2", "b3");
        List<String> myList3 = Arrays.asList("c1", "c2");

        List<List<String>> myLists = Arrays.asList(myList1, myList2, myList3);
        List<String> myListA = myLists.stream().flatMap(list -> list.stream()).collect(Collectors.toList());

        myListA.forEach(str -> log.info(str));

        List<Stream<String>> myListB = myLists.stream().map(list -> list.stream()).collect(Collectors.toList());

        myListB.forEach(stream -> {
            stream.forEach(str -> log.info(str));
        });

        log.info("End Test.");
    }
}
