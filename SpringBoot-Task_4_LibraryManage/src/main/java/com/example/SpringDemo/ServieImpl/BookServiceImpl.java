package com.example.SpringDemo.ServieImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Author;
import com.example.SpringDemo.Entity.Book;
import com.example.SpringDemo.Entity.Borrow;
import com.example.SpringDemo.Entity.Publisher;
import com.example.SpringDemo.ExceptionClasses.BookNotFoundException;
import com.example.SpringDemo.Repository.AuthorRepository;
import com.example.SpringDemo.Repository.BookRepository;
import com.example.SpringDemo.Repository.BorrowRepository;
import com.example.SpringDemo.Repository.PublisherRepository;
import com.example.SpringDemo.RequestDTO.BookRequest;
import com.example.SpringDemo.RequestDTO.BorrowReqForBook;
import com.example.SpringDemo.ResponseDTO.BookResponse;
import com.example.SpringDemo.ResponseDTO.BorrowResponse;
import com.example.SpringDemo.Service.BookService;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        try {
            // Step 1: Create and Save Book
            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setAvailableCopies(bookRequest.getAvailableCopies());

            // Step 2: Set Author
            Author author = authorRepository.findById(bookRequest.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with ID: " + bookRequest.getAuthorId()));
            book.setAuthor(author);

            // Step 3: Set Publisher
            Publisher publisher = publisherRepository.findById(bookRequest.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + bookRequest.getPublisherId()));
            book.setPublisher(publisher);

            // Step 4: Save Book First
            book = bookRepository.save(book);

            // Step 5: Handle Borrow Records from BorrowReqForBook List
            if (bookRequest.getBorrows() != null && !bookRequest.getBorrows().isEmpty()) {
                List<Borrow> borrows = new ArrayList<>();

                // Loop through BorrowReqForBook list to create Borrow entities
                for (BorrowReqForBook borrowReq : bookRequest.getBorrows()) {
                    Borrow borrow = new Borrow();
                    borrow.setBorrowDate(borrowReq.getBorrowDate());
                    borrow.setDueDate(borrowReq.getDueDate());
                    borrow.setIsReturned(borrowReq.getIsReturned());
                    borrow.setBook(book); // Associate Borrow with the saved Book
                    
                    // Save Borrow entity separately
                    borrowRepository.save(borrow); // Save Borrow to DB
                    borrows.add(borrow); // Add to the borrows list
                }

                book.setBorrows(borrows); // Attach Borrows to Book after saving
            }

            // Step 6: Map Book to BookResponse
            BookResponse bookResponse = new BookResponse();
            bookResponse.setBookId(book.getBookId());
            bookResponse.setTitle(book.getTitle());
            bookResponse.setAvailableCopies(book.getAvailableCopies());
            bookResponse.setAuthorName(author.getAuthorName());
            bookResponse.setPublisherName(publisher.getPName());

            // Step 7: Add Borrow Data to Response (Map Borrow entities to BorrowResponse)
            if (book.getBorrows() != null) {
                List<BorrowResponse> borrowResponses = new ArrayList<>();
                for (Borrow borrow : book.getBorrows()) {
                    BorrowResponse borrowResponse = new BorrowResponse();
                    borrowResponse.setBorrowId(borrow.getBorrowId());
                    borrowResponse.setBorrowDate(borrow.getBorrowDate());
                    borrowResponse.setDueDate(borrow.getDueDate());
                    borrowResponse.setIsReturned(borrow.getIsReturned());
                    borrowResponses.add(borrowResponse);
                }
                bookResponse.setBorrows(borrowResponses);
            }

            return bookResponse;

        } catch (Exception e) {
            e.printStackTrace();  // Detailed error logging
            throw new RuntimeException("Error occurred while creating Book", e);  // Enhanced error message
        }
    }

    @Override
    public BookResponse getBookById(Integer bookId) {
        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException("Book not found with bookId: " + bookId));

            BookResponse bookResponse = new BookResponse();
            bookResponse.setBookId(book.getBookId());
            bookResponse.setTitle(book.getTitle());
            bookResponse.setAvailableCopies(book.getAvailableCopies());

            if (book.getAuthor() != null) {
                bookResponse.setAuthorName(book.getAuthor().getAuthorName());
            }
            if (book.getPublisher() != null) {
                bookResponse.setPublisherName(book.getPublisher().getPName());
            }

            return bookResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error during fetching book by bookId: " + bookId);
        }
    }

    @Override
    public List<BookResponse> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            List<BookResponse> bookResponses = new ArrayList<>();

            for (Book book : books) {
                BookResponse bookResponse = new BookResponse();
                bookResponse.setBookId(book.getBookId());
                bookResponse.setTitle(book.getTitle());
                bookResponse.setAvailableCopies(book.getAvailableCopies());

                if (book.getAuthor() != null) {
                    bookResponse.setAuthorName(book.getAuthor().getAuthorName());
                }
                if (book.getPublisher() != null) {
                    bookResponse.setPublisherName(book.getPublisher().getPName());
                }

                bookResponses.add(bookResponse);
            }

            return bookResponses;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching all books");
        }
    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest) {
        try {
            Book book = new Book();

            book.setBookId(bookRequest.getBookId());
            book.setTitle(bookRequest.getTitle());
            book.setAvailableCopies(bookRequest.getAvailableCopies());

            Author author = authorRepository.findById(bookRequest.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with ID: " + bookRequest.getAuthorId()));
            book.setAuthor(author);

            Publisher publisher = publisherRepository.findById(bookRequest.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + bookRequest.getPublisherId()));
            book.setPublisher(publisher);

            book = bookRepository.save(book);

            BookResponse bookResponse = new BookResponse();
            bookResponse.setBookId(book.getBookId());
            bookResponse.setTitle(book.getTitle());
            bookResponse.setAvailableCopies(book.getAvailableCopies());
            bookResponse.setAuthorName(author.getAuthorName());
            bookResponse.setPublisherName(publisher.getPName());

            return bookResponse;
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during updating Book");
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException("Book not found with bookId: " + bookId));
            bookRepository.delete(book);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting book by bookId: " + bookId);
        }
    }
}
