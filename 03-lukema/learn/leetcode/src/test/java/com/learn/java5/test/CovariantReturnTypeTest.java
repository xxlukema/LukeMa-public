package com.learn.java5.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


public class CovariantReturnTypeTest {

    @Test
    public void testCovariantReturnType() {
        A1 a1 = new A1();

        a1.foo().print();

        A2 a2 = new A2();

        a2.foo().print();

        A3 a3 = new A3();

        a3.foo().print();
    }

}


@Log4j2
class A1 {
    A1 foo() {
        return this;
    }

    void print() {
        log.debug("Inside the class A1");
    }
}


/**
 * A2 is the child class of A1
 */
@Log4j2
class A2
    extends A1 {
    @Override
    A2 foo() {
        return this;
    }

    void print() {
        log.debug("Inside the class A2");
    }
}


/**
 * A3 is the child class of A2
 */
@Log4j2
class A3
    extends A2 {
    @Override
    A3 foo() {
        return this;
    }

    @Override
    void print() {
        log.debug("Inside the class A3");
    }
}

/*
Output:

Inside the class A1
Inside the class A2
Inside the class A3
*/
