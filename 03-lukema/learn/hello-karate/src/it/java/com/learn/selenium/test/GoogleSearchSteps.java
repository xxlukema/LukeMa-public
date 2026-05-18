package com.learn.selenium.test;


import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.learn.cucumber.util.WebDriverUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class GoogleSearchSteps {

    private GoogleSearchPageObjectModel googleSearchPage = null;

    @Given("Browser is open")
    public void browser_is_open() {
        log.debug("Browser is open");

        WebDriver webDriver = WebDriverUtils.newWebDriver();
        this.googleSearchPage = new GoogleSearchPageObjectModel(webDriver);
    }

    @Given("user is on google sarch page")
    public void user_is_on_google_sarch_page() {
        log.debug("user os on google sarch page");

        /**
         * https://example.testproject.io/web/
         */
        this.googleSearchPage.navigateTo("https://google.com");
    }

    @When("user enters a text in search box")
    public void user_enters_a_text_in_search_box()
        throws InterruptedException {
        log.debug("user enters a text in search box");

        this.googleSearchPage.sendInputText("Automation Step by Step");

        Thread.sleep(2_000);
    }

    @When("hits enter")
    public void hits_enter()
        throws InterruptedException {
        log.debug("hits enter");

        this.googleSearchPage.sendKeys(Keys.ENTER);
    }

    @Then("user is navigated to serach results")
    public void user_is_navigated_to_serach_results()
        throws InterruptedException {
        log.debug("user is navigated to serach results");

        boolean containsText = this.googleSearchPage.expectText("Online Courses");

        Assert.assertTrue("Contains search result", containsText);

        Thread.sleep(2_000);

        this.googleSearchPage.closeAndQuit();

    }

}
