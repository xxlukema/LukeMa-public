package com.lear.test.mcok_vs_spy;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.log4j.Log4j2;


@Log4j2
// @RunWith(MockitoJUnitRunner.class) // JUnit 4. Must have.
@ExtendWith(MockitoExtension.class) // for JUnit 5. Must have.
class MockVsSpyTest {

    /**
     * Mock vs Spy
     * Mock: create a mock object that does not have any behavior
     * Spy: create a spy object that has the same behavior as the real object
     */
    /**
    @Spy
    List<String> spyList = new ArrayList<>();
    */

    /**
     * @Mock
     * List<String> mockList;
     */

    /**
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    */

    @Test
    void testMock() {
        /**
         * For mock: No need to initialize mockList with `= new Arraylist<>()`.
         */
        @SuppressWarnings("unchecked")
        List<String> mockList = mock(ArrayList.class);

        /**
         * It is strange that mockList.isEmpty() returns false.
         */
        log.info("mockList.isEmpty(): {}", mockList.isEmpty());

        mockList.add("mock");

        verify(mockList).add("mock");

        log.info("Mock list size: {}", mockList.size());
        log.info("mockList.isEmpty(): {}", mockList.isEmpty());

        /**
         * It is strange that mockList.isEmpty() returns false.
         */
        assertFalse(mockList.isEmpty(), "Mock list should be empty");
    }

    @Test
    void testSpy() {
        /**
         * For spy: Must initialize spyList with `= new Arraylist<>()`.
         */
        List<String> spyList = spy(new ArrayList<>());

        spyList.add("spy");

        verify(spyList).add("spy");

        log.info("Spy list size: {}", spyList.size());
        log.info("Spy list: {}", spyList);

        assertFalse(spyList.isEmpty(), "Mock list should not be empty");
    }

}
