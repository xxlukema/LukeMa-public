package com.learn.java9;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


/**
 * Java 9 feature: Interfaces got private methods.
 */
interface MyInterface {

    @SuppressWarnings("unused")
    private static void myPrivateMethod() {
        System.out.println("Yay, I am private!");
    }
}


@Log4j2
public class Java9Test {

    @Test
    public void testMap() {

        List<String> list = List.of("one", "two", "three");
        Set<String> set = Set.of("one", "two", "three");
        Map<String, String> map = Map.of("foo", "one", "bar", "two");

        log.debug("list: {}, set: {}, map: {}", () -> list, () -> set, () -> map);
    }

    /**
     * stream takeWhile, dropWhile, iterate
     */
    @Test
    public void testStreamIterator() {

        Stream<String> stream = Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10);

        /**
         * var is java 10 feature
         */
        var str = stream.toList();

        log.debug("str: {}", () -> str);

    }

    @Test
    public void testHttpClient() {

    }

    @Test
    public void testInterfaceWithPrivateMethods() {

    }

    @Test
    public void testOptionals() {

        // user.ifPresentOrElse(this::displayAccount, this::displayLogin);

    }

    @Test
    public void testJava9() {

        log.info(() -> "Begin Test");

        // @formatter:off

        // 1. The Java Platform module system
        // 2. Linking
        // 3. JShell: the interactive Java REPL
        // 4. Improved Javadoc
        // 5. Collection factory methods
        // 6. Stream API improvements. dropWhile, takeWhile, ofNullable, iterate(1, i -> i < 100, i -> i + 1)
        // 7. Private interface methods
        
        // 8. HTTP/2
        /**
         * HTTP/2 to replace HttpURLConnection
         * One caveat: The new HttpClient API is delivered as a so-called _incubator module_ in Java 9. 
         * This means the API isn't guaranteed to be 100% final yet.
         */

        /*
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req =
           HttpRequest.newBuilder(URI.create("http://www.google.com"))
                      .header("User-Agent","Java")
                      .GET()
                      .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandler.asString());
        */

        // 9. Multi-release JARs
        /*
            multirelease.jar
            ├── META-INF
            │   └── versions
            │       └── 9
            │           └── multirelease
            │               └── Helper.class
            ├── multirelease
                ├── Helper.class
                └── Main.class
        */
        // @formatter:on

        log.info(() -> "End Test.");

    }

}
