package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.Book;


@RequestMapping("/spring/book")
@RestController
public class BookResource {

    private static final Logger LOG = LogManager.getLogger();

    private static final List<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book(1, "Core Java"));
        BOOKS.add(new Book(2, "Angular 2"));
        BOOKS.add(new Book(3, "Hibernate"));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "books", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
    public List<Book> getBooks()
        throws Exception {
        LOG.debug("Enter...");

        try {
            return BOOKS;
        } finally {
            LOG.info("Leave.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    public List<Book> addBook(@Valid @RequestBody Book book) {
        LOG.debug("Enter...");

        LOG.debug("Input: " + book.toString());

        BOOKS.add(book);

        try {
            return BOOKS;
        } finally {
            LOG.info("Leave.");
        }
    }

}
