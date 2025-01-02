package com.example.SpringDemo.ExceptionClasses;

public class PublisherNotFoundException extends RuntimeException{

	public PublisherNotFoundException(String msg) {
		super(msg);
	}
}
