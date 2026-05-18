
# hello-cucumber

Quotes from <https://cucumber.io/docs/cucumber/api/> :

- 1. `Cucumber` is based on `JUnit 4` using `cucumber-junit`.
- 2. If you are using `JUnit 5`, remember to include `junit-vintage-engine` dependency, as well.

[Demo Login Page](https://example.testproject.io/web/)

    https://example.testproject.io/web/

## [10 Minutes Tutorial](https://cucumber.io/docs/guides/10-minute-tutorial/)

In Cucumber, an example is called a scenario. Scenarios are defined in .feature files, which are stored in the src/test/resources/hellocucumber directory (or a subdirectory).

## To run a feature file:

- 1. Open the feature file.
- 2. Click in the feature editor --> Run As --> 1 Cucumber Feature
- 3. If Right click the feature filename from Project Explorer, it will not trigger the feature to run.

## Integration Test

- maven-failsafe-plugin is integration
- maven-sunfire-plugin is for junit
- `it` is the standard folder name for integration

    java io.cucumber.core.cli.Main --help
    mvn test -Dcucumber.filter.tags="@HelloCucumer"
    mvn clean verify -Dcucumber.filter.tags="@HelloCucumer"
    
    mvn exec:java                                  \
    -Dexec.classpathScope=test                 \
    -Dexec.mainClass=io.cucumber.core.cli.Main \
    -Dexec.args="/path/to/your/feature/files --glue hellocucumber --glue anotherpackage"
    
    mvn test
    mvn verify -P it
    mvn failsafe:verify -P it
    mvn verify -Dcucumber.features="src/it/resources/com/learn/cucumber/test/is_it_friday_yet.feature"
    
    # mvn test -Dcucumber.featuress="src/it/resources/com/learn/selenium/test/google-search.feature"
    # mvn failsafe:verify -P it -Dcucumber.options="--tags @GoogleSearch"
    # mvn verify -Dit.test=com.learn.selenium.test.GoogleSearchRunCucumberTest
    
    # mvn verify -DskipTests
    # mvn test -Dcucumber.filter.tags="@IsItFriday"
    # mvn verify -Dcucumber.filter.tags="@IsItFriday"
    
    # This is the only working command
    mvn verify -P it -Dcucumber.filter.tags="@IsItFridayYet"
    
    # 'and' is not supported:
    # mvn verify -P it -Dcucumber.filter.tags="@IsItFriday and @GoogleSearch"
    
## To run a single test

            <plugin>
                <!-- maven-surefire-plugin is for junit -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                    <includes>com.learn.cucumber.test.RunCucumberTest</includes>
                </configuration>
            </plugin>
    
    
    
## WebDrivers

### Chrome Driver

    // Windows Environment Variable:
    // Web_Driver_Dir=D:\01-LukeTools\WebDrivers
    String baseDir = System.getenv("Web_Driver_Dir");
    System.setProperty("webdriver.chrome.driver", baseDir + "/chromedriver.exe");
    this.webDriver = new ChromeDriver();

### Firefox Driver (Exception with `this.webDriver.quit();`)

    // Do NOT use. Exception with this.webDriver.quit();
    // maven project base dir, where the pom.xml sits.
    // String baseDir = System.getProperty("user.dir");
    // System.setProperty("webdriver.gecko.driver", baseDir + "/geckodriver.exe");
    
    // Or
    // Windows Environment Variable:
    // Web_Driver_Dir=D:\01-LukeTools\WebDrivers
    String baseDir = System.getenv("Web_Driver_Dir");
    System.setProperty("webdriver.chrome.driver", baseDir + "/geckodriver.exe");
    this.webDriver = new FirefoxDriver();

## `@Before` Hook: Think twice before you use `@Before`

Whatever happens in a Before hook is invisible to people who only read the features. You should consider using a background as a more explicit alternative, especially if the setup should be readable by non-technical people. Only use a Before hook for low-level logic such as starting a browser or deleting data from a database.







