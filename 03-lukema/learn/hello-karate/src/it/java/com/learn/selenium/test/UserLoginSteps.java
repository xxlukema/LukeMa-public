package com.learn.selenium.test;


import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.learn.cucumber.util.WebDriverUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class UserLoginSteps {

    private String fullname;
    private UserLoginPageObjectModel userLoginPageObjectModel;

    @Given("I open browser with url")
    public void i_open_browser_with_url(io.cucumber.datatable.DataTable dataTable) {
        log.debug("Inside {}", () -> "i_open_browser_with_url");

        WebDriver webDriver = WebDriverUtils.newWebDriver();
        this.userLoginPageObjectModel = new UserLoginPageObjectModel(webDriver);

        String url = dataTable.asMaps().get(0).get("url");

        log.debug("web url: {}", () -> url);

        this.userLoginPageObjectModel.navigateTo(url);
    }

    @Given("I enter fullname and password")
    public void i_enter_fullname_and_password(io.cucumber.datatable.DataTable dataTable) {
        log.debug("Inside {}", () -> "i_enter_fullname_and_password");

        Map<String, String> map = dataTable.asMaps().get(0);

        fullname = map.get("fullname");
        String password = map.get("password");

        this.userLoginPageObjectModel.sendInputTextFullname(fullname);
        this.userLoginPageObjectModel.sendInputTextPassword(password);

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
        }
    }

    @When("I click login button")
    public void i_click_login_button() {
        log.debug("Inside {}", () -> "i_click_login_button");
        this.userLoginPageObjectModel.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
        }
    }

    @Then("I can verify my login")
    public void i_can_verify_my_login() {
        log.debug("Inside {}", () -> "i_can_verify_my_login");
        this.userLoginPageObjectModel.expectText("Hello");
        this.userLoginPageObjectModel.expectText(fullname);

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
        }

        this.userLoginPageObjectModel.closeAndQuit();
    }
}
