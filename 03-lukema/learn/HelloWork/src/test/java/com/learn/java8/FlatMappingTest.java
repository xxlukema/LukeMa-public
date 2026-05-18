package com.learn.java8;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * Stream operations (filter, sum, distinct…) and collectors do not support Streams of nested structures:
 *
 * Stream<String[]>
 * Stream<Set<String>>
 * Stream<List<String>>
 * Stream<List<Object>>.
 *
 * We need flatMap() to do the following conversion :
 *
 * Stream<String[]>        -> flatMap ->   Stream<String>
 * Stream<Set<String>>     -> flatMap ->   Stream<String>
 * Stream<List<String>>    -> flatMap ->   Stream<String>
 * Stream<List<Object>>    -> flatMap ->   Stream<Object>
 *
 */
@Log4j2
public class FlatMappingTest {

    // private static final Logger log = LogManager.getLogger();

    @Test
    public void runTest1()
        throws Exception {
        log.info("Begin Test");

        // Optional
        Optional<String> opt = Optional.of("test");
        Assert.assertEquals(Optional.of("TEST"), opt.map(String::toUpperCase));

        Optional<String> optNull = Optional.ofNullable(null);
        optNull.map(String::toUpperCase).ifPresent(log::info);
        String value = optNull.map(String::toUpperCase).orElse("Default");
        log.info("value = " + value);

        //Stream<String[]>
        // @formatter:off
        String[][] data = {
                { "a", "b" },
                { "c", "d" },
                { "e", "f" }
            };
        // @formatter:on

        log.info("String [0][1] = " + data[0][1]);

        Stream<String[]> temp = Arrays.stream(data);
        /*
        temp.forEach(e -> {
            log.debug("");
            Stream.of(e).forEach(log::debug);
        });
        */
        Stream<String> stringStream = temp.flatMap(x -> Arrays.stream(x));
        stringStream.forEach(System.out::println);
        // Stream<String> stream = stringStream.filter(x -> "a".equals(x));
        // stream.forEach(System.out::println);

        // Stream<Set<String>>

        Student student1 = new Student();
        student1.setName("mkyong");
        student1.addToBookNameSet("Java 8 in Action");
        student1.addToBookNameSet("Spring Boot in Action");
        student1.addToBookNameSet("Effective Java (2nd Edition)");

        Student student2 = new Student();
        student2.setName("zilap");
        student2.addToBookNameSet("Learning Python, 5th Edition");
        student2.addToBookNameSet("Effective Java (2nd Edition)");

        List<Student> list = Arrays.asList(student1, student2);

        // @formatter:off
        List<String> collect = list.stream()
                        .map(Student::getBookNameSet)      // Stream<Set<String>>
                        .flatMap(Set<String>::stream)      // Stream<String>
                        .distinct()
                        .collect(Collectors.toList());
        // @formatter:on

        collect.forEach(x -> System.out.println(x));

        // Stream<int []>

        int[] intArray = { 1, 2, 3 };

        //1. Stream<int[]>
        Stream<int[]> streamArray = Stream.of(intArray);

        //2. Stream<int[]> -> flatMap -> IntStream
        IntStream intStream = streamArray.flatMapToInt(x -> Arrays.stream(x));

        intStream.forEach(x -> System.out.println(x));

        log.info("End Test.");
    }

}
