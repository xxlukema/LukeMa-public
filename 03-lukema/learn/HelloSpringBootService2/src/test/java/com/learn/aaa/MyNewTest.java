package com.learn.aaa;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MyNewTest {

    String str = "Hello World.";

    @Disabled
    @Test
    public void testReserse() {

        log.debug("Test start: {}", () -> str);

        for (int i = str.length() - 1; i >= 0; i--) {

            System.out.print(str.charAt(i));
        }

    }

    @Test
    public void testFindUsingLoop1() {
        String[] strs = { "test", "123test", "tes", "test123" };
        log.info(findUsingLoop("test", Arrays.asList(strs)));
    }

    @Test
    public void testFindUsingLoop2() {
        String[] strs = { "test", "123test", "tes", "test123" };
        log.info(findUsingLoop("wrong", Arrays.asList(strs)));
    }

    private List<String> findUsingLoop(String search, List<String> list) {
        return list.stream().filter(item -> item.contains(search)).collect(Collectors.toList());
    }
    

    /*
    Example1:
        Search = "test"
        list=[“test”,”123test”,”tes”,”test123”]
        Result = [“test”,”123test”,”test123”]
    Example2:
    Search = “wrong”
        list=[“test”,”123test”,”tes”,”test123”]
        Result = []
    */

}
