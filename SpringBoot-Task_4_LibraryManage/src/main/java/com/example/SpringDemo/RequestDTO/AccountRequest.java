package com.example.SpringDemo.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequest {	
	
	private Long accId;

    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    private String roleName; // Default can be handled in the service layer if needed
}
