package com.evoke.example.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1628028892264571066L;

	public EmployeeNotFoundException(String message) {

		super(message);
	}

}