package com.learn.java8.test;


import lombok.extern.log4j.Log4j2;


interface Sayable {
    void say();
}


@Log4j2
public class InstanceMethodReference {

    public void saySomething() {
        log.debug("Hello, this is non-static method.");
    }

    public static void main(String[] args) {
        InstanceMethodReference methodReference = new InstanceMethodReference(); // Creating object
        // Referring non-static method using reference
        Sayable2 sayable = methodReference::saySomething;
        // Calling interface method
        sayable.say();
        // Referring non-static method using anonymous object
        Sayable2 sayable2 = new InstanceMethodReference()::saySomething; // You can use anonymous object also
        // Calling interface method
        sayable2.say();
    }

}
