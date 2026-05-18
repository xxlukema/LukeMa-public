package com.learn.exception;


import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;


/**
 * !!! Trick: To specify array of controllers to advise
 * @RestControllerAdvice(assignableTypes = { BookResource.class })
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger();

    @ExceptionHandler(AppException.class)
    public String handleAppException(HttpServletRequest request, AppException ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        return "handleGlobalException";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "JWT Token expired")
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(HttpServletRequest request, Exception ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        //returning 404 error code
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "JWT Token expired")
    @ExceptionHandler(CookieExpiredException.class)
    public ResponseEntity<String> handleCookieExpiredException(HttpServletRequest request, Exception ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        //returning 404 error code
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<String> handleRestException(HttpServletRequest request, RestException ex, WebRequest webrequest) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        // return "handleGlobalException";
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        return "handleGlobalException";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException(HttpServletRequest request, IOException ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        //returning 404 error code
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unknown Exception occured")
    @ExceptionHandler(Exception.class)
    public void handleGlobalException(HttpServletRequest request, Exception ex) {
        log.info("[{}] {}", () -> request.getMethod(), () -> request.getRequestURL());
        log.error(ex.getClass().getName(), ex);
        //returning 404 error code
    }

}
