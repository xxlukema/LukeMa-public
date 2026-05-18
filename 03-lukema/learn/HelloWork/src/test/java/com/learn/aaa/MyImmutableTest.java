package com.learn.aaa;


import java.util.Arrays;
import java.util.stream.Collectors;


public class MyImmutableTest {

    Integer[] ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    
    
    public void testStream() {
        
        Arrays.asList(ints).parallelStream().filter(item -> (item % 2 == 0)).collect(Collectors.toList());
        
        
    }

}
