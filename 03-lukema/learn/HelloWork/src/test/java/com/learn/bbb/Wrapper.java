package com.learn.bbb;


public class Wrapper {

    public static String getWrapper() {
        return create();
    }
    
    public static String create() {
        Throwable t = new Throwable();
        //elemement 1 in the stack trace correspond to the caller class
        StackTraceElement directCaller = t.getStackTrace()[1];
        return directCaller.getClassName();
    }

}
