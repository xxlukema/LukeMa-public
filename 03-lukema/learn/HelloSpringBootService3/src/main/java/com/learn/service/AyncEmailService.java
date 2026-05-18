package com.learn.service;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class AyncEmailService {

    /**
     * 1. Only retry for for timeout.
     * 2. Exception and Throwable are added to noRetryFor to prevent retrying for all exceptions.
     */
    @Async("emailTaskExecutor")
    @Retryable(// noRetryFor = { Exception.class, Throwable.class }, 
            retryFor = { TimeoutException.class },
            maxAttempts = 3, /** must be constant */
            backoff = @Backoff(delay = 10_000) /** must be constant. 10 seconds delay between retries */
    /** listeners = {"emailRetryListener"} */
    )
    public CompletableFuture<Boolean> sendEmail(String to, String subject, String body) throws TimeoutException {
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> longTask(to, subject, body));

        try {
            future.get(5, TimeUnit.SECONDS);
            return CompletableFuture.completedFuture(false);
        } catch (TimeoutException | InterruptedException | ExecutionException _) {
            future.cancel(true);
            log.error("================= AyncEmailService - sendEmail: canceled due to timeout in 5 seconds");
            throw new TimeoutException("Email sending timed out");
        } finally {
            executor.shutdown();
        }
    }

    private CompletableFuture<Boolean> longTask(String to, String subject, String body) throws InterruptedException {
        try {
            log.info("================= AyncEmailService - sending Email: to={}, subject={}, body={}", to, subject, body);

            // Simulate email sending delay
            Thread.sleep(20_000);

            log.info("================= AyncEmailService - Email sent to {} with subject {} and body {}", to, subject, body);

            /**
             * Returning true to indicate successful email sending.
             */
            return CompletableFuture.completedFuture(true);
        } catch (InterruptedException e) {
            log.error("================= AyncEmailService - sendEmail: InterruptedException: {}", e.getMessage());
            throw e;
        }
    }

    @Recover
    public CompletableFuture<Boolean> recoverSendEmail(Exception e, String to, String subject, String body) {
        log.error(
                "================= AyncEmailService - recoverSendEmail: Failed to send email to {} with subject {} after retries. Exception: {}",
                to, subject,
                e.getMessage());

        return CompletableFuture.completedFuture(false);
    }

}
