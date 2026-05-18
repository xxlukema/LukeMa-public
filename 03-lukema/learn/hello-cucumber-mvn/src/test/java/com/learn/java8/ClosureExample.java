package com.learn.java8;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class ClosureExample {

    public static void main(String[] args) {

        log.info("Start");

        int a = 11;
        int b = 22;

        doProcess(a, item -> {
            log.info("b: {}. item: {}", () -> b, () -> item);
        });

        a = 100;

        /**
         * b cannot be change, because it is defined in closure and is effectively final.
         */
        // b = -1; 
        doProcess(a, item -> {
            log.info("b: {}. item: {}", () -> b, () -> item);
        });

        log.info("End");
    }

    public static void doProcess(int z, Process p) {
        p.process(z);
    }

}


@FunctionalInterface
interface Process {

    void process(int i);
}
