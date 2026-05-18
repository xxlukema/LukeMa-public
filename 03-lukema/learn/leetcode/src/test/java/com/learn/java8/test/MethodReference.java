package com.learn.java8.test;


import lombok.extern.log4j.Log4j2;


interface Sayable2 {
    void say();
}


@Log4j2
public class MethodReference {
    public static void saySomething() {
        log.debug("Hello, this is static method.");
    }

    public static void main(String[] args) {
        // Referring static method
        Sayable sayable = MethodReference::saySomething;
        // Calling interface method
        sayable.say();
    }
}
