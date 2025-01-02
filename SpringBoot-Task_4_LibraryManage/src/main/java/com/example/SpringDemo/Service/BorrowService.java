package com.example.SpringDemo.Service;

import java.util.List;

import com.example.SpringDemo.RequestDTO.BorrowRequest;
import com.example.SpringDemo.ResponseDTO.BorrowResponse;

public interface BorrowService {

    BorrowResponse createBorrow(BorrowRequest borrowRequest);

    // Get borrow by ID
    BorrowResponse getBorrowById(Integer borrowId);

    // Get all borrows
    List<BorrowResponse> getAllBorrows();

    // Update a borrow
    BorrowResponse updateBorrow(BorrowRequest borrowRequest);

    // Delete a borrow
    void deleteBorrow(Integer borrowId);
}
