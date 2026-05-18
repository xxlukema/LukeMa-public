package com.learn.test;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import lombok.extern.log4j.Log4j2;


@Log4j2
@TestMethodOrder(OrderAnnotation.class)
public class NullQueueStatckTest {

  @Order(1)
  @Test
  public void testNullStack() {

    log.debug(() -> "start test");

    Stack<Integer> stack = new Stack<>();

    /**
     * stack.add(null) is safe.
     */
    stack.add(null);

    log.debug("size: {}, stack: {}, stack.isEmpty(): {}", () -> stack.size(), () -> stack, () -> stack.isEmpty());

    log.debug(() -> "complete test");
  }

  @Order(2)
  @Test
  public void testNullQueue() {

    log.debug(() -> "start test");

    /**
     * (1) LinkedList allows queue.add(null). ConcurrentLinkedDeque and LinkedBlockingQueue does not allow queue.add(null).
     * (2) queue.add(null) is a runtime exception, not a compile time exception.
     * (3) stack.add(null) is safe.
     */
    // Queue<Integer> queue = new ConcurrentLinkedDeque<>();
    // Queue<Integer> queue = new LinkedBlockingQueue<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.add(null);

    log.debug("size: {}, queue: {}, queue.isEmpty(): {}", () -> queue.size(), () -> queue, () -> queue.isEmpty());

    log.debug(() -> "complete test");
  }

}
