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

import com.example.SpringDemo.RequestDTO.BookRequest;
import com.example.SpringDemo.ResponseDTO.BookResponse;
import com.example.SpringDemo.Service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
    private BookService bookService;

    // Endpoint to create a new book
    @PostMapping("/createBook")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.createBook(bookRequest);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    // Endpoint to get a book by ID
    @GetMapping("/getBookById/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Integer bookId) {
        BookResponse bookResponse = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    // Endpoint to get all books
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> bookResponses = bookService.getAllBooks();
        return new ResponseEntity<>(bookResponses, HttpStatus.OK);
    }

    // Endpoint to update an existing book
    @PutMapping("/updateBook")
    public ResponseEntity<BookResponse> updateBook(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.updateBook(bookRequest);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    // Endpoint to delete a book by ID
    @DeleteMapping("/deleteBookById/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Book deleted with id: "+bookId,HttpStatus.NO_CONTENT);
    }
}
