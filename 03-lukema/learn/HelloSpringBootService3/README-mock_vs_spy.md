# Mockito

    // @RunWith(MockitoJUnitRunner.class) // JUnit 4. Must have.
    @ExtendWith(MockitoExtension.class) // for JUnit 5. Must have.
    public class SomeManagerTest {
    
        @InjectMocks
        private SomeManager someManager;
    
        /**
         * For mock: No need to initialize mockList with `= new Arraylist<>()`.
         */
        @Mock
        private SomeDependency someDependency; // this will be injected into someManager
     
         // tests...
    
    }

## `@InjectMocks` vs `@Mock` vs `@Spy`

## `@Mock` vs `@Spy` must have `@ExtendWith(MockitoExtension.class)`

    /**
     * Mock vs Spy
     * Mock: create a mock object that does not have any behavior
     * Spy: create a spy object that has the same behavior as the real object
     * 
     * For spy: Must initialize spyList with `= new Arraylist<>()`.
     */
    @Spy
    List<String> spyList = new ArrayList<>();

    /**
     * Mock: create a mock object that does not have any behavior
     * For mock: No need to initialize mockList with `= new Arraylist<>()`.
     */
    @Mock
    List<String> mockList;

## If mocked instance needs to call another method, it will not call, while If spyed instance needs to call another method, it will call

## Call a mocked object method, by default it does nothing. Call a mocked object method, it does real call

    // @Mock <=== Mock doesn't do the real thing other than mock.
    @Test
    void whenCreateMock_thenCreated() {
        /**
         * For mock: No need to initialize mockList with `= new Arraylist<>()`.
         */
        @SuppressWarnings("unchecked")
        List mockedList = mock(ArrayList.class);
    
        mockedList.add("one");
        verify(mockedList).add("one");
    
        assertThat(mockedList).hasSize(0);
    }

    // @Spy <=== Spy does real thing other than mock.
    @Test
    void whenCreateSpy_thenCreate() {
        /**
         * For spy: Must initialize spyList with `= new Arraylist<>()`.
         */
        List spyList = Mockito.spy(new ArrayList());
    
        spyList.add("one");
        Mockito.verify(spyList).add("one");
    
        assertThat(spyList).hasSize(1);
    }

## `@Spy`

`Mockito.spy()` to spy on a real object.

    // `spy()`
    @Test
    void givenUsingSpyMethod_whenSpyingOnList_thenCorrect() {
        /**
         * For spy: Must initialize spyList with `= new Arraylist<>()`.
         */
        List<String> spyList = spy(new ArrayList<>());
    
        spyList.add("one");
        spyList.add("two");
    
        verify(spyList).add("one");
        verify(spyList).add("two");
    
        assertThat(spyList).hasSize(2);
    }


    // `@Spy`
    @Spy
    List<String> spyList = new ArrayList<>();
    
    @Test
    void givenUsingSpyAnnotation_whenSpyingOnList_thenCorrect() {
        spyList.add("one");
        spyList.add("two");
    
        verify(spyList).add("one");
        verify(spyList).add("two");
    
        assertThat(aSpyList).hasSize(2);
    }
    
    // Stubbing a `Spy`
    @Test
    void givenASpy_whenStubbingTheBehaviour_thenCorrect() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);
    
        assertEquals(0, spyList.size());
    
        doReturn(100).when(spyList).size();
        assertThat(spyList).hasSize(100);
    }

## `@ExtendWith(MockitoExtension.class)` and `MockitoAnnotations.openMocks(this)` are multual exclusive

    @Log4j2
    @ExtendWith(MockitoExtension.class)
    class CompPlanDataServiceTest {
    
        @InjectMocks
        CompPlanDataService service;
    
        @Mock
        BmsAppConfigService bmsAppConfigService;
        @Mock
        CompPlanProfileRepository compPlanProfileRepository;
    
        @BeforeEach
        public void before() {
            /**
             * `@ExtendWith(MockitoExtension.class)` and `MockitoAnnotations.openMocks(this)` are multual exclusive
             */
            // MockitoAnnotations.openMocks(this);
        }

        @Test
        void testOne() {
        }

    }
