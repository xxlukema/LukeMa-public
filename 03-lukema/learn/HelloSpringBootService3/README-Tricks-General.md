# Spring Boot FAQ

## Can REST Response Body Contain a Mapped Hibernate Entity?

!!! Important !!!

Response body cannot be a mapped hibernate entity. It must be a DTO. If entity is used, the association relationship will cause stackoverflow when serialize the body.

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/signin", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> signin(@Valid @RequestBody SheinUserDto userDto, ServletRequest request, HttpServletResponse response) {
      ...
      return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(userDto); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
    }

## Authentication and Authorization Using Gmail Account

Google search: authentication and authorization gmail account java

1. [How to]<https://developers.google.com/gmail/api/quickstart/java>
2. [stackoverflow]<https://stackoverflow.com/questions/72391666/gmail-api-server-to-server-authentication>

## `Neo4j` Cyclic Relationships Will Causes `StackOverflow` Exception

**!!! Important !!!**
**!!! Trick !!!**

Cyclic relationship will cause StackOverflow error: `person: [!!!com.learn.shein.neo4j.entity.Person@29fef6c0=>java.lang.StackOverflowError:null!!!]`

## `Neoj4` username and password

The default minimum password length is 8 characters. Use the `dbms.security.auth_minimum_password_length` configuration to change it.

1. default username is `neo4j`
2. default password is `neo4j` --- if and only if (iff) you forgot to set default password prior to first start of `neo4j`.
3. cmd to set default password: `neo4j-admin dbms set-initial-password test@1234 [--require-password-change]` or `neo4j-admin dbms set-initial-password test@1234`.
4. if this is **NOT** done prior to first neo4j start up, for first logon, user will be required to change password.

### 1. cmd to set default password

    neo4j-admin dbms set-initial-password test@1234
    # OR
    neo4j-admin dbms set-initial-password test@1234 [--require-password-change]

### 2. Change password first time user logon

    cypher-shell -u neo4j -p neo4j
    > Password change required
    > new password: test@1234
    > confirm password: test@1234

### 3. Trick

    # !!! Trick
    # 3.1 username is `neo4j`
    #     neo4j-admin dbms set-initial-password test@1234
    # 3.2 this must be done prior to first neo4j start up.
    # 3.3 if this is NOT done prior to first neo4j start up, the default password will be `neo4j`.
    # 3.4 if this is NOT done prior to first neo4j start up, for first logon, user will be required to change password
    cypher-shell -u neo4j -p neo4j
    > Password change required
    > new password: test@1234
    > confirm password: test@1234

## MongoDB Tricks: Auto-Generated Field for MongoDB using Spring Boot

[Auto-Generated Field for MongoDB using Spring Boot]<https://www.baeldung.com/spring-boot-mongodb-auto-generated-field>

1. In service tier, `AbstractMongoEventListener<Person>` is used to set new document id automatically.
2. Do **NOT** use `spring.data.mongodb.uri: mongodb://ENC(zjpIoh+5DcY+QGRUOxINeA==):ENC(DIM8/d6v+5pS57wfdRfgaA==)@localhost:27017/lukedb`, because `jasypt` does not work this way.

    @Service
    public class PersonService {

        private final PersonRepository personRepository;
        // private final PersonSeqGeneratorService personSeqGeneratorService; <=== not needed with `AbstractMongoEventListener<Person>`
    
        /**
         * Implicit constructor injection
         */
        public PersonService(PersonRepository personRepository, PersonSeqGeneratorService personSeqGeneratorService) {
            this.personRepository = personRepository;
            // this.personSeqGeneratorService = personSeqGeneratorService;
        }
    
        /**
         * !!! Trick !!!
         * `AbstractMongoEventListener<Person>` is used to set new document id automatically.
         * public class PersonModelListener extends AbstractMongoEventListener<Person>
         */
        public Person insertPerson(@NotNull Person person) {
            // Long seq = this.personSeqGeneratorService.generateSequence(Person.SEQ_NAME);  <=== not needed with `AbstractMongoEventListener<Person>`
            // person.setId(seq);  <=== not needed with `AbstractMongoEventListener<Person>`
    
            if (person == null) {
                return null;
            }
    
            return personRepository.insert(person);
        }

## `nginx` ingress reverse proxy tricks

!!! Tricks

1. proxy listens to port 443
2. for all web path, forward traffic to port 9443
3. for all `/sping/**` path, forward traffic to port 8443
4. security group `inbound rules` must make ports 443, 8443, 9443 accessible to all-ipv4 traffic from the world (0.0.0.0/0)
5. Inside `default-nginx-ingress.conf`, `server_name  52.3.85.231;`   # !!! Trick: It is fullname of host, or ip address of host, but NOT localhost.

## All `GET` `@PathVariable`s must be urlsafe

Use `this.nmsService.toBase64Urlsafe(title)`.

    /**
     * `src/app/sell/find-match/find-match.service.ts`:
     */
    
    private getConditionsByTitleUrl = '/spring/shein/getConditionsByTitle/{title}';

    const base64Title = this.nmsService.toBase64Urlsafe(title);
    const url = env.baseUrl + this.getItemsForSellerUrl.replace('{sellerUsername}', username);

    /**
     * `src\main\java\com\learn\shein\mongo\resources\MongoResource.java`
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getConditionsByTitle/{title}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CategoryConditions> getConditionsByTitle(@PathVariable("title") String title)
        throws Exception {
        log.debug("Enter.... title: {}", () -> title);

        String newTitle = Base64Utils.decodeUrlsafeInput(title);

        log.debug("Enter.... new title: {}", () -> newTitle);

## Implicit constructor injection

- "constructor" injection is better than "property" injection, because the injection is immutable.
- The member must have `final` modifier.
- Property injection is used for learning purpose only.

## `fluentd` is running correctly. Why I cannot find fluentd output files specified in `fluent.conf`?

Answer: You forgot to mount your local volume to fluentd container's output director. Fluentd is outputing files to its destination correctly.

    ## !!! Trick
    # `compose.yml`:
    fluentd:
      image: fluentd
      container_name: fluentd-lma
      user: root
      volumes:
        # fluent.conf mount point
        - ${HOME}/ec2-docker/fluent-conf/:/fluentd/etc/:rw
        # fluentd file store mount point for all the log file. log files are defined in `fluent.conf`.
        - ${HOME}/fluentd-store/:/fluentd-store/:rw    # <======= critical! must have


    # on ec2 host
    docker-compose up -d
    
    docker container ls
    > fluentd-lma

    docker logs boot-lma -f

    docker logs fluentd-lma -f

    docker exec -it fluentd ls /
    docker exec -it fluentd find /fluentd-store

    find /fluentd-store

## xml file comments

1. All xml comments must be surrounded by spaces: `<!-- my comment -->`. Notice the `&nbsp;` in the front or end of comments.
2. Violation will cause the comment break.

### Examples of correct and wrong xml comments

    # wrong comments:
    <!--my comment-->          <====== bad, because auto formatter will break the line into:
    <!--my
           comment-->
    
    # correct comment:
    <!-- my comment -->

## `maven-enforcer-plugin`

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-enforcer-plugin</artifactId>
        <version>3.5.0</version>
        <configuration>
            <rules>
                <dependencyConvergence />
            </rules>
        </configuration>
        <executions>
          <execution>
            <id>enforce-no-snapshot</id>
            <goals>
              <goal>enforce</goal>
            </goals>
            <configuration>
              <rules>
                <requireReleaseDeps>
                  <message>No snapshot allowed</message>
                  <onlyWhenRelease>true</onlyWhenRelease>
                </requireReleaseDeps>
              </rules>

              <!-- toggle -->
              <fail>false</fail>
            </configuration>
          </execution>
        </executions>
      </plugin>

## Fix `module jaja.base does not "open java.time" to unnamed module`

Solution:

    // Step 1. Add `java.17.options` to `properties`
    <properties>
      <java.17.options>
        --add-opens=java.base/java.time=ALL-UNNAMED
      </java.17.options>
    </properties>

    // Step 2. Add `java.17.options` to `maven-surefire-plugin`
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <argLine>
                ${java.17.options}
                -D...
                -D...
            </argLine>
        </configuration>
    </plugin>

## `hibernate-jpamodelgen` generates `<MyClass>_.java` java code

    <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.13.0</version>
          <configuration>
               <annotationProcessorPaths>
                        <path>
                           <groupId>org.mapstruct</groupId>
                           <artifactId>mapstruct-processor</artifactId>
                           <version>1.5.5.Final</version>
                         </path>
                         <path>
                           <groupId>org.projectlombok</groupId>
                           <artifactId>lombok</artifactId>
                           <version>1.18.38</version>
                         </path>
                         <path>
                           <groupId>org.projectlombok</groupId>
                           <artifactId>lombok-mapstruct-binding</artifactId>
                           <version>0.2.0</version>
                         </path>

                         <!-- Add only when it is needed! -->
                         <path>
                           <groupId>org.hibernate.orm</groupId>
                           <artifactId>hibernate-jpamodelgen</artifactId>
                           <version>5.4.3.Final</version>
                         </path>
                    </annotationProcessorPaths>
          </configuration>
    </plugin>

## `@RequiredAgrsConstructor` and `@Value`

- `@RequiredArgsConstructor` is for all `final` and `non-null` fields.
- `@RequierdArgsConstructor` doesnot add `non-final` fields to the constructor parameters.
- But, `@RequierdArgsConstructor` does add `non-final` fields to the constructor parameters if the field has `@NonNull` lombok annotation.
- To generate a constructor with `non-null` fields, use the `@NonNull` Lombok annotation.
- `final` members and members annotated with `@NonNull` are included in the constructor parameters and definition.
- Members which are **neither** `final` **nor** annotated with `@NonNull` are not initialized by the generated constructor.
