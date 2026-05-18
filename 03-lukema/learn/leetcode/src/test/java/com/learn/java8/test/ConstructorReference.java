package com.learn.java8.test;


import lombok.extern.log4j.Log4j2;


interface Messageable {
    Message getMessage(String msg);
}


@Log4j2
class Message {
    Message(String msg) {
        log.debug(() -> msg);
    }
}


public class ConstructorReference {
    public static void main(String[] args) {
        Messageable hello = Message::new;
        hello.getMessage("Hello");
    }

}
