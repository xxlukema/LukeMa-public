package com.learn.cucumber.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class BookStoreSteps {

    private List<Book> books;
    private int bookCount;

    @Given("I have the following books in the store")
    public void i_have_the_following_books_in_the_store(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

        log.debug("inside i_have_the_following_books_in_the_store()");

        books = new ArrayList<>();

        dataTable.asMaps().forEach(map -> {
            Book book = new Book(map.get("title"), map.get("author"));
            books.add(book);
        });
    }

    @When("I search for books by author Erik Larson")
    public void i_search_for_books_by_author_erik_larson() {
        this.bookCount = (int) books.stream().filter(book -> Objects.equals("Erik Larson", book.getAuthor())).count();
    }

    @Then("I find {int} books")
    public void i_find_books(Integer int1) {
        Assert.assertEquals("number of books by author", int1.intValue(), this.bookCount);
    }

    @When("I search for books by author Marcel Proust")
    public void i_search_for_books_by_author_marcel_proust() {
        this.bookCount = (int) books.stream().filter(book -> Objects.equals("Marcel Proust", book.getAuthor())).count();
    }

    @When("I search for a book titled The Lion, the Witch and the Wardrobe")
    public void i_search_for_a_book_titled_the_lion_the_witch_and_the_wardrobe() {
        this.bookCount = (int) books.stream().filter(book -> Objects.equals("The Lion, the Witch and the Wardrobe", book.getTitle())).count();
    }

    @Then("I find a book")
    public void i_find_a_book() {
        Assert.assertEquals("number of books title of Lion, ...", 1, this.bookCount);
    }

    @When("I search for a book titled Swann's Way")
    public void i_search_for_a_book_titled_swann_s_way() {
        this.bookCount = (int) books.stream().filter(book -> Objects.equals("Swann's Way", book.getTitle())).count();
    }

    @Then("I find no book")
    public void i_find_no_book() {
        Assert.assertEquals("number of books title of Swann's Way", 0, this.bookCount);
    }

}
