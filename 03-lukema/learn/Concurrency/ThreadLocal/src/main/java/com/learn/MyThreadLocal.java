package com.learn;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadLocal {
	private static final AtomicInteger uniqueId = new AtomicInteger(0);

	private static final ThreadLocal<Integer> idGenerator = new ThreadLocal<Integer>() {
		protected synchronized Integer initialValue() {
			return new Integer(uniqueId.incrementAndGet());
		}
	};

	private static final ThreadLocal<Pojo> pojoThreadLocal = new ThreadLocal<Pojo>() {
		protected synchronized Pojo initialValue() {
			return new Pojo();
		}
	};

	public static Integer generateId() {
		Integer id = getIdGenerator().get();

		return id;
	}

	private static ThreadLocal<Integer> getIdGenerator() {
		return idGenerator;
	}

	public static ThreadLocal<Pojo> getPojothreadlocal() {
		return pojoThreadLocal;
	}
}
