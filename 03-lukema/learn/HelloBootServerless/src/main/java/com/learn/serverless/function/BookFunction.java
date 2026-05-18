package com.learn.serverless.function;


import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


/**
 * 1. POST is required, because GET does not support request body. Therefore, "Function" and "Consumer" all need POST methods.
 * 2. "Supplier" does not need body payload. Thereofore, "GET" is used for "Supplier". 
 */
@Configuration
@ComponentScan(basePackages = { "com.learn.serverless" })
@EnableAutoConfiguration
@Log4j2
public class BookFunction {

    private List<Book> books;

    public BookFunction() {
        // @formatter:off
        this.books = Arrays.asList(new Book(101, "Hello Serverless"), 
                new Book(202, "Spring Boot Tutorials"), 
                new Book(303, "AWS Tutorials"),
                new Book(404, "Api Gateway Tutorials")
            ).stream().collect(Collectors.toList());
        // @formatter:on
    }

    /**
     * Supplier uses GET.
     * 
     * curl -H "Content-Type: text/plain" -X GET http://localhost:8080/getBooks
     */
    @Bean
    public Supplier<List<Book>> getBooks() {
        // return () -> new Book(id: 101, name: "Hello Boot Serverless");
        return () -> {
            /**
             * Other logics here.
             */
            log.info(() -> "here");

            return this.books;
        };
    }

    /**
     * Function and Consumer use POST.
     * 
     * curl -H "Content-Type: text/plain" http://localhost:8080/getBooksByName -d "Api"
     */
    @Bean
    public Function<String, List<Book>> getBooksByName() {
        // return () -> new Book(id: 101, name: "Hello Boot Serverless");
        return (bookName) -> {
            /**
             * Other logics here.
             */
            log.info(() -> "here");

            return this.books.stream().filter(item -> item.getName().contains(bookName)).collect(Collectors.toList());
        };
    }

    /**
     * Function and Consumer use POST.
     * 
     * curl -H "Content-Type: text/plain" http://localhost:8080/addBook -d "Api"
     */
    @Bean
    public Consumer<String> addBook() {
        return (bookName) -> {
            log.info(bookName);

            this.books.add(new Book(199, bookName));
        };
    }

    /**
     * Function and Consumer use POST.
     * 
     * curl -H "Content-Type: text/plain" http://localhost:8080/deleteBook -d "Api"
     */
    @Bean
    public Consumer<String> deleteBook() {
        return (bookName) -> {
            log.info(bookName);

            this.books = this.books.stream().filter(item -> !item.getName().contains(bookName)).collect(Collectors.toList());
        };
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Book {
    private Integer id;
    private String name;
}
