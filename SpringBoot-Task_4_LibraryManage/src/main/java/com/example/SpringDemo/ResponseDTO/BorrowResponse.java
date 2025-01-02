package com.example.SpringDemo.ResponseDTO;

import java.util.Date;

import lombok.Data;

@Data
public class BorrowResponse {

    private Long borrowId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private Boolean isReturned;
    private String userName; // Resolved from User entity
    private String bookTitle; // Resolved from Book entity
}
