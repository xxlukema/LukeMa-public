# JUni5

## Ordered Test

    @TestMethodOrder(OrderAnnotation.class)
    class OrderUnitTest {

        /**
         * This state will be reset for each test.
         */
        int changingCounter = 0;

        /**
         * This static state will **NOT** be reset for each test because it is `static`.
         */
        static int notChangingCounter = 0;
    
        @Test
        @Order(1)
        void firstTest() {
        }
    
        @Test
        @Order(2)
        void secondTest() {
        }
    }

## Deliberately Sharing State

[`@TestInstance`]<https://www.baeldung.com/junit-testinstance-annotation>

**Sharing state is usually an anti-pattern in unit tests, but can be useful in integration tests.**
**By default, both JUnit 4 and 5 create a new instance of the test class before running each test method.** This provides a clean separation of state between tests.
The per-class lifecycle supports sequential tests that intentionally share state.

### 1. `@TestInstance(Lifecycle.PER_CLASS)`

Use cases:

1. Resource is expensive to create for each test.
2. Deliberately share states between each tests.
3. Frequently used for **Integration Tests**.

    @TestInstance(Lifecycle.PER_CLASS)
    @TestMethodOrder(OrderAnnotation.class)
    class OrderUnitTest {

        /**
         * 1. This state will **NOT** be reset for each test because `@TestInstance(Lifecycle.PER_CLASS)` prevents recreation of this class for each test. 
         * 2. No need to make it `static`
         */
        int changingCounter = 0;
    
        @Test
        @Order(1)
        void firstTest() {
        }
    
        @Test
        @Order(2)
        void secondTest() {
        }
    }

#### Sharing Some State

We can reset variables that need to be cleaned between tests with methods annotated with `@BeforeEach` or `@AfterEach`.

### 2. Use `static` states

    @TestMethodOrder(OrderAnnotation.class)
    class OrderUnitTest {

        /**
         * This static state will **NOT** be reset for each test because it is `static`.
         */
        static int notChangingCounter = 0;
    
        @Test
        @Order(1)
        void firstTest() {
        }
    
        @Test
        @Order(2)
        void secondTest() {
        }
    }

### 3. Use `SenarioContext` class --- Used in Freddie Mac

    // `SenarioContext` class
    public class SenarioContext {

        private static final Map<String, Object> map = new ConcurrentHashMap<>();
    
        private SenarioContext() {
        }
    
        public static void setContext(String key, Object value) {
            map.put(key, value);
        }
    
        @SuppressWarnings("unchecked")
        public static <T> T getContext(String key) {
            return (T) map.get(key);
        }
    
    }

    // Use of `SenarioContext` class
    @TestMethodOrder(OrderAnnotation.class)
    class OrderUnitTest {

        @Test
        @Order(1)
        void firstTest() {
            /**
             * store "id" for later use by other tests.
             */
            SenarioContext.setContext("id", 1);
        }
    
        @Test
        @Order(2)
        void secondTest() {
            /**
             * retrieve "id" that is stored by other test.
             */
            int id = SenarioContext.getContext("id", 1);
        }
    }
