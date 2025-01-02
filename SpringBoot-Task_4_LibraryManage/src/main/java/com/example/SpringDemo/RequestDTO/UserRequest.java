package com.example.SpringDemo.RequestDTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

	private Integer userId;
	
	@NotNull(message = "Username is required")
    private String userName;
	
	@NotNull(message = "email is required")
    private String email;
	
    private List<BorrowRequest> borrows; // Include borrow details
    private List<FineRequest> fines;  
}
