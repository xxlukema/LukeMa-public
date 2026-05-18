package com.learn.cucumber.util;


import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class CucumberHook {

    /**
     * First hook to run.
     * Runs before @BeforeStep
     */
    @Before
    public void beforeEachSenario() {
        log.debug(() -> "beforeEachSenario()");
    }

    @Before("@MyCucumberTag")
    public void beforeMyCucumberTag() {
        log.debug(() -> "beforeMyCucumberTag()");
    }

    @BeforeStep
    public void beforeEachStep() {
        log.debug(() -> "beforeEachStep()");
    }

    /**
     * Last hook to run.
     * Runs after @AfterStep
     */
    @After
    public void afterEachSenario() {
        log.debug(() -> "afterEachSenario()");
    }

    @AfterStep
    public void afterEachStep() {
        log.debug(() -> "afterEachStep()");
    }
}
