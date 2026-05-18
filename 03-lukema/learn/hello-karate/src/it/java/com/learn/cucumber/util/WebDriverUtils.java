package com.learn.cucumber.util;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebDriverUtils {

    public static WebDriver newWebDriver() {
        log.debug("Inside {}", () -> "newWebDriver()");

        // Windows Environment Variable:
        // Web_Driver_Dir=D:\01-LukeTools\WebDrivers
        // String baseDir = System.getenv("Web_Driver_Dir");
        //
        // Or
        // maven project base dir, where the pom.xml sits.
        // String baseDir = System.getProperty("user.dir");
        // String baseDir = "D:/01-LukeTools/WebDrivers";
        //
        // System.setProperty("webdriver.gecko.driver", baseDir + "/geckodriver.exe");
        // this.webDriver = new FirefoxDriver();

        String baseDir = System.getenv("Web_Driver_Dir");
        String driver = baseDir + "/chromedriver.exe";

        log.debug("driver: {}", () -> driver);

        System.setProperty("webdriver.chrome.driver", driver);

        /**
         * https://stackoverflow.com/questions/16149610/how-to-override-default-set-of-chrome-command-line-switches-in-selenium
         *
         * List of Chromium Command Line Switches:
         * https://peter.sh/experiments/chromium-command-line-switches/#no-sandbox
         */
        /**
         *
         By default, chrome will be run with this command line:
        
            "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
            --disable-hang-monitor
            --disable-prompt-on-repost
            --dom-automation
            --full-memory-crash-report
            --no-default-browser-check
            --no-first-run
            --disable-background-networking
            --disable-sync
            --disable-translate
            --disable-web-resources
            --safebrowsing-disable-auto-update
            --safebrowsing-disable-download-protection
            --disable-client-side-phishing-detection
            --disable-component-update
            --disable-default-apps
            --enable-logging
            --log-level=1
            --ignore-certificate-errors
            --no-default-browser-check
            --test-type=ui
            --user-data-dir="C:\Users\nik\AppData\Local\Temp\scoped_dir1972_4232"
            --testing-channel=ChromeTestingInterface:1972.1
            --noerrdialogs
            --metrics-recording-only
            --enable-logging
            --disable-zero-browsers-open-for-tests
            --allow-file-access
            --allow-file-access-from-files about:blank
        
            I need to override(remove) all commands --disable-*, since there are no equivalent command --enable-*.
        
            In the end, I want to run browser with this command line:
        
            "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
            --dom-automation
            --full-memory-crash-report
            --no-first-run
            --safebrowsing-disable-auto-update
            --safebrowsing-disable-download-protection
            --enable-logging
            --log-level=1
            --ignore-certificate-errors
            --test-type=ui
            --user-data-dir="C:\Users\nik\AppData\Local\Temp\scoped_dir1972_4232"
            --testing-channel=ChromeTestingInterface:1972.1
            --noerrdialogs
            --metrics-recording-only
            --enable-logging
            --allow-file-access
            --allow-file-access-from-files about:blank
         *
         */
        ChromeOptions options = new ChromeOptions();
        // Add the WebDriver proxy capability.
        // Proxy proxy = new Proxy();
        // proxy.setHttpProxy("bproxy.fhlmc.com:8080");
        // options.setCapability("proxy", proxy);
        // options.addArguments("headless"); // !This may cause browser not display!
        // options.addArguments("start-maximized");

        options.addArguments("no-sandbox");
        options.addArguments("disable-infobars");
        options.addArguments("disable-extensions");
        options.addArguments("disable-gpu");
        options.addArguments("js-flags=expose-gc");
        options.addArguments("enable-precise-memory-info");
        options.addArguments("disable-popup-bloacking");
        options.addArguments("disable-default-apps"); // default
        options.addArguments("test-type=browser"); // default
        options.addArguments("disable-dev-shm-usage");
        options.addArguments("window-size=1400,750");
        options.addArguments("disable-setuid-sandbox");
        options.addArguments("remote-debugging-port=9222");
        options.addArguments("disable-translate");
        options.addArguments("disable-notifications");
        options.addArguments("disable-session-crashed-bubble");
        options.addArguments("disable-restore-session-state");
        options.addArguments("incognito");
        options.addArguments("noerrdialogs");
        options.addArguments("enable-automation");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        /**
         * protractor:
         *
         * capabilities: {
         *   'browserName': 'chrome',
         *   "goog:chromeOptions": {
         *       "excludeSwitches": [ "enable-automation" ]
         *    }
         * }
         *
         * */

        WebDriver webDriver = new ChromeDriver(options);

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        return webDriver;
    }

}
