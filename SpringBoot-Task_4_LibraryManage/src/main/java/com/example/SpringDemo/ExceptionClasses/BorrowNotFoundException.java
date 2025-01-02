package com.example.SpringDemo.ExceptionClasses;

public class BorrowNotFoundException extends RuntimeException{

	public BorrowNotFoundException (String msg) {
		super(msg);
	}
}
