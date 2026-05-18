package com.learn.java8;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class RemoveDuplicates {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Luke", "Ma"));
        persons.add(new Person("Hong", "Lin"));
        persons.add(new Person("Luke", "Ma"));
        persons.add(new Person("Hong", "Lin"));
        persons.add(new Person("Luke", "Ma"));

        log.info("persons: {}", () -> persons);

        RemoveDuplicates removeDuplicate = new RemoveDuplicates();
        removeDuplicate.removeDuplicateComparitor(new ArrayList<>(persons));
        removeDuplicate.removeDuplicateDistinctByKeyClass(new ArrayList<>(persons));
        removeDuplicate.removeDuplicateSeenSet(new ArrayList<>(persons));
        // removeDuplicate.removeDuplicate3(new ArrayList<>(persons));
    }

    /**
     * Comparator
     *
     * Problem: The order will be changed due to call of "sorted()"
     */
    public void removeDuplicateComparitor(List<Person> persons) {
        Comparator<Person> comparator = (Person a, Person b) -> {
            return a.lName.equals(b.lName) ? a.fName.compareTo(b.fName)
                    : a.lName.compareTo(b.lName);
        };

        persons = persons.stream().sorted(comparator).distinct().collect(Collectors.toList());
        log.info("persons: {}", persons);

        Assertions.assertEquals(persons.size(), 2);
        Assertions.assertEquals("Lin", persons.get(0).lName);

        log.info(() -> "Pass");
    }

    /**
     * User seen Set
     *
     * Good: Intuitive.
     */
    public void removeDuplicateSeenSet(List<Person> persons) {

        Set<String> seen = ConcurrentHashMap.newKeySet();
        List<Person> persons2 = new ArrayList<>();
        persons.forEach(person -> {
            String key = person.fName + " " + person.lName;
            if (!seen.contains(key)) {
                seen.add(key);
                persons2.add(person);
            }
        });

        Assertions.assertEquals(persons2.size(), 2);

        log.info(() -> "Pass");
    }

    /**
     * Re-usable "Predicate distinctByKeyClass()"
     */
    public static <T> Predicate<T> distinctByKeyClass(final Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Using CustomKey and "Predicate distinctByKeyClass"
     */
    public void removeDuplicateDistinctByKeyClass(List<Person> persons) {

        record CustomKey(String firstName, String lastName) {
            public CustomKey(final Person p) {
                this(p.fName, p.lName);
            }
        }

        persons = persons.stream().filter(distinctByKeyClass(CustomKey::new)).collect(Collectors.toList());

        log.info("persons: {}", persons);

        Assertions.assertEquals(persons.size(), 2);

        log.info(() -> "Pass");
    }

    /**
     * Re-usable "Predicate distinctByKeyClass()"
     */
    /*
    public static <T> Predicate<T> distinctByKeys(final Function<? super T, ?> keyExtractors[]) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
    */
    /*
    public void removeDuplicate3(List<Person> persons) {
        persons = persons.stream()
                .filter(
                        distinctByKeys(
                                Person::fName,
                                Person::lName))
                .collect(Collectors.toList());

        log.info("persons: {}", persons);

        Assertions.assertEquals(persons.size(), 2);
    }
    */

    /*
    public void removeDuplicate1(List<Person> persons);
    public void removeDuplicate1(List<Person> persons);
    */

}
