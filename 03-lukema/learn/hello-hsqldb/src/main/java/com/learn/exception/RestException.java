package com.learn.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "It can be a bad request.")
public class RestException
    extends Exception {
    private static final long serialVersionUID = 1L;

    public RestException(String msg) {
        super(msg);
    }

    public RestException() {
        super();
    }

    public RestException(Throwable t) {
        super(t);
    }

    public RestException(String msg, Throwable t) {
        super(msg, t);
    }
}
