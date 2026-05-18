package com.broadsoft.cpbx.e911.api;

public class InvalidTNException extends Exception {

	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = -7418870776275106851L;

	public InvalidTNException() {
	}

	public InvalidTNException(String message) {
		super(message);
	}

	public InvalidTNException(Throwable cause) {
		super(cause);
	}

	public InvalidTNException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTNException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
