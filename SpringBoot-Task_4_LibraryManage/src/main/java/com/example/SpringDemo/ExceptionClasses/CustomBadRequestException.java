package com.example.SpringDemo.ExceptionClasses;

public class CustomBadRequestException extends RuntimeException{
	
	public CustomBadRequestException(String message) {
		super(message);
	}

}
