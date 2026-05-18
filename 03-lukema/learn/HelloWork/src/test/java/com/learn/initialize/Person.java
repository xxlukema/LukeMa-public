package com.learn.initialize;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Person {

    private static final Logger LOG = LogManager.getLogger();

    private String name;

    /**
     * Non-static initialization block
     * 
     * Called before constructor.
     */
    {
        LOG.info("Non-static initialization block 11111 called. --- Called before constructor.");
    }

    /**
     * Constructor
     * 
     * Called after Non-static initialization block.
     */
    public Person() {
        LOG.info("Constructor called.  --- after Non-static initialization block.");
    }

    /**
     * Non-static initialization block
     * 
     * Called before constructor.
     */
    {
        LOG.info("Non-static initialization block 22222 called. --- Called before constructor.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
