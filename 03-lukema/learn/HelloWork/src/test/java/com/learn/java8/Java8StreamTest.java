package com.learn.java8;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Java8StreamTest {

    @Test
    public void runMain() {

        log.info("Begin Test.");

        // Count empty strings
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl", null);
        log.info("List: " + strings);
        log.info("Array: " + strings.toArray(new String[0]));

        // 1. Count Empty String
        // In java 7
        long count = getCountEmptyStringUsingJava7(strings);
        log.info("Empty Strings: " + count);

        // 1. Count Empty String
        // In java 8 Stream
        count = strings.stream().filter(str -> str != null && str.isEmpty()).count();
        log.info("Empty Strings: " + count);

        // 2. Count String of Length 3
        // In java 7
        log.info("Empty Strings: " + count);
        count = getCountLength3UsingJava7(strings);
        log.info("Strings of length 3: " + count);

        // 2. Count String of Length 3
        // In java 8 Stream
        count = strings.stream().filter(str -> str != null && str.length() == 3).count();
        log.info("Strings of length 3: " + count);

        // 3. Eliminate empty string
        // In java 7
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        log.info("Filtered List: " + filtered);

        // 3. Eliminate empty string
        // In java 8
        filtered = strings.stream().filter(str -> str != null && !str.isEmpty()).collect(Collectors.toList());
        log.info("Filtered List: " + filtered);

        // 4. Eliminate empty string and join using comma.
        // In java 7
        String mergedString = getMergedStringUsingJava7(strings, ", ");
        log.info("Merged String: " + mergedString);

        // 4. Eliminate empty string and join using comma.
        // In java 8
        mergedString = strings.stream().filter(str -> str != null && !str.isEmpty()).collect(Collectors.joining(", "));
        log.info("Merged String: " + mergedString);

        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);

        log.info("List: " + integers);
        log.info("Highest number in List : " + getMax(integers));
        log.info("Lowest number in List : " + getMin(integers));
        log.info("Sum of all numbers : " + getSum(integers));
        log.info("Average of all numbers : " + getAverage(integers));
        log.info("Random Numbers: ");

        log.info("List: " + integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();
        log.info("Highest number in List : " + stats.getMax());
        log.info("Lowest number in List : " + stats.getMin());
        log.info("Sum of all numbers : " + stats.getSum());
        log.info("Average of all numbers : " + stats.getAverage());

        log.debug("==== stats: {}", stats);

        // 5. get list of square of distinct numbers
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = getSquares(numbers);
        log.info("Squares List: " + squaresList);

        squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        log.info("Squares List: " + squaresList);

        numbers.stream().distinct().forEach(log::info);

        //print ten random numbers
        log.info("Random Numbers: ");

        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            log.info(random.nextInt());
        }
        random.ints().limit(4).sorted().forEach(log::info);

        //parallel processing
        count = strings.parallelStream().filter(str -> str != null && str.isEmpty()).count();
        log.info("Empty Strings: " + count);

        log.info("------------------- 00000000000000");

        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            log.info("map: " + s);
            return s.toUpperCase();
        }).anyMatch(s -> {
            log.info("anyMatch: " + s);
            return s.startsWith("A");
        });

        log.info("------------------- 111111111111");

        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            log.info("map: " + s);
            return s.toUpperCase();
        }).filter(s -> {
            log.info("filter: " + s);
            return s.startsWith("A");
        }).forEach(x -> {
            log.info("====> " + x);
        });

        log.info("------------------- 22222222222222");

        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> System.out.println("forEach: " + s));

        log.info("--------- Ordering of Operation Chain ----------");

        Stream.of("d2", "a2", "b1", "b3", "c", "a0").filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).sorted((s1, s2) -> {
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> System.out.println("forEach: " + s));

        log.info("---------- Reuse ---------");

        Stream<String> stream = Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> s.startsWith("a"));

        stream.anyMatch(s -> true); // ok
        // stream.noneMatch(s -> true); // exception

        // To overcome this limitation we have to to create a new stream chain for every terminal operation we
        // want to execute, e.g. we could create a stream supplier to construct a new stream with all intermediate
        // operations already set up:
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            log.debug("filter: {}", s);
            return s.startsWith("a");
        });

        // Each call to get() constructs a new stream on which we are save to call the desired terminal operation.

        log.debug(() -> "anyMatch");
        streamSupplier.get().anyMatch(s -> true); // ok

        log.debug(() -> "noneMatch. lambda is re-invoked. filter will be invoked again.");
        streamSupplier.get().noneMatch(s -> true); // ok

        // http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/

        // reduce
        // @formatter:off
        int i = Stream.of("1", "2", "3")
                    .parallel()
                    .reduce(0,
                            (total, s) -> {
                                System.out.println("parallel: accumulator: " + s);
                                return Integer.sum(total, Integer.parseInt(s));
                            },
                            (total, int2) -> {
                                System.out.println("parallel: combiner: " + int2);
                                return Integer.sum(total, int2);
                            });
        // @formatter:on

        System.out.println("reduce: " + i);

        // reduce
        // @formatter:off
        i = Stream.of("1", "2", "3")
                    // .parallel()
                    .reduce(0,
                            (total, s) -> {
                                System.out.println("non-parallel: accumulator: " + s);
                                return Integer.sum(total, Integer.parseInt(s));
                            },
                            (total, int2) -> {
                                System.out.println("non-parallel: combiner: " + int2);
                                return Integer.sum(total, int2);
                            });
        // @formatter:on

        System.out.println("reduce: " + i);

        log.info("End Test.");
    }

    private static int getCountEmptyStringUsingJava7(List<String> strings) {
        int count = 0;

        for (String str : strings) {

            if (str != null && str.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings) {
        int count = 0;

        for (String str : strings) {

            if (str != null && str.length() == 3) {
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings) {
        List<String> filteredList = new ArrayList<>();

        for (String str : strings) {

            if (str != null && !str.isEmpty()) {
                filteredList.add(str);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();

        for (String str : strings) {

            if (str != null && !str.isEmpty()) {
                sb.append(str);
                sb.append(separator);
            }
        }
        String mergedString = sb.toString();
        return mergedString.substring(0, mergedString.length() - 2);
    }

    private static List<Integer> getSquares(List<Integer> numbers) {
        List<Integer> squaresList = new ArrayList<>();

        for (Integer num : numbers) {
            Integer square = num * num;
            squaresList.add(square);
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers) {
        int max = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {

            Integer num = numbers.get(i);

            if (num.intValue() > max) {
                max = num.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers) {
        int min = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            Integer num = numbers.get(i);

            if (num.intValue() < min) {
                min = num.intValue();
            }
        }
        return min;
    }

    private static int getSum(List<Integer> numbers) {
        int sum = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            sum += numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers) {
        return getSum(numbers) / numbers.size();
    }
}
