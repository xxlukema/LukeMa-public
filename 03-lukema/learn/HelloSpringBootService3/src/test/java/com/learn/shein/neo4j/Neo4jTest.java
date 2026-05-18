package com.learn.shein.neo4j;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Query;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.internal.value.MapValue;

import lombok.extern.log4j.Log4j2;


/**
 * [Ref:]<https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall>
 * `@Before` vs `@BeforeClass` vs `@BeforeEach` vs `@BeforeAll`
 *   @Before (junit4, replaced with @BeforeClass) - Run before each test.
 *   @BeforeClass (junit4, replaced with @BeforeAll) - (1) Must be static method. (2) Executed only once before running all tests.
 *   @BeforeEach (junit5) - Same as @Before. Renamed with clearer names to avoid confusion.
 *   @BeforeAll (junit5)- (1) Must be static method. (2) Executed only once before running all tests. Same as @BeforeClass. Renamed with clearer names to avoid confusion.
 *   @After (junit4, replaced with @AfterEach) - After the execution of each test.
 *   @AfterClass (junit4, replaced with @AfterAll) - (1) Must be static method. (2) Executed only once after running all tests.
 *   @AfterEach (junit5) - Same as @After. Renamed with clearer names to avoid confusion.
 *   @AfterAll (junit5) - (1) Must be static method. (2) Executed only once before running all tests. Same as @AfterClass. Renamed with clearer names to avoid confusion.
 */
@Log4j2
public class Neo4jTest {

  private static Driver driver;

  private static String uri = "bolt://localhost:7687";
  private static String user = "neo4j";
  private static String password = "test@1234";

  @BeforeAll
  public static void initDriver() {
    log.debug("init driver. url: {}, user: {}, password: {}", uri, user, password);
    driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
  }

  @AfterAll
  public static void closeDriver() {
    driver.close();
  }

  /**
   *  match (n:Greeting) return n
   */
  @Test
  public void testGreeting() {

    log.debug(() -> "Begin test.");

    Map<String, Value> map = new HashMap<>();
    map.put("message", Values.value("Hello World from Neo4j"));

    Value parameters = new MapValue(map);

    try (var session = driver.session()) {
      var greeting = session.executeWrite(tx -> {
        var query = new Query("CREATE (a:Greeting) SET a.message = $message RETURN a.message + ', from node ' + id(a)",
            parameters);
        var result = tx.run(query);
        return result.single().get(0).asString();
      });

      log.debug("Greetings: {}", () -> greeting);
    }

    log.debug(() -> "End test.");
  }
}
