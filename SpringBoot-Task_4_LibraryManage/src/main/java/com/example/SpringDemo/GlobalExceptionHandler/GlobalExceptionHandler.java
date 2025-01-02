package com.example.SpringDemo.GlobalExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.SpringDemo.ExceptionClasses.AuthorNotFoundException;
import com.example.SpringDemo.ExceptionClasses.BookNotFoundException;
import com.example.SpringDemo.ExceptionClasses.BorrowNotFoundException;
import com.example.SpringDemo.ExceptionClasses.CustomBadRequestException;
import com.example.SpringDemo.ExceptionClasses.CustomIternalServerError;
import com.example.SpringDemo.ExceptionClasses.FineNotFoundException;
import com.example.SpringDemo.ExceptionClasses.PublisherNotFoundException;
import com.example.SpringDemo.ExceptionClasses.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> userNotFound(UserNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "User not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> bookNotFound(BookNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Book not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> fineNotFound(FineNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Fine not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> authorNotFound(AuthorNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Author not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> publisherNotFound(PublisherNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Publisher not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> borrowNotFound(BorrowNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Borrow not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleBadRequest(CustomBadRequestException exc){
	    Map<String, String> error = new HashMap<>();
	    error.put("error", "Bad Request");
	    error.put("message", exc.getMessage());
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleInternalServerError(CustomIternalServerError exc){
	    Map<String, String> error = new HashMap<>();
	   exc.printStackTrace();
	    error.put("error", "Internal Server Error");
	    error.put("message", exc.getMessage());
	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> fieldErrors = new HashMap<>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        fieldErrors.put(error.getField(), error.getDefaultMessage());
	    }
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("error", "Validation failed");
	    response.put("message", "Invalid input data");
	    response.put("details", fieldErrors);
	    
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   
	}	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map <String,String>> exceptionHandeler(Exception exc){
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Internal server Error");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
