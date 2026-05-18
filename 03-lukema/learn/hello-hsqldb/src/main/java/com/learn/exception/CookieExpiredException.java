package com.learn.exception;


public class CookieExpiredException
    extends Exception {
    private static final long serialVersionUID = 1L;

    public CookieExpiredException(String msg) {
        super(msg);
    }

    public CookieExpiredException() {
        super();
    }

    public CookieExpiredException(Throwable t) {
        super(t);
    }

    public CookieExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

}
