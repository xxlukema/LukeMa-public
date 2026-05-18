package com.learn.java8;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


public class GroupByTest {
    private static final Logger LOG = LogManager.getLogger();

    @Ignore
    @Test
    public void runTest1()
        throws Exception {
        LOG.info("Begin Test");

        //3 apple, 2 banana, others 1
        List<String> items = Arrays.asList("apple", "apple", "banana", "apple", "orange", "banana", "papaya");

        Map<String, Long> result = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LOG.info(result);

        LOG.info("End Test.");
    }

    @Test
    public void runTest2()
        throws Exception {
        LOG.info("Begin Test");

        // Group by the name + Count or Sum the Qty.
        //3 apple, 2 banana, others 1
        // @formatter:off
        Supplier<Stream<Item>> supplier = () -> Stream.of(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
            );
        
        // @formatter:on

        Map<String, Long> counting = supplier.get().collect(Collectors.groupingBy(Item::getName, Collectors.counting()));
        System.out.println("Count group by name: " + counting);

        Map<String, Integer> sum = supplier.get().collect(Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));
        System.out.println("Qty group by name: " + sum);

        Supplier<Item> itemSupplier = () -> new Item("aa", 1, new BigDecimal("0.1"));
        System.out.println("Suplplier: " + itemSupplier.get());

        Consumer<Item> itemConsumer = (System.out::println);
        itemConsumer.accept(itemSupplier.get());

        Map<BigDecimal, List<Item>> map = supplier.get().collect(Collectors.groupingBy(Item::getPrice));
        System.out.println("Group by price: " + map);

        Map<BigDecimal, Set<String>> map2 = supplier.get().collect(Collectors.groupingBy(Item::getPrice, Collectors.mapping(Item::getName, Collectors.toSet())));
        System.out.println("Name sets group by price: " + map2);

        List<Item> list = supplier.get().sorted(Comparator.comparing(Item::getName).reversed()).collect(Collectors.toList());
        System.out.println("Sorted list by name: " + list);

        List<String> list2 = supplier.get().sorted(Comparator.comparing(Item::getName).reversed()).collect(Collectors.mapping(Item::getName, Collectors.toList()));
        System.out.println("Reverse sorted by name: " + list2);

        LOG.info("End Test.");

    }
}
