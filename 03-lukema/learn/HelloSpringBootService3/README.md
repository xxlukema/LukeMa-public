
# Spring Security

    # Run boot
    mvn spring-boot:run
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dfoo=FOOO000 -Dbar=BARRR000 -Xdebug"
    # Params separated by comma ','
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

## build info

    <https://localhost:8443/actuator/info>

## When Should You Use CSRF Protection?

Based on <https://docs.spring.io/spring-security/site/docs/5.2.x/reference/html/features.html#csrf-when>:

When should you use CSRF protection? Our recommendation is to use CSRF protection for any request that could be processed
by a browser by normal users. If you are only creating a service that is used by **non-browser clients (RESTful services)**,
you will likely want to **disable** CSRF protection.

## Static file server

- spring.web.resources.static-locations=file:/c:/D/03-lukema/LukeMa/03-lukema/learn/HelloSpringBootService3/shein-images
- spring.mvc.static-path-pattern=/content/**
- authorize.requestMatchers("/content/**").permitAll();  /** by pass securities */
- [static file]<https://localhost:8443/content/luke/java__cert.png>

## Migrate from boot 2.7.x to 3.x

1. Replace `javax.persistence` with `jakarta.persistence`
2. Replace `javax.validation` with `jakarta.validation`
3. In `src\main\resources\application.properties`, comment out `spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false`

## Swagger/OpenAPI Problems (my-properties-boot)

    # 1. As of 12/19/2022, boot 3 + Swagger does not start.
    # 2. As of 12/19/2022, boot 3 + OpenAPI works on Windows with vscode.
    # 3. As of 12/19/2022, boot 3 + OpenAPI throw the following error when open swagger-ui on docker:
    # Failed to load API definition.
    # Errors
    # Fetch error
    # response status is 500 /my-properties-boot/v3/api-docs

## Test

    # this pings PropertyLookupRestController (No Auth)
    curl -k -i -X GET "https://localhost:8443/ping"

    # this pings PingRestController (No Auth)
    curl -k -i -X GET "https://localhost:8443/rest/ping"

    # this pings SpringUserResource (No Auth) (non-existing makeshift user)
    curl -k -i -X GET "https://localhost:8443/spring/user/ping"

    # this pings SpringSecurityController (Auth)
    # ADMIN role has access
    curl -k -i --user admin:admin -X GET "https://localhost:8443/spring/security/ping"
    # or
    curl -k -i -X GET "https://admin:admin@localhost:8443/spring/security/ping"

    # USER role has no access
    curl -k -i --user user:user -X GET "https://localhost:8443/spring/security/ping"

## Oauth2

(<https://www.baeldung.com/spring-security-oauth>)

## Swagger 2 OpenAPI

- [Swagger ui 2] <https://localhost:8443/swagger-ui.html>
- [OpenAPI 2] <https://localhost:8443/v2/api-docs>

## Swagger 3 Springfox

- [Swagger ui 3] <https://localhost:8443/swagger-ui/>
- [OpenAPI 3] <https://localhost:8443/swagger-ui/index.html>

### 1 of 2. To generate a cookie file

    # use GET to a permitted action:
    curl -k -i --user admin:admin -c cookies.txt -X GET "https://localhost:8443/rest/ping"
    
    # or, use POST to a permitted action:
    curl -k -i -X POST -d username=admin -d password=admin -c cookies.txt https://localhost:8443/login

### 2 of 2. To use the generate cookie file from above to send request to permitted action

    curl -k -i -b cookies.txt -X GET "https://localhost:8443/spring/security/ping"
    
    # or get the JSESSIONID from cookie file and put it into request header: 
    curl -k -i -H "Cookie: JSESSIONID=AE3EBB545C3DC961D4BF009CD29116B9" -X GET "https://localhost:8443/spring/security/ping"

## JWT: 1 of 2. To generate a cookie file for JWT

    curl -k -i -c cookies.txt -d username=admin -d password=admin -X POST "https://localhost:8443/jwt/login"

## JWT: 2 of 2. To use the generate cookie file from above to send request to permitted action for JWT

    curl -k -i -b cookies.txt -X GET "https://localhost:8443/jwt/ping"

## JWT: 1 of 2. Web login

    "https://localhost:8443/jwtlogin"

## JWT: 2 of 2. Web ping

    "https://localhost:8443/jwt/ping"

## This pings SpringUserResource (No Auth)

    curl -k -i -X GET "https://localhost:8443/spring/user/ping"

## This pings PropertyLookupRestController (No Auth)

    curl -k -i -X GET "https://localhost:8443/ping"

## This pings PingRestController (No Auth)

    curl -k -i -X GET "https://localhost:8443/rest/ping"

## This pings SpringSecurityController (Auth)

### USER role has no access

    curl -k -i --user user:user -X GET "https://localhost:8443/spring/security/ping"

#### ADMIN role has access

    curl -k -i --user admin:admin -X GET "https://localhost:8443/spring/security/ping"
    # or
    curl -k -i -X GET "https://admin:admin@localhost:8443/spring/security/ping"

## References

    "https://www.baeldung.com/spring-security-method-security"
    "https://gist.github.com/thomasdarimont/8d6bc243d3b504439e67d57cb0d0bb72"

## Other curl commands

    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d @slow_req.json -X POST https://localhost:8443/spring/slowpost
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://localhost:8443/spring/slowget?name=Luke%20Ma
    
    curl -k -i -X GET "https://localhost:8443/rest/ping"
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://localhost:8443/property?property_id=home_154
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://localhost:8443/property?property_id=0
    
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name":"pojo name"}' -X POST  https://localhost:8443/rest/post/ping
    
    mvn spring-boot:run
    
    curl -k -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -F 'id=101' -F "summary=sum" -F "description=My Desc" https://localhost:8443/HelloHoustonService/rest/todoCrud/formPost
    
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":"102", "summary":"sum", "description":"My desc"}' \
         -X POST  https://localhost:8443/HelloHoustonService/rest/    todoCrud/jsonPost
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d @todo.json -X POST  https://localhost:8443/HelloHoustonService/rest/todoCrud/jsonPost
    
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d "<todo><id>101</id><summary>sum</summary><description>My Desc</description></todo>" \
         -X POST  https://localhost:8443/    HelloHoustonService/rest/todoCrud/xmlPost
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d @todo.xml -X POST https://localhost:8443/HelloHoustonService/rest/todoCrud/xmlPost
    
    
    curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -H "MyHeaderParam: Luke\r\nHeader%0D%0A%0d%0aParam\r\n%0D%0A%0d%0aLine Two: Another%0D%0Line" \
         -d @todo.xml -X POST     https://localhost:8443/HelloHoustonService/rest/todoCrud/xmlPost
    
    
    curl -k -i -H "Content-Type: application/json" -X GET https://localhost:8443/HelloHoustonService/rest/todoCrud/callJSon/101
    
    curl -v -k -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":"59b0dcfb5753540bdc949a42", "summary":"sum", "details":"My details"}' \
         "https://localhost:8443/HelloHoustonService/rest/rent/addRentProperty"
    
    curl -k -i -d {''} https://localhost:8443/spring/stream

## IE, Edge, and Chrome Donot Allow HttpClient to call self signed REST calls

 To toggle http/https: comment/uncomment:

    (1) TomcatPort8080Forwarding.java
    (2) application.properties
        # server.port: 8443
        # server.ssl.key-store: ${JKS_DIR:}/bootkeystore.jks
        # server.ssl.key-store-password: bootpass
        # server.ssl.keyAlias: boot

## <https://dzone.com/articles/solving-dependency-conflicts-in-maven>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <configuration>
                    <rules>
                        <dependencyConvergence />
                    </rules>
                </configuration>
            </plugin>
        </plugins>
    </build>

    mvn enforcer:enforce

## jQuery Form Validation

    <https://jqueryvalidation.org/documentation/>
    <https://jqueryvalidation.org/>

    List of built-in Validation methods
           A set of standard validation methods is provided:
     
           required – Makes the element required.
           remote – Requests a resource to check the element for validity.
           minlength – Makes the element require a given minimum length.
           maxlength – Makes the element require a given maximum length.
           rangelength – Makes the element require a given value range.
           min – Makes the element require a given minimum.
           max – Makes the element require a given maximum.
           range – Makes the element require a given value range.
           step – Makes the element require a given step.
           email – Makes the element require a valid email
           url – Makes the element require a valid url
           date – Makes the element require a date.
           dateISO – Makes the element require an ISO date.
           number – Makes the element require a decimal number.
           digits – Makes the element require digits only.
           equalTo – Requires the element to be the same as another one

## `hello-boot-jwt`

    # 1. Generate JWS Cookie:
    curl -k -i -u "admin:admin" -c cookies.txt -X POST 'https://localhost:8443/jwt/login'
       
    # 2. Use that cookie to access secured sites:
    curl -k -i -b cookies.txt -X GET 'https://localhost:8443/jwt/ping'

## `Mockito` in `@WebMvcTest`

REF: `src/test/java/com/learn/rest/resource/test/new_junit/GreetingControllerWebMvcTest.java`

1. `@WebMvcTest`: Do full Spring application context is started but without the server - BUT, this will instantiate multiple controllers
2. `@WebMvcTest(GreetingController.class)`: Do full Spring application context is started but without the server - This will instantiate only one related controller
3. Spring automatically injects the service dependency into the controller (because of the constructor signature - Use explicit/implicit constructor injection).
4. For @WebMvcTest, baseUrl = "", not "<https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}>".
5. For **JUnit 5**, use: `@ExtendWith(SpringExtension.class)`
6. For **JUnit 4**, use: `@RunWith(SpringRunner.class)`
7. For **Mockito**, use: `@ExtendWith(MockitoExtension.class)` to use `@InjectMocks` and `@Mock`
8. This line has no use: `@TestPropertySource(locations = { "classpath:application.properties", "classpath:application-test.properties" })`
9. `@ActiveProfiles("test")` --- This line causes application-test.properties values to override application.properties values
10. For `@WebMvcTest(value = GreetingController.class)`, this line is required: `@Import(GreetingController.class)`
11. This line is optional: `@AutoConfigureMockMvc`

    @Log4j2
    // @ExtendWith(SpringExtension.class)
    // @ExtendWith(MockitoExtension.class)  /** to use `@InjectMocks` and `@Mock` */
    @WebMvcTest(value = GreetingController.class)
    @Import({ GreetingController.class })
    @ContextConfiguration(classes = BootMvcTestConfig.class)
    @ActiveProfiles("test")
    @WithMockUser(username = "user", password = "user")
    public class GreetingControllerWebMvcTest {

        /**
         * For @WebMvcTest, baseUrl = "", not "https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}".
         */
        final String baseUrl = "https://localhost:8443/";
    
        @Autowired
        private MockMvc mockMvc;
    
        @Autowired
        TestRestTemplate testRestTemplate;
    
        @MockBean
        private GreetingService greetingService;

## Build docker image

    # step 1. create war file
    m clean -P linux package

    # step 2. docker login
    docker login --username xxlukema/Cfg

    # step 3. docker buildx build
    # v0.0.2 logs to 'stdout/stderr'
    docker buildx build . -t xxlukema/hello-shein-boot:0.0.2
    
    # step 4. docker push
    # optional if run docker-compose, required if deploy to k8s:
    docker push xxlukema/hello-shein-boot:0.0.2

## `@RestControllerAdvice(assignableTypes = { BookResource.class })`

To specify array of controllers to advise

    /**
     * !!! Trick: To specify array of controllers to advise
     * @RestControllerAdvice(assignableTypes = { BookResource.class })
     */
    @RestControllerAdvice
    public class GlobalExceptionHandler {
