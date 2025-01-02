package com.example.SpringDemo.ServieImpl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Author;
import com.example.SpringDemo.Entity.Book;
import com.example.SpringDemo.ExceptionClasses.AuthorNotFoundException;
import com.example.SpringDemo.Repository.AuthorRepository;
import com.example.SpringDemo.RequestDTO.AuthorRequest;
import com.example.SpringDemo.RequestDTO.BookReqForAuthor;
import com.example.SpringDemo.ResponseDTO.AuthorResponse;
import com.example.SpringDemo.Service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;



    @Override
    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        // Step 1: Manually map AuthorRequest to Author entity
        Author author = new Author();
        author.setAuthorName(authorRequest.getAuthorName());

        // Step 2: Handle the books list (manual mapping)
        if (authorRequest.getBooks() != null) {
            List<Book> books = new ArrayList<>();

            for (BookReqForAuthor bookRequest : authorRequest.getBooks()) {
                // Step 2.1: Manually map BookRequest to Book entity
                Book book = new Book();
                book.setTitle(bookRequest.getTitle());
                book.setAvailableCopies(bookRequest.getAvailableCopies());

                
                book.setAuthor(author);
                
                // Step 2.2: Add the mapped Book to the list
                books.add(book);
            }

            // Step 3: Associate the list of books with the author
            author.setBooks(books);
        }

        // Step 4: Save the author and associated books to the repository
        author = authorRepository.save(author);

        // Step 5: Map the saved Author entity to AuthorResponse (for the response)
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorId(author.getAuthorId());
        authorResponse.setAuthorName(author.getAuthorName());
        
        // Manually map the books to the AuthorResponse (assuming AuthorResponse has a list of books)
        List<BookReqForAuthor> bookRequests = new ArrayList<>();
        for (Book book : author.getBooks()) {
        	BookReqForAuthor bookRequest = new BookReqForAuthor();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        authorResponse.setBooks(bookRequests);

        return authorResponse;
    }
//    @Override
//    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
//        Author author = new Author();
//        author.setAuthorName(authorRequest.getAuthorName());
//
//        if (authorRequest.getBooks() != null) {
//            List<Book> books = new ArrayList<>();
//
//            for (BookRequest bookRequest : authorRequest.getBooks()) {
//                Book book = new Book();
//                book.setTitle(bookRequest.getTitle());
//                book.setAvailableCopies(bookRequest.getAvailableCopies());
//
//                book.setAuthor(author);  // Set the author for each book
//
//                // Handle Publisher association
//                if (bookRequest.getPublisher() != null) {
//                    Publisher publisher = publisherRepository.findById(bookRequest.getPublisher().getPublisherId())
//                            .orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));
//                    book.setPublisher(publisher);
//                }
//
//                books.add(book);
//            }
//
//            author.setBooks(books);
//        }
//
//        author = authorRepository.save(author);
//
//        // Create response DTO
//        AuthorResponse authorResponse = new AuthorResponse();
//        authorResponse.setAuthorId(author.getAuthorId());
//        authorResponse.setAuthorName(author.getAuthorName());
//
//        // Map books to response DTO
//        List<BookRequest> bookRequests = new ArrayList<>();
//        for (Book book : author.getBooks()) {
//            BookRequest bookRequest = new BookRequest();
//            bookRequest.setBookId(book.getBookId());
//            bookRequest.setTitle(book.getTitle());
//            bookRequest.setAvailableCopies(book.getAvailableCopies());
//            bookRequests.add(bookRequest);
//        }
//        authorResponse.setBooks(bookRequests);
//
//        return authorResponse;
//    }

    @Override
    public AuthorResponse getAuthorById(Integer authorId) {
        Optional<Author> authorOpt = authorRepository.findById(authorId);
        Author author = authorOpt.orElseThrow(() -> 
            new AuthorNotFoundException("Author not found with ID: " + authorId));

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorId(author.getAuthorId());
        authorResponse.setAuthorName(author.getAuthorName());

        List<BookReqForAuthor> bookRequests = new ArrayList<>();
        for (Book book : author.getBooks()) {
        	BookReqForAuthor bookRequest = new BookReqForAuthor();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        authorResponse.setBooks(bookRequests);

        return authorResponse;
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> {
                    AuthorResponse authorResponse = new AuthorResponse();
                    authorResponse.setAuthorId(author.getAuthorId());
                    authorResponse.setAuthorName(author.getAuthorName());
                    List<BookReqForAuthor> bookRequests = new ArrayList<>();
                    for (Book book : author.getBooks()) {
                    	BookReqForAuthor bookRequest = new BookReqForAuthor();
                        bookRequest.setBookId(book.getBookId());
                        bookRequest.setTitle(book.getTitle());
                        bookRequest.setAvailableCopies(book.getAvailableCopies());
                        bookRequests.add(bookRequest);
                    }
                    authorResponse.setBooks(bookRequests);
                    return authorResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponse updateAuthor(Integer authorId, AuthorRequest authorRequest) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow(() -> 
            new AuthorNotFoundException("Author not found with ID: " + authorId));

        existingAuthor.setAuthorName(authorRequest.getAuthorName());

        if (authorRequest.getBooks() != null && !authorRequest.getBooks().isEmpty()) {
            List<Book> books = authorRequest.getBooks().stream()
                    .map(bookRequest -> {
                        Book book = new Book();
                        book.setTitle(bookRequest.getTitle());
                        book.setAvailableCopies(bookRequest.getAvailableCopies());
                        book.setAuthor(existingAuthor);  // Set the existing Author instance
                        return book;
                    })
                    .collect(Collectors.toList());

            existingAuthor.setBooks(books);
        }

        Author updatedAuthor = authorRepository.save(existingAuthor);

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorId(updatedAuthor.getAuthorId());
        authorResponse.setAuthorName(updatedAuthor.getAuthorName());

        List<BookReqForAuthor> bookRequests = new ArrayList<>();
        for (Book book : updatedAuthor.getBooks()) {
        	BookReqForAuthor bookRequest = new BookReqForAuthor();
            bookRequest.setBookId(book.getBookId());
            bookRequest.setTitle(book.getTitle());
            bookRequest.setAvailableCopies(book.getAvailableCopies());
            bookRequests.add(bookRequest);
        }
        authorResponse.setBooks(bookRequests);

        return authorResponse;
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> 
            new AuthorNotFoundException("Author not found with ID: " + authorId));
        
        authorRepository.delete(author);
    }

}
