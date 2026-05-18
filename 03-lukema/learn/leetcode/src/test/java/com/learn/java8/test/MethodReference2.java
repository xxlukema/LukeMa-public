package com.learn.java8.test;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class MethodReference2 {

    public static void ThreadStatus() {
        log.debug("Thread is running...");
    }

    public static void main(String[] args) {
        Thread t2 = new Thread(MethodReference2::ThreadStatus);
        t2.start();
    }
}
