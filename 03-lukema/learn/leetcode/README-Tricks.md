# LeetCode For Luke Ma

## Java Versions and Features

[Java Versions and Features]<https://www.marcobehler.com/guides/a-guide-to-java-versions-and-features#_java_features_8_19>

## JShell

    # Start JShell:
    jshell

## `lombok` on `vscode` on aws linux workspace

### 1. install `lombok` extension

### 2. enable `lombok` on `vscode`

    # `.vscode/setting.json` or `/home/lma/.config/Code/User/settings.json`:
    "java.jdt.ls.lombokSupport.enabled": true,
    "java.jdt.ls.vmargs": "-XX:+UseParallelGC -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true -Xmx1G -Xms100m -javaagent:\"/home/lma/02-LukeTools/lombok-1.18.26.jar\"",

### 3. clean up java project: `Shift + Ctrl + p` --> Java: Clean Java Language Server Workspace

## Run Java from CLI

## (Method 1) To see stackoverflow beyond console terminal

    # Redirect output to 1.txt
    # Provide main class:
    # 
    mvn compile exec:java -Dexec.mainClass="com.learn.backtrack.MinSumPath" -Dexec.cleanupDaemonThreads=false > 1.txt
    mvn compile exec:java -Dexec.mainClass="com.learn.backtrack.GrayCode" -Dexec.cleanupDaemonThreads=false > 1.txt
    #
    # Or
    # Add "<mainClass>com.learn.backtrack.MinSumPath</mainClass>" to pom.xml <artifactId>exec-maven-plugin</artifactId>
    # Then run the following command:
    #
    mvn compile exec:java -Dexec.cleanupDaemonThreads=false > 1.txt
    #
    # To pass CLI parameters:
    #
    mvn compile exec:java -Dexec.mainClass="com.learn.backtrack.MinSumPath" -Dexec.args="First Second" -Dexec.cleanupDaemonThreads=false > 1.txt
    #
    mvn compile exec:java -Dexec.mainClass="com.learn.other.MyScann*" -Dexec.cleanupDaemonThreads=false

## `pom.xml` with `exec-maven-plugin` (Run mvn main classes from CLI)

## Run JUnit Tests

    mvn compile test -Dtest="com.learn.test.other.ScannerTe*"

## Dynamic Programs from Leetcode

For most people, it's easiest to start by coming up with a recursive brute-force solution and then adding memoization to it.
After that, they then figure out how to convert it into an (often more desired) bottom-up tabulated algorithm.

Indicators of dynamic programming:

1. For the "number of ways" to do something
2. We need to make decisions that may depend on previously made decisions

Three components to solve dynamic programing:

1. Establishing a base case
2. A function or array that represents the answer to the problem for a given state
3. A way to transition between states, such as `totalWays(3)` and `totalWays(4)`. This is called a **recurrence relation** and the hardest part of the solution

## Memoization Creationg Point - Important

It is important to remember that **memo** MUST be created inside the base call (the call that triggers recursion). It cannot be created inside
recursion. If **memo** is created inside recursion, the **memo** will be a temporary variable, and it will be re-created for every recursion, and the
**memo** values will be lost for every recursion.

`HashMap` is used as `memo` for **most** top-down dynamic programming solutions, instead of array. Although array is slightly more efficient, using `HashMap`
as `memo` is a better practice.

## `HashSet`, `LinkedHashSet`, `TreeSet`

**Note**: If you want a sorted Set then it is better to add elements to `HashSet` and then convert it into `TreeSet` rather than creating a `TreeSet` and adding elements to it.

1. `TreeSet` - Time: `O(log n)` for `search/insert/delete`.
2. `HashSet` - Time: `O(1)` **constant time** `contains/add/remove`.
3. `LinkedHashSet` - (1) Time: `O(1)` for `contains/add/remove` (2) Maintains the order of insertion (Note that this is not sorted order, but the order in which elements are inserted).

`TreeSet` uses a **Red-Black** (self balanced) tree algorithm underneath to sort out the elements. When one needs to perform read/write operations frequently, then `TreeSet`
is a good choice.

`TreeSet` has a greater locality than HashSet. If two entries are nearby in the order, then `TreeSet` places them near each other in data structure and hence in memory,
while `HashSet` spreads the entries all over memory regardless of the keys they are associated to.

The sorted list given by `TreeSet` is always in **ascending** order.

The Iterators returned by `TreeSet` and `HashSet` are **fail-fast**. That means that any modification of the Set at any time after the Iterator
is created will throw a `ConcurrentModificationException`

## `PriorityQueue` vs `TreeSet` in Performance

- Both `PriorityQueue` and `TreeSet`- adding/removing/searching: `O(log(N))`
- `PriorityQueue` - poll: `O(1)`.
- `PriorityQueue` - data is not ordered, but `poll()` is guareentined to return the first in queue.

## Combination vs Permutation

### Combinations (组合)

Sometimes, we want to count all of the possible ways that a single set of objects can be selected - without regard
to the order in which they are selected.

- In general, n objects can be arranged in n (n - 1) (n - 2) ... (3) (2) (1) ways. This product is represented by the
  symbol n!, which is called n <i>factorial</i>. (By convention, 0! = 1.)

- A combination is a selection of all or part of a set of objects, without regard to the order in which they were
  selected. This means that XYZ is considered the same combination as ZYX.

- The number of combinations of n objects taken r at a time is denoted by n C <sub>r</sub>.

Rule 1. The number of combinations of n objects taken r at a time is

<pre>
 n C <sub>r</sub> = n (n - 1) (n - 2) ... (n - r + 1) / r! = n! / r!(n - r)!
</pre>

### Permutaion (排列)

Often, we want to count all of the possible ways that a single set of objects can be arranged. For example, consider
the letters X, Y, and Z. These letters can be arranged a number of different ways (XYZ, XZY, YXZ, etc.) Each of these
arrangements is a permutation.

- A permutation is an arrangement of all or part of a set of objects, with regard to the order of the arrangement.
  This means that XYZ is considered a different permutation than ZYX.

- The number of permutations of n objects taken r at a time is denoted by n P <sub>r</sub>.

- The number of permutations of n objects taken r at a time is

<pre>
 n P <sub>r</sub> = n (n - 1) (n - 2) ... (n - r + 1) = n! / (n - r)!
</pre>

### Combination and Permutation Relationship

Combinations and permutations are related according to the following formulas:

<pre>

 n P <sub>r</sub> = n C <sub>r</sub> * r!
 and
 n C <sub>r</sub> = n P <sub>r</sub> / r!

</pre>

## Total Number of Substrings

Total number of substrings of all lengths from 1 to n:

<pre>
 n + (n - 1) + (n - 2) + (n - 3) + … 2 + 1 = n * (n + 1)/2
</pre>

## Power Set

If `S` is a finite set with the **cardinality** `|S| = n` (i.e., the number of all elements in the set S is n), then the number
of all the subsets of `S` is

<pre>
| P(S) | = 2 <sup>n</sup>
</pre>

### Example

If `S` is the set `{x, y, z}`, then all the subsets of S are

<pre>
  {} (also denoted empty set or the null set)
  {x}
  {y}
  {z}
  {x, y}
  {x, z}
  {y, z}
  {x, y, z}
</pre>

and hence the **Power Set** of `S` is `{{}, {x}, {y}, {z}, {x, y}, {x, z}, {y, z}, {x, y, z}}`

### Power Set Is Binary

Here is the most amazing thing. To create the Power Set, write down the sequence of binary numbers (using n digits),
and then let `1` mean "put the matching member into this subset".

So `101` is replaced by {'s', 's'}

Like this:

<pre>
       abc     Subset
  0    000     { }
  1    001     {c}
  2    010     {b}
  3    011     {b,c}
  4    100     {a}
  5    101     {a,c}
  6    110     {a,b}
  7    111     {a,bc}
</pre>

Well, they are not in a pretty order, but they are all there.

## Default `hashCode`

The value returned by the default implementation of `hashCode()` is called **identity hash code**.

FYI: Even if a class overrides `hashCode()`, you can always get the **identity hash code** of an object o by calling `System.identityHashCode(o)`.

Common wisdom is that the **identity hash code** uses the **integer representation of the memory address**. That's also what the J2SE JavaDocs
for `Object.hashCode()` imply:

<pre>
... is typically implemented by converting the internal address of
the object into an integer, but this implementation technique is not
required by the Java™ programming language.

Whenever it is invoked on the same object more than once during an
execution of a Java application, the hashCode method must consistently
return the same integer.
</pre>

!!! **So default implementation is JVM-specific**

### Recap (Test Result)

<pre>
    The default hashCode() implementation (identity hash code) has nothing to do with the object's memory address, at least in OpenJDK.
    In versions 6 and 7 it is a randomly generated number. In 8 and, for now, 9, it is a number based on the thread state.
</pre>

## Bit Operator

<pre>
   Operator     Meaning
      &         Bitwise AND operator
      |         Bitwise OR operator
      ^         Bitwise exclusive OR operator
      ~         Binary One's Complement Operator. It is also called NOT operator. It is a unary operator.
      <<        Left shift operator
      >> -------Right shift operator

      x     y    x & y    x | y    x ^ y
      0     0      0        0        0
      0     1      0        1        1
      1     0      0        1        1
      1     1      1        1        0
</pre>

### XOR - Can be used to detect a number "a" appeared "odd" time

<pre>
     0 ^ a          = a
     0 ^ a ^ a      = 0

     b ^ a          = an intermediate number
     b ^ a ^ a      = b
</pre>

#### Detect a Number Appeared Once But Not Three Time

It is so amazing with the following code:

<pre>
         seenOnce = ~seenTwice & (seenOnce ^ a);          <---- 1st run: a. 2nd run: 0. 3rd run: 0.
         seenTwice = ~seenOnce & (seenTwice ^ a);         <---- 1st run: 0. 2nd run: a. 3rd run: 0.

     ****************** Sample Code Below*******************

         int a = 21;

         int seenOnce = 0;
         int seenTwice = 0;

         seenOnce = ~seenTwice & (seenOnce ^ a);
         seenTwice = ~seenOnce & (seenTwice ^ a);
         log.debug("1st a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
         Assertions.assertEquals(a, seenOnce, "1st time.");
         Assertions.assertEquals(0, seenTwice, "1st time.");

         seenOnce = ~seenTwice & (seenOnce ^ a);
         seenTwice = ~seenOnce & (seenTwice ^ a);
         log.debug("2nd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
         Assertions.assertEquals(0, seenOnce, "2nd time.");
         Assertions.assertEquals(a, seenTwice, "2nd time.");

         seenOnce = ~seenTwice & (seenOnce ^ a);
         seenTwice = ~seenOnce & (seenTwice ^ a);
         log.debug("3rd a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
         Assertions.assertEquals(0, seenOnce, "3rd time.");
         Assertions.assertEquals(0, seenTwice, "3rd time.");

         seenOnce = ~seenTwice & (seenOnce ^ a);
         seenTwice = ~seenOnce & (seenTwice ^ a);
         log.debug("4th a: {}, seenOnce: {}, seenTwice: {}", a, seenOnce, seenTwice);
         Assertions.assertEquals(a, seenOnce, "4th time.");
         Assertions.assertEquals(0, seenTwice, "4th time.");
</pre>

#### Detect a Number Appeared Odd Times But Not Even Times

It is so amazing with the following code:

<pre>
        int a = 21;

        int odd = 0 ^ a;
        int even = 0 ^ a ^ a;

        log.debug(" odd a: {}, odd: {}", a, odd);
        Assertions.assertEquals(a, odd, "Odd.");

        log.debug("Odd is OK");

        log.debug("even a: {}, even: {}", a, even);
        Assertions.assertEquals(0, even, "Even.");

        log.debug("Even is OK");
</pre>

## `HashMap.computeIfAbsent` Throws `ConcurrentModificationException` (Fail Fast) In Recursive Calls or Cross Thread Calls

REF: LC - 138 - Copy List With Random Pointer

- `HashMap.computeIfAbsent` throws `ConcurrentModificationException` --- fail fast in recursion or cross thread calls

- `ConcurrentHashMap.computeIfAbsent` throws `IllegalStateException:`

## LC -140 - Word Break II - Notes

There are three ways to find matching sub-string s from wordDtictionary: , or (2) Implement `startWith`

        // (1) LC - Use "one for loop" and substring()
        /**
         * Time: O(N ^ 2) * O(log(ROWS))
         */
        for (int end = 1, n = s.length(); end <= n; ++end) {
            String word = s.substring(0, end);
            if (wordSet.contains(word)) {
                ...
            }
        }
        
        // (2) LC - Use "two for loops"
        /**
         * Time: O(N ^ 2) * O(log(ROWS))
         */
        for (int end = 1, n = s.length(); end <= n; end++) {
            for (int start = 0; start < end; start++) {
                if (dp[start] && wordDictSet.contains(s.substring(start, end))) {
                    dp[end] = true;
                    break;
                }
            }
        }
        
        // (3) Implement `startWith` as I have doen
        /**
         * Time: O(word.length()) * O(ROWS)
         * Space: O(1)
         */
        boolean startsWith(String s, int idx, String word) {
            if (idx + word.length() > s.length()) {
                return false;
            }
            for (int i = 0; i < word.length(); i++) {
                if (s.charAt(idx + i) != word.charAt(i)) {
                    return false;
                }
            }
            return true;
        }

## When NOT to Override `hashCode()` and `equals()`?

Overriding `hashCode()` and `equals()` is not always desired. In these two cases use the default implementation (memory loation):

    (1) The list can be cyclic.
    (2) The list can have dupliacted "val".

## `Character.isDigit(ch)`

There is `Character.isDigit(c)` to check an individual char. There is no built in JDK utils to check that.

Java 8 IntStream solution:

    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

## Java Regex

<pre>
Predefined character classes:

.   Any character (may or may not match line terminators)
\d  A digit: [0-9]
\D  A non-digit: [^0-9]
\s  A whitespace character: [ \t\n\x0B\f\r]
\S  A non-whitespace character: [^\s]
\w  A word character: [a-zA-Z_0-9]
\W  A non-word character: [^\w]
</pre>

## If No Recursion, There Is No Need of Property Variable

- A property variable might be needed for max/min values of backtracks with recursion. But `property variable` makes the class `stateful`. `AtomicInteger` or `Mutable Objects`
  can be created inside the `caller` and pass that AtomicInteger or Object to recursive function as a parameter to avoid making the class `stateful`.

- The `AtomicInteger` or `Mutable Objects` may **only** be created in `caller`, and may **NOT** be created inside `recursion`. Otherwise, `AtomicInteger` or `Mutable Objects`
  be just temporary variable.

- If no recursion, there is no need of a property variable. A local variable should be enough.
  
- Use AtomicInteger in caller to keep track of shared values. This trick can be used to avoid using property variables for recursion.

## **RadixSort** Only Applies For **Positive Numbers**

- RadixSort **ONLY** applies to positive numbers.
- If min of the array is less then 0, add (-min) to all elements to make the array positive. Then do Radix Sort. Then take out that (-min) from all elements.
- There is **no way** to improve **divisor** to more than 10. **divisor** MUST starts with `1`. Then multiple by `10` for every loop.

REF: `leetcode\src\main\java\com\learn\other\ContainsDuplicateIII.java`:

        /**
         * Trick: Make all elements to positive
         */
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        ...

        /**
         * Trick: quotient MUST starts with max
         */
        int quotient = max;
        /**
         * Trick: divisor MUST starts with 1.
         */
        int divisor = 1;
        ...
                /**
                 * Trick: int remainder = tmpNums[i] / divisor % 10;
                 */
                int remainder = tmpNums[i] / divisor % 10;
                ...
            /**
             * Trick: User AtomicInteger to increment pos inside lambda
             */
            AtomicInteger pos = new AtomicInteger();
        ...
            /**
             * Trick
             */
            map.clear();

            /**
             * Trick
             */
            quotient /= 10;
            divisor *= 10;
        ...
        /**
         * Trick: Recover nagetive values
         */

LC - 162 - Max Gap: Radix Sort (Positive Integers)

## `Integer.valueOf()` vs `Integer.parseInt()`

- `Integer.valueOf()` returns an `Integer` object, while `Integer.parseInt()` returns an `int` primitive.
- `valueOf` uses `parseInt` internally

## `Function` vs `BiFunction` in Java 8

The `Function interface` is a **pre-defined** functional interface that can be used as an assignment target for a lambda expression or method reference.
It takes a **single parameter** and returns result by calling the apply() method. While the `BiFunction interface` is also a **pre-defined**
functional interface that takes **two parameters** and returns a result. It is similar to the Function interface except it takes two parameters.

<pre>

<b>Syntax</b>

@FunctionalInterface
public interface Function<T, R>

@FunctionalInterface
public interface BiFunction<T, U, R>
</pre>

Example:

    @Test
    public void testFunc() {
        Function<Integer, Integer> printNumber = a -> a * 10;
        log.debug("The number is: {}", printNumber.apply(10));

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        log.debug("The addition of two numbers are: {}", add.apply(3, 2));
    }

## `computeIfAbsent` and `computeIfPresent` and `putIfAbsent` and `merge` of `Map`

1. They can **NOT** be used in recursions.
2. They can **NOT** be used by multi-threads.
3. `map.computeIfAbsent` does **NOT** add the key/value.
4. `map.computeIfPresent` **DOES** update the value.
5. `map.merge(key, newValue, (oldValue, newValue) -> oldValue + 1);`

    // Example: LC-266 Palindrome Permutation:
        final Map<Character, Integer> map = new HashMap<>();

        for (int i = 0, len = str.length(); i < len; i++) {
            char ch = str.charAt(i);
            map.merge(ch, 1, (oldValue, newValue) -> oldValue + 1);
        }

<pre>
    @Test
    @Order(4)
    public void testComputerIfAbsent2() {
        log.debug(() -> "Start Test");

        Map<String, String> map = new HashMap<>();

        map.put("one", "The First Value");

        map.computeIfAbsent("two", key -> "The Second Value");

        /**
         * No effect, because the the key exists.
         */
        map.computeIfAbsent("one", key -> "This has no effect because the key 'one' is present.");

        map.computeIfAbsent("three", key -> "The Third Value");

        /**
         * Has effect, because it is "do it exists".
         */
        map.computeIfPresent("three", (key, val) -> key + " ---> " + val.toUpperCase());

        log.debug("Complete Test. map: {}", () -> map);
    }

    public static <T> Predicate<T> distinctByKeyClass(final Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
</pre>

## `Stream.sort()` Time Complexity

As of Java 8, the main sorting for stream sequential sorting is **TimSort**. The worst and average time complexity is `O(n log(n))`.
Best case time complexity is `O(n)`. Space complexity is `O(n)`.

## `String.join(delimitor, ...)`

- There is `String.join(delimitor, ...)` :smile:
- There is NO <s>`List.join(delimitor, ...)`</s>
- There is NO <s>`Lists.join(delimitor, ...)`</s>
- There is NO <s>`Collections.join(delimitor, ...)`</s>

## JDK Built-In Sorts

- `Arrays.sort` works on **array** and `Collections.sort` works on **Lists**.
- `Collections.sort` **converts Lists into arrays** then calls `Arrays.sort`.
- `Arrays.sort` has **two** different sorting algorithms. `Quicksort`, a non-stable algorithm, and `Timsort`, a stable algorithm.
- Both `QucikSort` and `TimeSort` share a time complexity of `O(n log n)`. `QuickSort`: space `O(log N)`, worst: `O(N ^ 2)`, not stable.
  `TimSort`: space `O(N)`, worst `O(n log N)`, space `O(n)`, stable.
- Including the comparator is `O(n * (log n) * c`, where `c` is the time complexity of the comparator.
- `Arrays.sort` **determines** which sorting algorithm to use based on the **type of parameter**. `Quicksort` for **primitive** data types and `Timsort` for **objects**.
- A **stable** sorting algorithm means items of **equal value stay in the same order** after sorting.

## SQL Interview Q/A Programmerinterview.com

[SQL Interview Q/A]<https://www.programmerinterview.com/database-sql/find-nth-highest-salary-sql/>

## `stack.add(null)` vs <s>`queue.add(null)`</s>

1. `LinkedList` allows `queue.add(null)`. `ConcurrentLinkedDeque` and `LinkedBlockingQueue` does not allow `queue.add(null)`.
2. `queue.add(null)` is a runtime exception, not a compile time exception.
3. `stack.add(null)` is safe.

## Use `Queue<TreeNdoe>` for `TreeNode` **`BFS/Iterative`**. Do NOT use <s>`Stack<TreeNode>`</s>

## Use `Stack<Param>` for `Param` **`Recursion to Iteration Conversion`** to Store Function Params

## `BFS` Trick

Sample: `src\main\java\com\learn\lc75\NearestExitFromEntrance.java` LC-1926

**Important**: Mark the cell `Visited` **immediately** the cell is enqued.

## `BFS` vs `DFS`

- `BFS` is for **Shortest Path/Distance**.
- `DFS` is for find **ALL possible** combinations.
- `BFS` tends to hold more memory in its `Queue` or `PriorityQueue`.
- `BFS` is iterative, not recursive.
- `DFS` is recursive. It holds memory for each level of recusion.

<table>
<tr><th></th><th>BFS</th><th>DFS</th></tr>

<tr><td>Data Structure</td><td>Queue or PriorityQueue</td><td>Stack</td></tr>
<tr><td>Loop</td><td>No infinite loop</td><td>Infinite loop is possible</td></tr>
<tr><td>Memory</td><td>More</td><td>Less</td></tr>
<tr><td>Space Complexity</td><td>Space complexity is more critical as compared to time complexity</td>
    <td>Lesser space complexity because it stores only one path at a time</td></tr>
<tr><td>Backtracking</td><td>No need of backtracking.</td>
    <td>Recursive algorithm that uses backtracking</td></tr>
<tr><td>Suitable For</td><td>Shortest path</td><td>Destination is far from source</td></tr>
<tr><td>Suitable for Decision?</td><td>No. Considers all neighbors first and not suitable for decision-making trees</td>
    <td>Yes. More suitable for decision-making trees in game or puzzle problems</td></tr>
<tr><td>Siutable for A.I.?</td><td>No. Although BFS is complete and optimal, it takes more memory</td><td>Yes. Work horse for A.I. due to less memory usage</td></tr>
<tr><td>Limit Depth</td><td>No need</td><td>When a depth limit is reached, return</td></tr>
</table>

## Special To Bash

### 1. Pattern multiple char matching with a backslash `\` before `{` and `}`

`grep file.txt -w "[a]\{3\}"`   <--- NOT <s>`grep file.txt -w "[a]{3}"`</s>

### 2. Space char matters

`if [ "${i}" -eq "0" ]`   <--- NOT <s>`if[ "${i}" -eq "0" ]`</s> NOR <s>`if ["${i}" -eq "0"]`</s>

## `LinkedHashMap.removeEldestEntry()`

        final int MAX = 10;
        LinkedHashMap<Integer, String> linkedHashMap =
        new LinkedHashMap<Integer, String>() {
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest)
            {
                return size() > MAX;
            }
        };

## Initialize `Map` With Strings

    Map<String, String> doubleBraceMap  = new HashMap<String, String>() {{
        put("key1", "value1");
        put("key2", "value2");
    }};

    Map<String, String> emptyMap = Map.of();
    Map<String, String> singletonMap = Map.of("key1", "value");
    Map<String, String> map = Map.of("key1","value1", "key2", "value2");

    Map<String, String> map = Stream.of(new String[][] { 
        { "Hello", "World" }, 
        { "John", "Doe" },
    }).collect(Collectors.toMap());

## `HashMap` Time Complexities

- On avaerage, `HashMap` insertion, deletion, the search takes `O(1)` constant time.
- In the worst case, `HashMap` takes `O(n)` time to search, insert, and delete.

## Fill Array

    // Makes all elements of a[] equal to "val"
    // public static void fill(int[] a, int val)
    Arrays.fill(ar, 10);
    
    // Makes elements from from_Index (inclusive) to to_Index
    // (exclusive) equal to "val"
    // public static void fill(int[] a, int from_Index, int to_Index, int val)
    Arrays.fill(ar, 1, 5, 10);

## Shifts `<<`, `>>`, and `>>>`. There is no <s>`<<<`</s>

1. There is no <s>`<<<`</s>
2. `<< s` - Left shift s bits
3. `>> s` - **signed shift** `n >> s` is `n` right-shifted `s` bit positions with sign-extension.
4. `>>> s` - **unsigned shift** `n >>> s` is `n` right-shifted `s` bit positions with zero-extension.
5. `>>` is arithmetic shift right, `>>>` is logical shift right.

## `char` Default Value is `Character.MIN_VALUE`

    char[] chs = new char[1];
    Assertions.assertTrue(chs[0] == Character.MIN_VALUE);

[Primative Default Values]<https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html>

## `Map.getOrDefault(key, default)` Does **NOT** Put Default Into The Map

1. `Map.getOrDefault(key, default)` - Does **NOT** put default into map. You have to put the key/default explicitly!
2. `map.put(key, default)` - **Must** be called explictly: `map.put(key, map.getOrDefault(key, default) + 1 /** count */)`

## `String.charAt(i)` vs `String.toCharacterArray()`

    /** Time: 681,400 ns */
    for (char c : s.toCharArray()) {
        tmp += c;
    }
    
    /** Time: 518,200 ns */
    char[] chars = s.toCharArray();
    for (int i = 0, n = chars.length; i < n; i++) {
        tmp += chars[i];
    }
    
    /** Time: 1,422,200 ns */
    for (int i = 0, n = s.length(); i < n; i++) {
        tmp += s.charAt(i);
    }

## Format Numbers `String.format("%,.02f", myFloat)` and `String.format("%,d", st)` and `String.format("%8s", Integer.toBinaryString(n)).replaceAll(" ", "0")`

1. `String.format("%,.02f", myFloat)` 1,234,567,936.00
2. `String.format("%,d", st)` 1,413,300
3. `String.format("%8s", Integer.toBinaryString(n)).replaceAll(" ", "0");` 0000 0001

## Remove Duplicates from `List`

    HelloWork/src/test/java/com/learn/java8/RemoveDuplicates.java

## `PriorityQueue.add()` and `PriorityQueue.poll()` Time Complexity

`PriorityQueue.add()` Time: `O(log(N))`.
`PriorityQueue.poll()` Time: `O(1)` because the smallest element is always the first element.
`PriorityQueue` has the smallest element as root. `poll()` is guareetined to return the smallest element.
`PriorityQueue` does **NOT** guareetine the order of data. It only guareentines the `poll()` returns the smallest element.

## `Collections.copyOf()` vs `Collectors.toList(), toSet()` vs [`new HashSet<>(set)`, `New ArrayList<>(set or list)`]

1. `Collections.copyOf()` and `Collections.of()` - Immutable
2. `Collectors.toList(), toSet()` - Mutable
3. `new HashSet<>(set)` and `new ArrayList<>(set or list)` - Mutable
4. **Unmodifiable** elements cannot be added or removed. Calling any mutator method on the Set will always cause `UnsupportedOperationException`. However,
   if the contained elements are themselves mutable, this may cause the Collection to behave inconsistently or its contents to appear to change.
5. **Unmodifiable** disallows `null` elements. Attempts to create them with null elements result in `NullPointerException`.

## A **KEY TRICK** To Avoid Redundancy In **BACKTRACK** Combination

REF: LC - LC - 216 - Combination Sum III

The **KEY TRICK** is that:

1. We **pick** the candidates **in order**.
2. We treat the candidate digits as a **list/array with order (sorted or not sorted)**, i.e. [1, 2, 3, 4, 5, 6, 7, 8. 9].
3. At **ANY** given step, **ONCE** we pick a digit, e.g. 6, we will **NOT consider** any digits **before the chosen digit**
   for the **following steps**, e.g. **the candidates are reduced down** to [7, 8, 9].

## **`backtracking`** Algorithm (also `DFS`)

**`backtracking`** (also `DFS`) is a general algorithm for finding **all (or some) solutions** to some computational problems. The idea is that it
**incrementally** builds candidates to the solutions, and **abandons** a candidate ("backtrack") as soon as it determines that this
candidate cannot lead to a final solution.

## Use `LinkedList` for partial solution, and `LinkedList.removeLast()` to Recover the Partial Result

## **`backtrack`** **KEY TRICKS**

1. Use **`backtrack`** to find **ALL (or some)** Solutions
2. **Pick** the candidates **in order** to avoid redundancy.
3. Use `LinkedList` for partial solution, so that `LinkedList.removeLast()` can be used to Recover the Partial Result.
4. To recover partial result from `ArrayList`, use `arrayList.remove(Integer.valueOf(candidates[i]));`
5. If call `arrayList.remove(candidates[i]);`, `arrayList.remove(int index);` will be invoked, and the result will be wrong.
6. `list.remove(int index);` and `list.remove(Object obj);` are different.
7. This is **WRONG**: `list.remove(nums[i]);` <-- This invokes `list.remove(int index);`
8. This is **CORRECT**: `list.remove(Integer.valueOf(nums[i]));` <-- This invokes `list.remove(Object obj);`
9. An important detail on choosing the next number for the combination is that we select the candidates **in order**, where the total
   candidates are treated as a list. Once a candidate is added into the current combination, we will **not look back** to all the previous
   candidates in the next explorations.
10. Do `Add()/removeLast()` in the **same block** of code.

## **`backtrack`** **Optimozation**

- If the array is **ordered**, break out earlier if sum exceeds target. See example "Combination Sum II (LC-40) `combinationSum2LukeOptimized()`"
- Use `LinkedHashSet` or `LinkedHashMap` to keep record adding sequence.

## Octal

A leading `0` denotes an octal numeric value so the value `010` can be decoded thus:

`010 = 1 * 81 + 0 * 80 = 8`

An octal numeral consists of an ASCII digit `0` followed by one or more of the ASCII digits `0` through `7` interspersed with underscores,
and can represent a positive, zero, or negative integer.

## Java 14 `record` Keyword

[Java 14 `record` Keyword]<https://www.baeldung.com/java-record-keyword>

- Records are **immutable** data classes that require only the type and name of fields.
- An **all-args** public constructor is generated be default.
- Default `equals()` returns true if the supplied record is of the same type and the values of all of its fields match
- Default `hashCode()` returns the same value for two record objects if all of the field values for both records match
- You **RARELY** needs to override `equals` and `hashCode` for record. You **ONLY needs** to override these two methods `IFF` (IF and Only IF) **not all, but part** of
  the fields are needed for comparison.

To create a Person record, we'll use the record keyword:

    public record Person (String name, String address) {}

## Heap

- Left child: `2 * i + 1`
- Right child: `2 * i + 2`
- Parent: `(i - 1) / 2`

## TimSort: Runs + Insertion Sort in each run + Merge

## Tree Traversal

[Iterative | Recursive | DFS & BFS | In, Pre, Post & LevelOrder | Views]<https://leetcode.com/discuss/general-discussion/937307/iterative-recursive-dfs-bfs-tree-traversal-in-pre-post-levelorder-views>

[Recursive DFS, Iterative DFS and BFS]<https://leetcode.com/problems/serialize-and-deserialize-binary-tree/solutions/74260/recursive-dfs-iterative-dfs-and-bfs/>

## Grapgic Algorithms

[Graph Algorithms One Place | Dijkstra | Bellman Ford | Floyd Warshall | Prims | Kruskals | DSU]<https://leetcode.com/discuss/general-discussion/969327/Graph-Algorithms-One-Place-or-Dijkstra-or-Bellman-Ford-or-Floyd-Warshall-or-Prims-or-Kruskals-or-DSU>

## Grind 75 Questions

[Grind 75 Questions]<https://www.techinterviewhandbook.org/grind75>

[Leetcode Cheatsheet - Pirate King]<https://www.piratekingdom.com/leetcode/cheat-sheet>

[Amazon Behavior Interview Questions, Methods, and Prep]<https://igotanoffer.com/blogs/tech/amazon-behavioral-interview#questions>

## Anti-Patterns

1. Spaghetti Code
2. Golden Hammer
3. Boat Anchor
4. Dead Code
5. God Object and God Class
6. Copy and Paste Programming

## Base64 Encode/Decode

1. Simple − Output is mapped to a set of characters lying in [A-Za-z0-9+/]. The encoder does not add any line feed in output,
   and the decoder rejects any character other than [A-Za-z0-9+/].
2. URL − Output is mapped to set of characters lying in [A-Za-z0-9+_]. Output is URL and filename safe.
3. MIME − Output is mapped to MIME friendly format. Output is represented in lines of no more than **76** characters each, and uses a carriage
   return `'\r'` followed by a linefeed `'\n'` as the line separator. No line separator is present to the end of the encoded output.
4. MIME - `static Base64.Encoder getMimeEncoder(int lineLength, byte[] lineSeparator);` lineLength and lineSeparator are configurable.

## Java 5 Covariant Return Type

Before Java5, it was not possible to override any method by changing the return type. But now, since Java5, it is possible to override method by
changing the return type if subclass overrides any method whose return type is **Non-Primitive** but it changes its **return type to subclass type**.

Java **does not allow the return type-based overloading**, but **JVM always allows return type-based overloading**. JVM uses the **full signature**
of a method for lookup/resolution. Full signature means it includes return type in addition to argument types. That is, a class can have two or more
methods differing only by return type. javac uses this fact to implement covariant return types.

Example:

    @Log4j2
    class A1 {
        A1 foo() {
            return this;
        }
    
        void print() {
            log.debug("Inside the class A1");
        }
    }
    
    
    /**
     * A2 is the child class of A1
     */
    @Log4j2
    class A2 extends A1 {
        // A2 must be SUB-TYPE of A1
        @Override
        A2 foo() {
            return this;
        }
    
        void print() {
            log.debug("Inside the class A2");
        }
    }

## Java 8 Streams

1. Stream does not store elements. It simply conveys elements from a source such as a data structure, an array, or an I/O channel,
   through a pipeline of computational operations.
2. Stream is functional in nature. Operations performed on a stream does not modify its source. For example, filtering a Stream obtained
   from a collection produces a new Stream without the filtered elements, rather than removing elements from the source collection.
3. Stream is lazy and evaluates code only when required.
4. Stream cannot be re-used. Like an Iterator, a new stream must be generated to revisit the same elements of the source.

## Java 8 Stream Pipeline

A chain of the

1. source
2. operations (Lazy. Only be invoked with terminator. No terminator, no invokation.)
3. terminator

Streams are pull-based. Only a terminal operations (like the collect) will cause items to be consumed.

Conceptually this means that collect will ask an item from the limit, limit from the map and map from the filter, and filter from the stream.

## Java Method References

1. Reference to a static method: `ContainingClass::staticMethodName`
2. Reference to an instance method: `containingObject::instanceMethodName`
3. Reference to a constructor: `ClassName::new`

## Java Stack vs Heap Memory

    # Display all options:
    java -X

    # The Java thread stack size specified is too small. Specify at least 84k
    java -Xss1k -version
    
    # The specified size exceeds the maximum representable size (Usually, 1g is max)
    java -Xss2g -version
    
    # `java -XX:ThreadStackSize=2G` does the same thing as `java -Xss2g`, but in different format (with equal sign =)
    # intx ThreadStackSize=2147483648 is outside the allowed range [ 0 ... 1048576 ]
    # Improperly specified VM option 'ThreadStackSize=2G'
    java -XX:ThreadStackSize=2G -version

    # -Xmn<size>  set the initial and maximum size (in bytes) of the heap for the young generation (nursery)
    # -Xms<size>  set initial Java heap size
    # -Xmx<size>  set maximum Java heap size
    java -Xms1g -Xmx2g -version

    # Show all settings
    java -XshowSettings:all

## Java Heap Data Structure

A heap is a tree-based data structure:

1. Min Heap
2. Max Heap
3. Binary Heap

## Use `Arrays.fill(dp, Integer.MAX_VALUE / 2);` to Prevent Integer add Overflow

Sample code: `src/main/java/com/learn/other/PerfectSquares.java`

## Trick: [`int add` overflow prevention]<https://en.wikipedia.org/wiki/Binary_search_algorithm#Implementation_issues>

    /**
     * Trick: "int add" overflow prevention:
     */
    int mid = left + (right - left) / 2;

## Trick: Sorted Array or List --> Binary Search

## Binary Search Trick: Two Elements Only Array

(Binary Search Trick: Two Elements Only Array)<https://stackoverflow.com/questions/48109050/binary-search-with-2-elements>

My Solution: example `src\main\java\com\learn\lc75\KokoEatingBananas.java` LC-875. Koko Eating Bananas

    Use `lastTestedMid`:

        /**
          * Trick 1: if(mid == left), there will never be a change to test the second value.
          */
        int lastTestedMid = 0;

        while ((hours = minEatingSpeedBacktrackHours(piles, h, mid)) != h) {
            lastTestedMid = mid;

            if (hours > h) {
                left = mid;
            } else {
                right = mid;
            }

            mid = left + (right - left) / 2;

            /**
             * Trick 2: if(mid == left), there will never be a change to test the second value.
             */
            if (lastTestedMid == mid) {
                mid = right;
            }
        }

    /**
     * Trick 3: Use `left = mid + 1;`
     */
    left = mid + 1;

Sample code: `src\main\java\com\learn\lc75\KokoEatingBananas.java` LC-875. Koko Eating Bananas

- Trick 1: Use `left = mid + 1`
- Trick 2: Use `if (hours <= h) {` to enable further seek for min solution.
- Trick 3: Never use `while (left <= right) {`. Always use `while (left < right) {`
- Trick 4: Use `return right;` to converge.

### Modifying the input collection in any way is **bad** design

When implementing an Iterator as in LC-251, one of the main purposes of an Iterator is to minimize the use of auxiliary space.
We should try to utilize the existing data structure as much as possible, only adding as much extra space as needed to keep
track of the next value. In some situations, the data structure we want to iterate over is too large to even fit in memory anyway
(think of file systems).

## Power of Two Bit Ops

To get/isolate the right most 1 bit: `x ^ (-x)`

To turn off (set to 0) the right most 1 bit: `x ^ (x - 1)`

`a ^ a = 0`

## How to Reverse String

    return new StringBuilder(str).reverse().toString();

## Graph Coloring to Detect Cycle

One issue we need to be careful of is cycles. In directed graphs, we often detect cycles by using **graph coloring**.
All nodes start as **white**, and then once they're first visited they become **grey**, and then once all their
outgoing nodes have been fully explored, they become **black**.

## `spring-boot-devtools` Might no Work for Profile Base Project. Run `mvn clean package`

Sample project: `my-properties-boot`

## Int and Bytes Conversions

Ref: `src/test/java/com/learn/test/IntAndBytesConversionTest.java`

## `String.format`

    Prints octal numbers with a leading "0" and hex numbers with leading "0x"
    String.format("|%#o|", 93);    // prints: 0135
    String.format("|%#x|", 93);    // prints: 0x5d
    String.format("|%#X|", 93);    // prints: 0X5D

## **array** vs **record** Performance

**array** is much faster than **record/Map** for TopDowm memo (25 times faster)

Sample code: `com.learn.dp.BestTimeToBuyAndSellStockWithCooldown.java`

## `Math.max()` vs `Collections.max(List.of(...))` Performance

`Math.max()` is faster than `Collections.max(List.of(...))`

## `for(int num : nums)` vs `for(int i=0; i<nums.length; i++) { int num=nums[i]; }`

- `for(int num : nums)` can cause log4j2 out of sync
- `for(int i=0; i<nums.length; i++) { int num=nums[i]; }` keeps log4j2 in sync
- It means `for(int num : nums)` is fater than `for(int i=0; i<nums.length; i++) { int num=nums[i]; }`

## `mvn test -Dtest=StreamTest#testStreamExecuteOrder` vs `m test -Dtest=StreamTest#testStreamExecuteOrder`

1. `mvn test -Dtest=StreamTest#testStreamExecuteOrder` will run tests
2. `m test -Dtest=StreamTest#testStreamExecuteOrder` will SKIP tests. It will not run tests because of `alias m='mvn -Dsurefire.useFile=false -Dmaven.test.skip=true'`

## `src\main\resources\log4j2.xml` vs `src\test\resources\log4j2-test.xml`

1. `src\main\resources\log4j2.xml` is the **default** for run `src\main` applications (with `public static void main(String... args)`)
2. `src\test\resources\log4j2-test.xml` is the **default** for `src\test` junit tests

## `src\main\resources\application.properties` vs `src\test\resources\application-test.properties`

1. `src\main\resources\application.properties` is the **default** for run `src\main` applications (with `public static void main(String... args)`)
2. `src\test\resources\application-test.properties` is the **default** for `src\test` junit tests
3. `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

## Divisor vs Dividend

    20 (dividend) / 4 (divisor) = 5 (quotient) --- ddd / dr = quot

## `java.lang.Math.ceil(double)`

    jshell
    double a = java.lang.Math.ceil((double) 11/5);
    > a ==> 3.0

## Slow Fast Pointers on ListNode

There are two ways to start move the pointers:

1. Start both pointers (SLOW and FAST) at head --- When FAST is at the end, SLOW is at the center. (Good to print center.)

       // odd number:
                V
       0, 1, 2, 3, 4, 5, 6
       sF
          s  F
             s     F
                s        F

       // even number:
                V  V
       0, 1, 2, 3, 4, 5, 6, 7
       sF
          s  F
             s     F
                s        F

2. Start SLOW at index 0, and FAST at index 2 --- When FAST is at the end, SLOW is one node prior to the center. (Good to remove center.)

       // odd number:
                V
       0, 1, 2, 3, 4, 5, 6
       s     F
          s        F
             s           F

       // event number:
                V  V
       0, 1, 2, 3, 4, 5, 6, 7
       s     F
          s        F
             s           F

## Trick for Binary Search

    `src\test\java\com\learn\test\amzn2024\Solutions.java`:

    /**
     * Trick: Make min 1 larger than last guess, or max 1 smaller than last guess.
     */
    if (can) {
        min = guess + 1;
    } else {
        max = guess - 1;
    }

## Trick: Use `TreeSet::ceiling()` and `TreeSet::floor()`

    `src\test\java\com\learn\test\amzn2024\Solutions.java` LC-2055 line: 833

        /**
         * Trick: Use `TreeSet::ceiling()` and `TreeSet::floor()`
         *
         * Time: O(log(candles.size()))
         */
        Integer ceiling = candles.ceiling(left);
        Integer floor = candles.floor(right);

## Time Complexity of `String::hashCode()` and `Object::hashCode()`

1. Time complexity is the `O(n)` for `String::hashCode()`. After one pass, it is cached so the time complexity is effectively O(1).
2. Time complexity is the `O(1)` for `Object::hashCode()`, because it has no any interaction with the data of your object. It is
   a native method and written with C language. The integer value which is returned, is probably heap memory address with some
   modifications (bitwise operations) since every address in memory representing unique value.

## Will the Java compiler optimize out `String.length()` in a for-loop's condition?

In Java (and in .Net), strings are length counted (number of UTF-16 code points), so finding the length is a simple operation.
The compiler (javac) may or may not perform hoisting, but the JVM JIT Compiler will almost certainly inline the call to
`.length()`, making `String::length()` nothing more than a memory access.

The Java compiler (javac) performs no such optimization. The JIT compiler will likely inline the length() method,
which at the very least would avoid the overhead of a method call.

However, that sort of thing is an implementation detail. Unless you control every machine that your code will run on, you
shouldn't make too many any assumptions about which JVM it will run on, or which optimizations it will perform.

## Hint for Dynamic programming LC-1531 Editorial

- "find the minimum length of the run-length encoded version of s after deleting at most k characters," which means that this is
  an optimization problem.
- Whenever a problem asks us to `minimize` or `maximize` a result based on a given set of rules, we should always include dynamic programming
  in our list of approaches to consider.

- There are a few more characteristics of this problem that hint to us that DP is a viable first approach.
- If we consider building the string one character at a time, (1) for each character we will have a choice, we can either keep it or delete it,
  and (2) we need to choose the optimal action. Each of these choices will lead us to another subproblem.
  
- Furthermore, whether we are allowed to delete a character depends on if we have already deleted k characters, so our current options
  are affected by our past decisions. These are both characteristics of problems that can be solved using dynamic programming.

## Dynamic Programming

1. States
2. Base Cases
3. Recursion

## `Red Hat Dependency Analytics` Disabled

    # this extension causes the following call:
    mvn -q help:effective-pom -Doutput=effective-pom.xml -f pom.xml
    # Or
    (with absolute path) mvn -q help:effective-pom -Doutput=effective-pom.xml -f pom.xml

## `Arrays.binarySearch()`

1. The array must be pre-sorted.
2. Returns: the index of the search key, if it is contained in the list; otherwise, negation of `insertion point when index starts with 1`.
3. If not found, it returns a negative number. The absolute value of the negative number is the `insertion index with first index starts with 1.`
4. Therefore, the `Math.abs(negative number) - 1` is the insertion index when index starts with 0.

    @Test
    public void testBinarySearchPerson() {
        record Person(String name, int age) {
        }

        Person[] persons = { new Person("0a", 0),
                new Person("1b", 1),
                new Person("2c", 3),
                new Person("3d", 3),
        };

        var index = Arrays.binarySearch(persons, new Person("new", 2), (a, b) -> a.age - b.age);

        if (index < 0) {
            index = -(index + 1);
        }

        log.debug("index: {}", index);

    }

## `Collections.binarySearch()`

1. The collection must be pre-sorted.
2. Returns: the index of the search key, if it is contained in the list; otherwise, it return the negation of the
   `insertion index with first index starts with 1.`
3. Therefore, the `Math.abs(negative number) - 1` is the insertion index when index starts with 0.

    static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key)
    static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c)

## `Collections.reverse()`

## `Arrays` has `copyOf()`

## `Collections` has `addAll()`

## `Collections.addAll(collection, ...[])` and `Collection.addAll(collection)`

## `Collection` is super interface of

    BeanContext, BeanContextServices,
    BlockingDeque<E>, BlockingQueue<E>, Deque<E>, List<E>, NavigableSet<E>, Queue<E>, Set<E>, SortedSet<E>, TransferQueue<E>

## `new ArrayList<>(collection);`

## `Queue<T>` is interface of

    BlockingDeque<E>, BlockingQueue<E>, Deque<E>, TransferQueue<E>

    AbstractQueue, ArrayBlockingQueue, ArrayDeque, ConcurrentLinkedDeque, ConcurrentLinkedQueue,
    DelayQueue, LinkedBlockingDeque, LinkedBlockingQueue, LinkedList, LinkedTransferQueue,
    PriorityBlockingQueue, PriorityQueue, SynchronousQueue

    Queue<T> queue = new LinkedList<>();

    Deque<T> deque = new LinkedList<>();

## `Stack<T>` is a class, not an interface

    Stack<T> stack = new Stack<>();

## `Arrays` has `sort(T[] a, Comparator<? super T> c)`

## `Array` does **NOT** have `sort(T[] a, Comparator<? super T> c)`

## `Array` is a small object. It does not have much utility classes

## `[].clone()` vs `[][].clone()`

1. `[].clone()` is good for 1-d array.
2. `[][].clone()` is not good for 2-d array. change one array will affact another array.

## `Array` vs `Arrays`

- `Array`: This class can be used to **create array** in run time using **reflection**.
- `Arrays`: Utility class, which contains static methods to manipulate(sort,max,min etc.) the values stored in array.

## Space complexity when modify input (bad practice)

- It is generally considered a bad practice to modify the input.
- When you do, you should count it as part of the space complexity.

## `Collection` vs `Collections`

- `Collection` is simply an **interface**. It is able to form the root or head of the hierarchy of interfaces in the java collection framework.
- `Collections` is a **utility class**. It uses static methods (utility) for computations.

## `Collector` vs `Collectors`

- `Collector` is an **interface**.
- `Collectors` implements `Collector`. It is a **utility class** with many static memober functions to create different collectors.

## `Map` itself does not have `stream()`. But `Map.forEach((k, v) -> {})` works as stream

## `TreeSet.floor(E e)` --- floor of curr --- self or the most close element to self from `left`

- Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
- Meaning returns the equal element, or greatest (from left) element that is less than param, or `null` if none.

## `TreeSet.ceiling(E e)` --- ceiling of curr --- self or the most close element to self from `right`

- Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
- Meaning returns the equal element, or smallest (from right) element that is greater than param, or `null` if none.

## Trick: Cannot do this: `Arrays.fill(todo, new ArrayList<>());`

    /**
     * Trick: Cannot do this: `Arrays.fill(todo, new ArrayList<>());`. This will create a one List with all instances point to the same reference.
     */
    // Arrays.fill(todo, new ArrayList<>());
    for (int i = 0; i < 26; i++) {
        todo[i] = new ArrayList<>();
    }

## Sorting a string based on another string

REF: `src\test\java\com\learn\test\amzn2024\Solution.java`

    public String customSortStringSorting(String order, String s) {
      Character[] chs = new Character[s.length()];

       int idx = 0;
       for (char ch : s.toCharArray()) {
           chs[idx++] = ch;
       }

       /**
        * Time: O(s.length() * (log(s.length()) * order.length()))
        */
       Arrays.sort(chs, (a, b) -> order.indexOf(a) - order.indexOf(b));

## `str.toCharArray();` creates a new array of chars and return the the array (**deep copy**)

`str.toCharArray();` - returns a **deep copy** of char array.

## Does java `substring()` take extra space?

- In older versions of Java (prior to Java 7 Update 6), `String.substring()` did not allocate new space, but instead, it created a new
  String object that shared the character array of the original string. This meant that the substring operation itself was very
  efficient in terms of space and time complexity, as it only involved creating a new String object with different start and end
  indices. However, it could lead to memory leaks if the original string was very large and only a small substring was needed,
  because the entire character array of the original string would remain in memory as long as the substring was referenced.
- Since Java 7 Update 6, `String.substring()` creates a new character array and copies the relevant portion of the original string into it.
  This change was made to avoid the memory leak issue. Therefore, in modern Java, `String.substring()` does take extra space,
  proportional to the length of the substring. The space complexity is `O(n)`, where n is the length of the substring.
