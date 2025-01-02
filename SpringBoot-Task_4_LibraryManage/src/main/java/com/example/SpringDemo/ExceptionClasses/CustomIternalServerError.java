package com.example.SpringDemo.ExceptionClasses;

public class CustomIternalServerError extends RuntimeException {

	public CustomIternalServerError(String message) {
		super(message);
	}

}
