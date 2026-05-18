# `application.yml`

## `-D` vs `--`

1. `-D` --- To pass parameter to `mvn`
2. `--` -- To override `application.yml` property values

## Validate `application.yml` requires `Spring Boot Tools` and `Spring Boot Dashboard`

### 1/2. Dependency

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>

    # Or

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-autoconfigure</artifactId>
      <optional>true</optional>
    </dependency>

### 2/2. Plugins requires `Spring Boot Tools` and `Spring Boot Dashboard`

Enable `Spring Boot Tools` and `Spring Boot Dashboard` to validate `application.yml`

## `application.yml` properties override with `application-dev.yml`

    # `application.yml`
    prop1: Property One             <=== This will remain the same
    prop2: Property Two             <=== This will be overridden

    # `application-dev.yml`
    prop2: Property 2

    # cmd for mvn
    mvn spring-boot:run -Dspring-boot.run.profiles=no-security,dev

    # cmd for Dockerfile
     java -jar filename-1.0.0.war -Dspring.profiles.active=no-security,dev

    # application.yml
    spring:
      profiles:
        active: 
          - no-security
          - dev

## `@Profile("no-security")`, `@Profile({"Tomcat & Linux"})`

    @Profile("no-security")
    @Configuration
    public class AppConfigNoSecurity {...}
    
    @Profile({"Tomcat & Linux"})
    @Configuration
    public class AppConfigMongodbLinux {...}

    @Log4j2
    // @ExtendWith(SpringExtension.class)
    // @ExtendWith(MockitoExtension.class)  /** to use `@InjectMocks` and `@Mock` */
    @WebMvcTest(value = GreetingController.class)
    @Import({ GreetingController.class })
    @ContextConfiguration(classes = BootMvcTestConfig.class)
    @ActiveProfiles({"no-security", "dev", "jwt"})
    @WithMockUser(username = "user", password = "user")
    public class GreetingControllerWebMvcTest {..}
    
    @Log4j2
    @ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
    // @SpringBootTest(classes = HelloSpringBootMainApplication.class)
    @SpringBootTest
     @ActiveProfiles("no-security")
    public class SpringSecurityUserDetailsServiceTest {...}

## `spring.profiles.active` vs `spring.profiles.include`

1. `spring.profiles.active` --- To specify active profiles. Can be overriden in command line
2. `spring.profiles.include` --- To specify profiles to include. Cannot be overriden in command line
