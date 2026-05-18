# Externalized Configuration

    clr;

## `-D` vs `--`

1. `-D` --- To pass parameter to `mvn`
2. `--` -- To override `application.yml` property values

## Command

## Run maven with default logger

    # mvn
    clr; mvn spring-boot:run -Dspring-boot.run.profiles=dev,jwt,no-security
    # Or
    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt"

    # java
    clr; java -jar target/xxx-1.0.1.war --spring.profiles.active=dev,jwt,no-security

## Run maven with `logback-debug.xml`

    # mvn
    clr; mvn spring-boot:run -Dspring.profiles.active=dev,jwt  -Dspring-boot.run.arguments="--logging.config=classpath:logback-debug.xml"
    # Or
    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt --logging.config=classpath:logback-debug.xml"

    # Absolute path
    clr; mvn spring-boot:run -Dspring.profiles.active=dev,jwt  -Dspring-boot.run.arguments="--logging.config=C:/dev/config/logback-debug.xml"
    # Or
    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt --logging.config=C:/dev/config/logback-debug.xml"

    # java
    clr; java -jar target/xxx-1.0.1.war --spring.profiles.active=dev,jwt --logging.config=classpath:logback-debug.xml

    # (mvn test using logback)
    clr; mvn test -Dlogback.configurationFile=C:/dev/config/logback-debug.xml

    # (mvn test using log4j2)
    clr; mvn test -Dlogback.configurationFile=C:/dev/config/log4j2.xml
    ##
    ## Extra steps to run `mvn test` using external `log4j2.xml`
    ##
    ## Step 1. Exlude `spring-boot-starter-logging` from `boot-starter`s from `pom.xml`
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <exclusions>
        <exclusion>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    ##
    ## Step 2. Include `spring-boot-starter-log4j2` in `pom.xml`:
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>

## Override property file

### 1. Specify dir (dir must end with `/`)

    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt --logging.config=C:/dev/config/logback-debug.xml --spring.config.location=C:/dev/config/"

    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt --logging.config=C:/dev/config/logback-debug.xml --spring.config.location=C:/dev/config/ --spring.config.additional-location=C:/dev/config2/"

### 2. Specify list of files (classpath files or absolute path files: `classpath:application.yml,/absplute_path/application-dev.yml`)

    clr; mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev,jwt --logging.config=C:/dev/config/logback-debug.xml --spring.config.location=C:/dev/config/application.yml,C:/dev/config/application-dev.yml"

### 3. `spring.config.location` vs `spring.config.additional-location`

1. `spring.config.location` --- Specifies property files location.
2. `spring.config.additional-location` --- Specifies additional property files location in addition to `spring.config.location`

## `spring.profiles.active` vs `spring.profiles.include`

1. `spring.profiles.active` --- To specify active profiles. Can be overriden in command line
2. `spring.profiles.include` --- To specify profiles to include. Cannot be overriden in command line
