package com.revature.bank.exceptions;

@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {

	public InvalidRequestException() {
		super("Inavlid data");
	}

	public InvalidRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
