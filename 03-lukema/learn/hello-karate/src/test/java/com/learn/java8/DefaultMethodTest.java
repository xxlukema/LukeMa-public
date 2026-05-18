package com.learn.java8;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class DefaultMethodTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testDefaultMethod() {

        LOG.info("Begin Test");

        Vehicle vehicle = new Car();
        vehicle.print();

        LOG.info("End Test");
    }

}


interface Vehicle {

    public static final Logger LOG = LogManager.getLogger();

    default void print() {
        LOG.info("I am a vehicle!");
    }

    static void blowHorn() {
        LOG.info("Blowing horn!!!");
    }
}


interface FourWheeler {

    public static final Logger LOG = LogManager.getLogger();

    default void print() {
        LOG.info("I am a four wheeler!");
    }
}


class Car
    implements Vehicle, FourWheeler {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Must override because both super interfaces have the same default function name and signatures.
     */
    @Override
    public void print() {

        Vehicle.super.print();
        FourWheeler.super.print();
        Vehicle.blowHorn();
        LOG.info("I am a car!");
    }
}
