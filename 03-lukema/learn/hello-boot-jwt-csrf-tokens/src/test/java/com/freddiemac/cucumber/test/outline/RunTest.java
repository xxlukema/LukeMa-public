package com.freddiemac.cucumber.test.outline;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
// @Cucumber.Options(format = { "pretty", "html:target/cucumber" })
// @formatter:off
@CucumberOptions(monochrome = true, 
                 plugin = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" },
                 stepNotifications = true)
// @formatter:on
// @CucumberOptions(plugin = { "pretty", "html:target/cucumber" })
public class RunTest {
}
