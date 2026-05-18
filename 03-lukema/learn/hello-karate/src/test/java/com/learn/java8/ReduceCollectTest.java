package com.learn.java8;


import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ReduceCollectTest {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Reduce: http://www.baeldung.com/java-8-streams
     */
    @Test
    public void runTestReduce()
        throws Exception {
        LOG.info("Begin Test");
        
        IntStream.range(1, 4).forEach(LOG::info);

        // 1. One Param 
        // @formatter:off
        OptionalInt reducedOneParams = IntStream.range(1, 4)
                .reduce((a, b) -> a + b);
        // @formatter:on
        LOG.info("reducedOneParams = " + reducedOneParams.getAsInt());

        // 2. Two Params
        // @formatter:off
        int reducedTwoParams = IntStream.range(1, 4)
                .reduce(10, (a, b) -> a + b);
        // @formatter:on
        LOG.info("reducedTwoParams = " + reducedTwoParams);

        // 3. Three Params. Combiner runs only in parallel streams.
        // @formatter:off
        int reducedParallel = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .parallelStream()    // .stream() // .parallel() 
                .reduce(10, 
                        (a, b) -> {
                            System.out.println("parallel: accumulator was called: a=" + a + " b=" + b);
                            return a + b;
                            },
                        (a, b) -> {
                            System.out.println("parallel: combiner was called: a=" + a + " b=" + b);
                            return a + b;
                        }
                    );
        // @formatter:on
        LOG.info("reducedParallel = " + reducedParallel);

        // 3. Three Params. Combiner does not run non-parallel streams.
        // @formatter:off
        int reducedNonParallel = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .stream() // .parallel() // parallelStream()
                .reduce(10, 
                        (a, b) -> {
                            System.out.println("non-parallel: accumulator was called: a=" + a + " b=" + b);
                            return a + b;
                            },
                        (a, b) -> {
                            System.out.println("non-parallel: combiner was called: a=" + a + " b=" + b);
                            return a + b;
                        }
                    );
        // @formatter:on
        LOG.info("reducedNonParallel = " + reducedNonParallel);

        LOG.info("End Test.");

    }

    /**
     * Collect: http://www.baeldung.com/java-8-streams
     */
    @Test
    public void runTestCollect()
        throws Exception {
        LOG.info("Begin Test");

        // @formatter:off
        List<Product> productList = Arrays.asList(
                new Product("potatoes", 23), 
                new Product("orange", 14), 
                new Product("lemon", 13), 
                new Product("bread", 23),
                new Product("sugar", 13)
           );
        
        /**
         * Collection implementations returned from the factory methods are highly optimized for the number of elements you put in. 
         * That's possible because they're immutable: adding items to these collections after creation results in 
         * an `UnsupportedOperationException`.
         */
        // productList.add(new Product("sugar2", 132));

        Supplier<Stream<Product>> prividerProducts = () -> productList.stream();
        
        // 1. 
        List<String> collectorCollection = prividerProducts.get()
                .map(Product::getName)
                .collect(Collectors.toList());
        collectorCollection.forEach(System.out::println);

        // 2.
        String listToString = prividerProducts.get()
                .map(Product::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        LOG.info("listToString: " + listToString);
        
        // 3.
        double averagePrice = prividerProducts.get()
                .collect(Collectors.averagingInt(Product::getPrice));
        LOG.info("averagePrice: " + averagePrice);
        
        // 4.
        int summingPrice = prividerProducts.get()
                .collect(Collectors.summingInt(Product::getPrice));
        LOG.info("summingPrice: " + summingPrice);
        
        // 5.
        IntSummaryStatistics statistics = prividerProducts.get()
                .collect(Collectors.summarizingInt(Product::getPrice));
        LOG.info("statistics: " + statistics);
        
        // 6.
        Map<Integer, List<Product>> groupingByMapOfLists = prividerProducts.get()
                .collect(Collectors.groupingBy(Product::getPrice));
        LOG.info("groupingByMapOfLists: ");
        groupingByMapOfLists.forEach((key, value) -> {System.out.println(key + ": " + value);});
        
        // 7. 
        Map<Boolean, List<Product>> partitioningByMap = prividerProducts.get()
                .collect(Collectors.partitioningBy(element -> element.getPrice() > 15));
        LOG.info("partitioningByMap: ");
        partitioningByMap.forEach((key, value) -> {System.out.println(key + ": " + value);});
        
        // 8. 
        Set<Product> unmodifiableSet = prividerProducts.get()
                .collect(Collectors.collectingAndThen(Collectors.toSet(), 
                                                      Collections::unmodifiableSet)
                        );
        LOG.info("unmodifiableSet: ");
        unmodifiableSet.forEach(System.out::println);
        
        // 9. Custom collector
        Collector<Product, ?, LinkedList<Product>> toLinkedList = Collector.of(
                                                                                 LinkedList::new, 
                                                                                 LinkedList::add, 
                                                                                 (first, second) -> { 
                                                                                     first.addAll(second); 
                                                                                     return first; 
                                                                                 });
               
        List<Product> customCollect = prividerProducts.get()
                .collect(toLinkedList);
        LOG.info("customCollect: ");
        customCollect.forEach(System.out::println);
              
        // @formatter:on
        
        LOG.info("End Test.");
    }

}
