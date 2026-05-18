package com.learn.initialize;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class InitTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {

        LOG.info("Begin Test");

        Person person = new Person() {

            /**
             * Non-static initialization block
             * 
             * Called after constructor.
             * 
             * After create a new instance, call its method to init it.
             */
            {
                LOG.info("Non-static initialization block 33333 called. --- Called after constructor.");
                this.setName("Luke Ma");
            }

        };

        LOG.info("Person.name = " + person.getName());

        LOG.info("End Test.");

    }

}
