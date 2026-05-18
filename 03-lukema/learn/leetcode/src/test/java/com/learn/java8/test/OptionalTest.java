package com.learn.java8.test;


import java.util.Optional;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class OptionalTest {

    @Test
    void testOptional() {
        String[] str = new String[10];
        str[5] = "JAVA OPTIONAL CLASS EXAMPLE"; // Setting value for 5th index
        // It returns an empty instance of Optional class
        Optional<String> empty = Optional.empty();
        log.debug(empty);
        // It returns a non-empty Optional
        Optional<String> value = Optional.of(str[5]);
        // If value is present, it returns an Optional otherwise returns an empty Optional
        log.debug("Filtered value: " + value.filter((s) -> s.equals("Abc")));
        log.debug("Filtered value: " + value.filter((s) -> s.equals("JAVA OPTIONAL CLASS EXAMPLE")));
        // It returns value of an Optional. if value is not present, it throws an NoSuchElementException
        log.debug("Getting value: " + value.get());
        // It returns hashCode of the value
        log.debug("Getting hashCode: " + value.hashCode());
        // It returns true if value is present, otherwise false
        log.debug("Is value present: " + value.isPresent());
        // It returns non-empty Optional if value is present, otherwise returns an empty Optional
        log.debug("Nullable Optional: " + Optional.ofNullable(str[5]));
        // It returns value if available, otherwise returns specified value,
        log.debug("orElse: " + value.orElse("Value is not present"));
        log.debug("orElse: " + empty.orElse("Value is not present"));
        value.ifPresent(System.out::println); // printing value by using method reference
    }

}
