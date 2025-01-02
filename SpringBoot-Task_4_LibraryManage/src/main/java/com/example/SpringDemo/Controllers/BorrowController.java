package com.example.SpringDemo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.RequestDTO.BorrowRequest;
import com.example.SpringDemo.ResponseDTO.BorrowResponse;
import com.example.SpringDemo.Service.BorrowService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    
    @PostMapping("/createBorrow")
    public ResponseEntity<BorrowResponse> createBorrow(@RequestBody @Valid BorrowRequest borrowRequest) {
        BorrowResponse borrowResponse = borrowService.createBorrow(borrowRequest);
        return ResponseEntity.ok(borrowResponse);
    }

    @GetMapping("/getBorrowById/{borrowId}")
    public ResponseEntity<BorrowResponse> getBorrowById(@PathVariable Integer borrowId) {
        BorrowResponse borrowResponse = borrowService.getBorrowById(borrowId);
        return ResponseEntity.ok(borrowResponse);
    }
    
    @GetMapping("/getAllBorrows")
    public ResponseEntity<List<BorrowResponse>> getAllBorrows() {
        List<BorrowResponse> borrowResponses = borrowService.getAllBorrows();
        return ResponseEntity.ok(borrowResponses);
    }

    // Update a borrow record by ID
    @PutMapping("/updateBorrow")
    public ResponseEntity<BorrowResponse> updateBorrow(@Valid
            @RequestBody BorrowRequest borrowRequest) {
        BorrowResponse borrowResponse = borrowService.updateBorrow(borrowRequest);
        return ResponseEntity.ok(borrowResponse);
    }


    @DeleteMapping("/deleteBorrow/{borrowId}")
    public ResponseEntity<String> deleteBorrow(@PathVariable Integer borrowId) {
        borrowService.deleteBorrow(borrowId);
        return ResponseEntity.ok("Borrow record deleted successfully");
    }
}
