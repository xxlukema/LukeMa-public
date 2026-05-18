package com.learn.java8.test;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class InstanceMethodReference2 {

    public void printnMsg() {
        log.debug(() -> "Hello, this is instance method");
    }

    public static void main(String[] args) {
        Thread t2 = new Thread(new InstanceMethodReference2()::printnMsg);
        t2.start();
    }
}
