package com.learn.util;


import java.util.function.Supplier;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class MyLog4j1Wrapper {

    private static final String FQCN = MyLog4j1Wrapper.class.getName();

    private Logger logger;

    private MyLog4j1Wrapper(Logger logger) {
        this.logger = logger;
    }

    public static MyLog4j1Wrapper getLogger() {

        Throwable t = new Throwable();
        //elemement 1 in the stack trace correspond to the caller class
        StackTraceElement directCaller = t.getStackTrace()[1];

        Logger logger = Logger.getLogger(directCaller.getClassName());
        return new MyLog4j1Wrapper(logger);
    }

    public void trace(Supplier<String> supplier) {
        log(Level.TRACE, supplier, null);
    }

    public void debug(Supplier<String> supplier) {
        log(Level.DEBUG, supplier, null);
    }

    public void trace(String msg) {
        log(Level.TRACE, msg, null);
    }

    public void debug(String msg) {
        log(Level.DEBUG, msg, null);
    }

    public void info(String msg) {
        log(Level.INFO, msg, null);
    }

    private void log(Priority level, String msg, Throwable t) {

        if (level == null) {
            return;
        } else if (level == Level.TRACE) {
            if (!logger.isTraceEnabled()) {
                return;
            }
        } else if (level == Level.DEBUG) {
            if (!logger.isDebugEnabled()) {
                return;
            }
        } else if (level == Level.INFO) {
            if (!logger.isInfoEnabled()) {
                return;
            }
        }

        logger.log(FQCN, level, msg, t);
    }

    private void log(Priority level, Supplier<String> supplier, Throwable t) {

        if (level == null) {
            return;
        } else if (level == Level.TRACE) {
            if (!logger.isTraceEnabled()) {
                return;
            }
        } else if (level == Level.DEBUG) {
            if (!logger.isDebugEnabled()) {
                return;
            }
        } else if (level == Level.INFO) {
            if (!logger.isInfoEnabled()) {
                return;
            }
        }

        if (supplier == null) {
            return;
        }

        logger.log(FQCN, level, supplier.get(), t);
    }

}
