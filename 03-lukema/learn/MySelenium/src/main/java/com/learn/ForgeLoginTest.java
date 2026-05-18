package com.learn;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ForgeLoginTest {

	private static final Logger LOG = LogManager.getLogger();

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {

		System.setProperty("webdriver.gecko.driver", "D:/02-LukeTools/geckodriver-v0.15.0-win64/geckodriver.exe");

		driver = new FirefoxDriver();
		// driver.manage().window().maximize();
		baseUrl = "https://forge.collab.net";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testForgeLogin() throws Exception {
		LOG.info("Begin Test.");

		driver.get(baseUrl + "/sf/sfmain/do/home");

		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("xma");
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys("Galo_n4@1");

		driver.findElement(By.cssSelector("strong")).click();

		// LOG.info(driver.getPageSource());

		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		LOG.info("sessionId = " + sessionId);

		Cookie cookie = driver.manage().getCookieNamed("JSESSIONID");
		LOG.info("JSESSIONID = " + cookie.getValue());

		/*
		 * Cookie clientCookie = new Cookie.Builder("name",
		 * "value").domain(".mydomain.com") .expiresOn(new Date(2015, 10,
		 * 28)).isHttpOnly(true).isSecure(false).path("/mypath").build();
		 */

		Cookie clientCookie = new Cookie.Builder("JSESSIONID", cookie.getValue()).build();
		driver.manage().addCookie(clientCookie);

		//driver.manage().addCookie(cookie);
		
		driver.get(baseUrl + "/sf/go/artf289898");

		LOG.info(driver.getPageSource());

		LOG.info("End Test.");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	protected String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
