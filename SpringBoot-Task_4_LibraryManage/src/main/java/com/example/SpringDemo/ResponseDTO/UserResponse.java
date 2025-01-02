package com.example.SpringDemo.ResponseDTO;

import java.util.List;

import com.example.SpringDemo.RequestDTO.BorrowRequest;
import com.example.SpringDemo.RequestDTO.FineRequest;

import lombok.Data;

@Data
public class UserResponse {

	private Integer userId;


    private String userName;
	
    private String email;
	
    private List<BorrowRequest> borrows; // Include borrow details
    private List<FineRequest> fines; 
}
