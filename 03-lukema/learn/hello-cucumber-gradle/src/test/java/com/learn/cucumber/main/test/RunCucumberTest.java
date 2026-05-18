package com.learn.cucumber.main.test;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


/**
 * cucumber junit integration:
 * 
 * https://cucumber.io/docs/cucumber/api/#junit
 * 
 * Cucumber is based on JUnit 4. If you’re using JUnit 5, remember to include junit-vintage-engine dependency, as well.
 */
//@formatter:off

/**
 * This must be run with JUnit4 runner:
 * 
 *    1. Right click on this class (or on blank area of this class editor) --> Run Configurations... --> Test Runner: (Select JUnit4) --> Apply --> Run.
 *    2. Shortcut to run JUnit: After first run of JUnit, subsequent test can be run with shortcut: Shift + Alt + X --> T
 *    3. Sometimes, it is needed to delete previous run configuration:
 *       Right click on this class (or on blank area of this class editor) --> Run Configurations... --> Delete the JUnit configuration from left panel.
 * 
 * @RunWith(Cucumber.class) is for JUnit4.
 * 
 * @Cucumber is for JUnit5. But it does not have an @CucumberOptions equivalent. 
 */
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = false,
                 // tags = {"@foo", "not @bar"},
                 features = { "classpath:com/learn/cucumber/step/test" },
                 // features = { "src/test/resources/com/learn/cucumber/step/test" },
                 glue = "com.learn.cucumber.step.test",
                 plugin = { "pretty", "html:build/cucumber-html-report.htm", "json:build/cucumber-json-report.json" })
//@formatter:on
public class RunCucumberTest {

}
