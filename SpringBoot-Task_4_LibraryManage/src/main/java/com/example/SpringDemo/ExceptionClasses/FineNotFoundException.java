package com.example.SpringDemo.ExceptionClasses;

public class FineNotFoundException extends RuntimeException{

	public FineNotFoundException(String msg) {
		super(msg);
	}
}
