package com.example.SpringDemo.ServieImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Book;
import com.example.SpringDemo.Entity.Borrow;
import com.example.SpringDemo.Entity.User;
import com.example.SpringDemo.ExceptionClasses.BorrowNotFoundException;
import com.example.SpringDemo.Repository.BookRepository;
import com.example.SpringDemo.Repository.BorrowRepository;
import com.example.SpringDemo.Repository.UserRepository;
import com.example.SpringDemo.RequestDTO.BorrowRequest;
import com.example.SpringDemo.ResponseDTO.BorrowResponse;
import com.example.SpringDemo.Service.BorrowService;

@Service
public class BorrowServiceImpl implements BorrowService {

	 	@Autowired
	    private BorrowRepository borrowRepository;

	    @Autowired
	    private UserRepository userRepository;  // Inject UserRepository

	    @Autowired
	    private BookRepository bookRepository;  // Inject BookRepository

	    // Method to create a new borrow record using existing user and book
	    @Override
	    public BorrowResponse createBorrow(BorrowRequest borrowRequest) {
	        try {
	            // Fetch existing User and Book entities by their IDs
	            Optional<User> user = userRepository.findById(borrowRequest.getUserId());
	            Optional<Book> book = bookRepository.findById(borrowRequest.getBookId());

	            // If User or Book is not found, throw an exception
	            if (!user.isPresent()) {
	                throw new RuntimeException("User not found with userId: " + borrowRequest.getUserId());
	            }
	            if (!book.isPresent()) {
	                throw new RuntimeException("Book not found with bookId: " + borrowRequest.getBookId());
	            }

	            // Create a new Borrow record and set existing User and Book
	            Borrow borrow = new Borrow();
	            borrow.setBorrowDate(borrowRequest.getBorrowDate());
	            borrow.setDueDate(borrowRequest.getDueDate());
	            borrow.setIsReturned(borrowRequest.getIsReturned());
	            borrow.setUser(user.get());  // Set the existing User
	            borrow.setBook(book.get());  // Set the existing Book

	            // Save the borrow record
	            borrow = borrowRepository.save(borrow);

	            // Map Borrow entity to BorrowResponse
	            BorrowResponse borrowResponse = new BorrowResponse();
	            borrowResponse.setBorrowId(borrow.getBorrowId());
	            borrowResponse.setBorrowDate(borrow.getBorrowDate());
	            borrowResponse.setDueDate(borrow.getDueDate());
	            borrowResponse.setReturnDate(borrow.getReturnDate());
	            borrowResponse.setIsReturned(borrow.getIsReturned());

	            return borrowResponse;
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred while creating borrow record");
	        }
	    }

	    // Method to get a borrow record by ID
	    @Override
	    public BorrowResponse getBorrowById(Integer borrowId) {
	        try {
	            Borrow borrow = borrowRepository.findById(borrowId)
	                .orElseThrow(() -> new BorrowNotFoundException("Borrow record not found with borrowId: " + borrowId));

	            BorrowResponse borrowResponse = new BorrowResponse();
	            borrowResponse.setBorrowId(borrow.getBorrowId());
	            borrowResponse.setBorrowDate(borrow.getBorrowDate());
	            borrowResponse.setDueDate(borrow.getDueDate());
	            borrowResponse.setReturnDate(borrow.getReturnDate());
	            borrowResponse.setIsReturned(borrow.getIsReturned());

	            return borrowResponse;
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred while fetching borrow record by borrowId: " + borrowId);
	        }
	    }

	    // Method to get all borrow records
	    @Override
	    public List<BorrowResponse> getAllBorrows() {
	        try {
	            List<Borrow> borrows = borrowRepository.findAll();
	            return borrows.stream()
	                .map(borrow -> {
	                    BorrowResponse borrowResponse = new BorrowResponse();
	                    borrowResponse.setBorrowId(borrow.getBorrowId());
	                    borrowResponse.setBorrowDate(borrow.getBorrowDate());
	                    borrowResponse.setDueDate(borrow.getDueDate());
	                    borrowResponse.setReturnDate(borrow.getReturnDate());
	                    borrowResponse.setIsReturned(borrow.getIsReturned());
	                    return borrowResponse;
	                })
	                .collect(Collectors.toList());
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred while fetching all borrow records");
	        }
	    }

	    // Method to update a borrow record by ID
	    @Override
	    public BorrowResponse updateBorrow(BorrowRequest borrowRequest) {
	        try {
	            // Fetch existing Borrow record by borrowId
	            Optional<Borrow> existingBorrow = borrowRepository.findById(borrowRequest.getBorrowId());

	            // If Borrow record is not found, throw an exception
	            if (!existingBorrow.isPresent()) {
	                throw new RuntimeException("Borrow record not found with borrowId: " + borrowRequest.getBorrowId());
	            }

	            // Fetch existing User and Book entities by their IDs
	            Optional<User> user = userRepository.findById(borrowRequest.getUserId());
	            Optional<Book> book = bookRepository.findById(borrowRequest.getBookId());

	            // If User or Book is not found, throw an exception
	            if (!user.isPresent()) {
	                throw new RuntimeException("User not found with userId: " + borrowRequest.getUserId());
	            }
	            if (!book.isPresent()) {
	                throw new RuntimeException("Book not found with bookId: " + borrowRequest.getBookId());
	            }

	            // Update the existing Borrow record with the new data
	            Borrow borrow = existingBorrow.get();
	            borrow.setBorrowDate(borrowRequest.getBorrowDate());
	            borrow.setDueDate(borrowRequest.getDueDate());
	            borrow.setIsReturned(borrowRequest.getIsReturned());
	            borrow.setUser(user.get());  // Set the existing User
	            borrow.setBook(book.get());  // Set the existing Book

	            // Save the updated borrow record
	            borrow = borrowRepository.save(borrow);

	            // Map Borrow entity to BorrowResponse
	            BorrowResponse borrowResponse = new BorrowResponse();
	            borrowResponse.setBorrowId(borrow.getBorrowId());
	            borrowResponse.setBorrowDate(borrow.getBorrowDate());
	            borrowResponse.setDueDate(borrow.getDueDate());
	            borrowResponse.setReturnDate(borrow.getReturnDate());
	            borrowResponse.setIsReturned(borrow.getIsReturned());

	            return borrowResponse;
	        } catch (Exception e) {
	            e.printStackTrace();  // Print the exception stack trace for debugging
	            throw new RuntimeException("Error occurred while updating borrow record: " + e.getMessage());
	        }
	    }


	    // Method to delete a borrow record by ID
	    @Override
	    public void deleteBorrow(Integer borrowId) {
	        try {
	            Borrow borrow = borrowRepository.findById(borrowId)
	                .orElseThrow(() -> new BorrowNotFoundException("Borrow record not found with borrowId: " + borrowId));
	            borrowRepository.delete(borrow);
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred while deleting borrow record by borrowId: " + borrowId);
	        }
	    }  
	    
}
