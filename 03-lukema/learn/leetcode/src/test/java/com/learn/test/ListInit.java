package com.learn.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

/**
 * Init List
 * https://www.baeldung.com/java-init-list-one-line
 */
@Log4j2
@SuppressWarnings("unused")
public class ListInit {

  @Test
  public void givenArraysAsList_thenInitialiseList() {

    log.debug("{}", () -> "here");

    List<String> list = Arrays.asList("foo", "bar");

    assertTrue(list.contains("foo"));

    String[] array = { "foo", "bar" };
    List<String> list1 = Arrays.asList(array);

    List<String> list2 = Stream.of("foo", "bar")
        .collect(Collectors.toList());

    List<String> list3 = List.of("foo", "bar", "baz");
    Set<String> set = Set.of("foo", "bar", "baz");

    List<String> cities = new ArrayList<>() {
      {
        add("New York");
        add("Rio");
        add("Tokyo");
      }
    };
  }

}
