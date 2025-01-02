package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.AuthorRequest;
import com.example.SpringDemo.ResponseDTO.AuthorResponse;

public interface AuthorService {

    AuthorResponse createAuthor(AuthorRequest authorRequest);

    // Get author by ID
    AuthorResponse getAuthorById(Integer authorId);

    // Get all authors
    List<AuthorResponse> getAllAuthors();

    // Update an author
    AuthorResponse updateAuthor(Integer authorId, AuthorRequest authorRequest);

    // Delete an author
    void deleteAuthor(Integer authorId);
}
