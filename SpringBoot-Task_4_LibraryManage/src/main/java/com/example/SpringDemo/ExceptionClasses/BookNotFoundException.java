package com.example.SpringDemo.ExceptionClasses;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(String msg) {
		super(msg);
	}
}
