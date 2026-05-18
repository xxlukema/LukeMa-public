package com.learn.selenium.test;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class UserLoginPageObjectModel {

    private WebDriver webDriver = null;

    private By input_text_fullname = By.id("fullname");
    private By input_text_password = By.id("password");
    // private By input_text_search = By.name("q");

    public UserLoginPageObjectModel(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void navigateTo(String url) {
        this.webDriver.navigate().to(url);

        log.info("Page title: {}", this.webDriver.getTitle());
        log.info("Page url: {}", this.webDriver.getCurrentUrl());
    }

    public void sendInputTextFullname(String username) {
        this.webDriver.findElement(input_text_fullname).sendKeys(username);
    }

    public void sendInputTextPassword(String password) {
        this.webDriver.findElement(input_text_password).sendKeys(password);
    }

    public void sendKeys(Keys keys) {
        this.webDriver.findElement(input_text_password).sendKeys(keys);
    }

    public boolean expectText(String resultText) {
        return this.webDriver.getPageSource().contains(resultText);
    }

    public void closeAndQuit() {
        try {
            // 1. close first.
            this.webDriver.close();
        } catch (Exception e) {
            log.error("Unable to close connection", e);
        }

        try {
            // 2. then quit.
            this.webDriver.quit();
        } catch (Exception e) {
            log.error("Unable to quit browser", e);
        }
    }

}
