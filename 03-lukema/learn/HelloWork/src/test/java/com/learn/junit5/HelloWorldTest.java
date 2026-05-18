package com.learn.junit5;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

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
public class HelloWorldTest {
    private static final Logger LOG = LogManager.getLogger();

    //private static final String QuantityFormat = "######0.00";

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @EnabledOnOs({ OS.WINDOWS, OS.MAC })
    public void testMain1()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @DisabledOnOs(OS.LINUX)
    public void testMain2()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @EnabledOnJre({ JRE.JAVA_10, JRE.JAVA_11 })
    public void testMain0()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @DisabledOnJre(JRE.OTHER)
    public void testMain3()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    public void testMain4()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @DisabledIfSystemProperty(named = "file.separator", matches = "[/]")
    public void testMain5()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GDMSESSION", matches = "ubuntu")
    public void testMain6()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    public void testMain7()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    public void testMain7e()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    public void testMain7a()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    public void testMain7b()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @Test
    public void testMain7c()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }

    @ThisTestWillOnlyRunAtLinuxAndMacWithJava9Or10Or11
    public void someSuperTestMethodHere() {
        // this method will run with Java9, 10, 11 and Linux or macOS.
    }

    @RepeatedTest(2)
    @CoinToss
    public void gamble() {
        // this method run run roughly 50% of the time
    }
}
