package com.example.SpringDemo.ExceptionClasses;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message) {
		super(message);
	}
}
