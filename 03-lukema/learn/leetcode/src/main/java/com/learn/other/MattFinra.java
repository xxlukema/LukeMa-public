package com.learn.other;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class MattFinra {

    public static void main(String[] args) {

        MattFinra mattFinraTest = new MattFinra();

        mattFinraTest.mattTest();

    }

    public void mattTest() {
        log.debug("Test Start");
        log.debug("Test End");
    }
}
