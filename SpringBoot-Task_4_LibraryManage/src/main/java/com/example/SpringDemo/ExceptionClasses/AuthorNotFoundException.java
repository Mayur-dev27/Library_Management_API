package com.example.SpringDemo.ExceptionClasses;

public class AuthorNotFoundException extends RuntimeException {

	public AuthorNotFoundException(String msg) {
		super(msg);
	}
}
