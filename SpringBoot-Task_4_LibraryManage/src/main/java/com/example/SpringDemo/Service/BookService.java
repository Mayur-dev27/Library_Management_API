package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.BookRequest;
import com.example.SpringDemo.ResponseDTO.BookResponse;

public interface BookService {

    BookResponse createBook(BookRequest bookRequest);

    // Get book by ID
    BookResponse getBookById(Integer bookId);

    // Get all books
    List<BookResponse> getAllBooks();

    // Update a book
    BookResponse updateBook(BookRequest bookRequest);

    // Delete a book
    void deleteBook(Integer bookId);
}
