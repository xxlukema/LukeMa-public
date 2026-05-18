
# `hello-cucumber-gradle`

## [Cucumber-Java Skeleton](https://github.com/cucumber/cucumber-java-skeleton)

Open the link to see how to run a subset of Features or Scenarios

Specify a particular scenario by line

    -Dcucumber.features="classpath:skeleton/belly.feature:4"

This works because Maven puts ./src/test/resources on your classpath. You can also specify files to run by filesystem path:

    -Dcucumber.features="src/test/resources/skeleton/belly.feature:4"

In case you have many feature files or scenarios to run against, separate them with commas ,

    -Dcucumber.features="src/test/resources/skeleton/belly.feature:4, src/test/resources/skeleton/stomach.feature"

You can also specify what to run by tag:

    -Dcucumber.filter.tags="@bar"

Running only the scenarios that failed in the previous run

    -Dcucumber.features="@target/rerun.txt"

This works as long as you have the rerun formatter enabled.
Specify a different formatter:

For example a JUnit formatter:

    -Dcucumber.plugin="junit:target/cucumber-junit-report.xml"

    
    