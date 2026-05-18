# `map()` vs `flatMap()`

## `Optional<T> optional.flatMap()` vs `Optional<T> optional.map()`

- 'map' is for **Optional**: `Optional<String>`
- 'flatMap' is for **nested Optionals**, or **This Optional has Optional**: `Optional<Optional<String>>`

### Examples of `Optional<T> optional.flatMap()` vs `Optional<T> optional.map()`

    /**
     * Example 1. map()` for `List<Person> people`
     */
    List<Person> people = Arrays.asList(new Person(30), null, 
            new Person(25), new Person(40));
    
    List<Optional<Integer>> ages = people.stream()
            .map(person -> Optional.ofNullable(person)
            .map(Person::getAge))
            .collect(Collectors.toList());
    
    // Output the ages, showing empty for null Person objects
    ages.forEach(age -> System.out.println(age.orElse(null)));
    
    /**
     * Example 2. map()` for `List<Book> books`
     */
    Optional<Book> books = getBooks();
    
    // Chaining operations with map()
    Optional<Integer> titleLength = book.map(Book::getTitle)    // Extract title
            .map(String::toUpperCase) // Convert title to uppercase
            .map(String::length);     // Get length of the title
    
    // Output the length of the title if present
    titleLength.ifPresent(length -> System.out.println("Title length: " + length));
    
    // Assume this method fetches a customer, which may or may not have a loyalty status
    Optional<Customer> customer = getCustomer();
    
    // Applying conditional logic with map()
    Optional<Double> discount = customer.map(Customer::getLoyaltyStatus)
            .map(Main::calculateDiscount);
    
    // Output the discount if present
    discount.ifPresent(d -> System.out.println("Discount: " + d + "%"));
    
    /**
     * Example 2. List<Person> people
     */
    
    // Create an Optional containing a String
    Optional<String> optionalString = Optional.of("Hello, World!");
    
    // Use flatMap to transform the String into an Optional of its length
    Optional<Integer> flattened = optional.flatMap(value -> {
      int length = value.length();
      return Optional.of(length);
    });
    
    // Print the result if it exists
    flattened.ifPresent(System.out::println); // Output: 13
    
    // Assume this method fetches a library which may or may not contain a book
    Library library = getLibrary();
    
    // Using flatMap to navigate through nested Optionals
    Optional<String> authorName = Optional.ofNullable(library)
            .flatMap(Library::getBook)
            .flatMap(Book::getAuthor)
            .map(Author::getName);
    
    // Output the author's name if present
    authorName.ifPresent(System.out::println);

## `Stream.flatMap()` vs `Optional<T> optional.flatMap()`

`Stream.flatMap()` method is used to flatten a **Stream of collections** to a **Stream of objects**.

    Stream<Collection<Item>> —-> flatMap() —-> Stream<Item>

    /**
     * Example 1. Collecting Nested Arrays into a Single List
     */
    Merging Arrays into a Single ListString[][] dataArray = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}, {"g", "h"}};
    List<String> listOfAllChars = Arrays.stream(dataArray)
                  .flatMap(x -> Arrays.stream(x))
                  .collect(Collectors.toList());
    System.out.println(listOfAllChars);

    /**
     * Example 2. Converting Nested Lists into a Single List
     */
    List<Integer> list1 = Arrays.asList(1,2,3);
    List<Integer> list2 = Arrays.asList(4,5,6);
    List<Integer> list3 = Arrays.asList(7,8,9);
      
    List<List<Integer>> listOfLists = Arrays.asList(list1, list2, list3);
     
    List<Integer> listOfAllIntegers = listOfLists.stream()
              .flatMap(x -> x.stream())
              .collect(Collectors.toList());
    
    System.out.println(listOfAllIntegers);
