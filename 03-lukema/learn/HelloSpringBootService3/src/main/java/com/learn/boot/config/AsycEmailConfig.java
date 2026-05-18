package com.learn.boot.config;


import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.retry.RetryListener;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.log4j.Log4j2;


@Configuration
@EnableAsync
@EnableRetry
@Log4j2
public class AsycEmailConfig
    implements AsyncConfigurer {

    @Value("${async.email.corePoolSize:100}")
    private int corePoolSize;

    @Value("${async.email.maxPoolSize:50000}")
    private int maxPoolSize;

    @Value("${async.email.maxLifetimeSeconds:8}")
    private int maxLifetimeSeconds;

    private static final String THREAD_NAME_PREFIX = "AsycEmail-";

    /**
     * Set the capacity of the queue to 1 to prevent hanging taks blocking new submissions.
     */
    private static final int QUEUE_CAPACITY_ONE = 1;

    @Override
    @Bean(name = "emailTaskExecutor")
    public Executor getAsyncExecutor() {
        log.info("============== AsycEmailConfig - getAsyncExecutor: corePoolSize={}, maxPoolSize={}, maxLifetimeSeconds={}", corePoolSize,
                maxPoolSize, maxLifetimeSeconds);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(maxLifetimeSeconds);

        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setQueueCapacity(QUEUE_CAPACITY_ONE);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new EmailAsyncExceptionHandler();
    }

    @Bean(name = "emailRetryListener")
    public EmailRetryListener emailRetryListener() {
        return new EmailRetryListener();
    }

}


@Log4j2
class EmailAsyncExceptionHandler
    implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.error("================ Exception message - {}", throwable.getMessage());
        log.error("================ Method name - {}", method.getName());
        for (Object param : obj) {
            log.error("=============== Parameter value - {}", param);
        }
    }
}


@Log4j2
class EmailRetryListener
    implements RetryListener {

    public <T, E extends Throwable> void open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("============== EmailRetryListener - open: Starting retry operation. context: {}", context.toString(), callback.toString());
    }

    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback) {
        log.info("============== EmailRetryListener - close: Finished retry operation. context: {}. callback: {}", context.toString(),
                callback.toString());
    }
}

/**
 * Usage Example:
 * 
 * @Async("emailTaskExecutor")
 * public void sendEmail(...) {
 *    // email sending logic
 * }
 * 
 */
