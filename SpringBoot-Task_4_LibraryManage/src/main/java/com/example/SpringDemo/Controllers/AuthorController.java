package com.example.SpringDemo.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.RequestDTO.AuthorRequest;
import com.example.SpringDemo.ResponseDTO.AuthorResponse;
import com.example.SpringDemo.Service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

	
	@Autowired
    private AuthorService authorService;

	@PostMapping(value = "/author", consumes = "application/json")
//	@PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
//		System.out.println("enter in function");
    	try {
    		AuthorResponse authorResponse = authorService.createAuthor(authorRequest);
            return new ResponseEntity<>(authorResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.CREATED);
			// TODO: handle exception
		}
        
    }

    @GetMapping("/getAuthorById/{authorId}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Integer authorId) {
        AuthorResponse authorResponse = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }


    @GetMapping("/getAllAuthors")
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


    @PutMapping("/updateAuthor/{authorId}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Integer authorId,
                                                       @Valid @RequestBody AuthorRequest authorRequest) {
        AuthorResponse authorResponse = authorService.updateAuthor(authorId, authorRequest);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAuthorById/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer authorId) {
        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
