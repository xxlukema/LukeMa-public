package com.learn.jpmc.kunhacker2;


import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class LogUtil {

    private static final Executor EXECUTOR = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static void writeAuditLog(String msg, String userId) {
        // slow process: 1-500 milliseconds
    }

    public static void auditLog(String msg, String userId) {
        // It's better to use executor api with control of pool size. It needs to refer to the executor API.

        EXECUTOR.execute(() -> writeAuditLog(msg, userId));
        
    }

}
