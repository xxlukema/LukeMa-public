package com.learn.junit5;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


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
public class MethodTest {

    @ParameterizedTest
    @ValueSource(strings = { "", "  " })
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {

        log.info("Begin Test.");

        assertTrue(Strings.isBlank(input));

        log.info("End Test.");
    }

    @ParameterizedTest
    @MethodSource("com.learn.junit5.StringParams#blankStrings")
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
        log.info("Begin Test.");
        
        assertTrue(Strings.isBlank(input));
        
        log.info("End Test.");
    }
}

@Log4j2
class StringParams {

    static Stream<String> blankStrings() {
        
        log.debug("Called.");
        
        return Stream.of(null, "", "  ");
    }
}
