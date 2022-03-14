package com.michael.microservices.exception;

public class DriverNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -267076241828239975L;

	public DriverNotFoundException(Long id) {
		super("Could not find driver with the specified id: " + id);
	}
}
